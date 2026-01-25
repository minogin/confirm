package com.minogin.confirm

import org.jetbrains.kotlin.backend.common.*
import org.jetbrains.kotlin.backend.common.extensions.*
import org.jetbrains.kotlin.backend.common.lower.*
import org.jetbrains.kotlin.backend.jvm.ir.*
import org.jetbrains.kotlin.ir.builders.*
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.types.defaultType
import org.jetbrains.kotlin.ir.types.typeWith
import org.jetbrains.kotlin.ir.util.*
import org.jetbrains.kotlin.ir.visitors.*

class ConfirmIrTransformer(
    private val context: IrPluginContext
) : IrElementTransformerVoid() {
    companion object {
        const val ApiPackage = "com.minogin.confirm.api"
        const val ImplPackage = "com.minogin.confirm.impl"

        const val DeepMatches = "$ImplPackage.ConfirmationImpl.deepMatches"
        const val both = "$ApiPackage.both"
        const val either = "$ApiPackage.either"
        const val lessThan = "$ApiPackage.lessThan"

        const val MatcherPackage = "com.minogin.confirm.matcher"
        const val Matcher = "Matcher"

        const val BuiltinMatcherPackage = "com.minogin.confirm.matcher.builtin"
        const val ObjectMatcher = "ObjectMatcher"
        const val ListMatcher = "ListMatcher"
        const val LogicalMatcher = "LogicalMatcher"
        const val LogicalOperator = "LogicalOperator"
        const val ComparableMatcher = "ComparableMatcher"
        const val ComparisonOperator = "ComparisonOperator"
    }

    override fun visitCall(expression: IrCall): IrExpression =
        when (expression.fqName()) {
            DeepMatches -> transformDeepMatchesLambda(expression)
            else -> super.visitCall(expression)
        }

    private fun transformDeepMatchesLambda(expression: IrCall): IrExpression {
        val lambda = expression.arguments[1] as IrFunctionExpression
        val lambdaFunction = lambda.function

        lambdaFunction.transform(Transformer(), null)

        lambdaFunction.returnType = context.kClass(MatcherPackage, Matcher).defaultType

        lambda.type = context.irBuiltIns.functionN(0).typeWith(lambdaFunction.returnType)

        return expression
    }

    private inner class Transformer : IrElementTransformerVoidWithContext() {
        private fun builder(transformed: IrExpression): DeclarationIrBuilder? {
            val scopeSymbol = allScopes.lastOrNull()?.scope?.scopeOwnerSymbol
                ?: return null

            return DeclarationIrBuilder(
                generatorContext = context,
                symbol = scopeSymbol,
                startOffset = transformed.startOffset,
                endOffset = transformed.endOffset
            )
        }

        override fun visitExpression(expression: IrExpression): IrExpression {
            val transformed = super.visitExpression(expression)

            val builder = builder(transformed) ?: return transformed

            if (transformed.type.isSubtypeOfClass(context.irBuiltIns.listClass.owner.symbol)) {
                val anyNType = context.irBuiltIns.anyNType
                val newListType = context.irBuiltIns.listClass.typeWith(anyNType)

//                transformed.type = newListType

                when {
                    transformed is IrCall -> {
                        transformed.arguments.forEachIndexed { i, arg ->
                            if (arg is IrVararg) {
                                transformed.arguments[i] = context.retypeToAny(arg)
                            }
                        }

//                        builder
//                            .irCallWithSubstitutedType(function, listOf(anyNType))
//                            .apply {
//                                transformed.arguments.forEachIndexed { i, arg ->
//                                    function.owner.valueParameters
//                                    arguments[i] = when {
//                                        arg.va
//                                    }
//                                }
//                                this.dispatchReceiver = transformed.dispatchReceiver
//                            }
                    }
                }

//                val cast = builder.typeOperator(
//                    resultType = newListType,
//                    argument = transformed,
//                    typeOperator = IrTypeOperator.CAST,
//                    typeOperand = newListType
//                )

                val listMatcherConstructor =
                    context.kClass(BuiltinMatcherPackage, ListMatcher).constructor()
                return builder.irCall(listMatcherConstructor).apply {
                    arguments[0] = transformed
                }
            }

            return transformed
        }

        override fun visitCall(expression: IrCall): IrExpression {
            val transformed = super.visitCall(expression)
            if (transformed !is IrCall) return transformed

            val builder = builder(transformed) ?: return transformed

            val fqName = transformed.symbol.owner.kotlinFqName.asString()

            if (fqName == both || fqName == either) {
                val logicalMatcherConstructor =
                    context.kClass(BuiltinMatcherPackage, LogicalMatcher).constructor()
                val logicalOperatorClass = context.kClass(BuiltinMatcherPackage, LogicalOperator)

                return builder.irCall(logicalMatcherConstructor).apply {
                    arguments[0] = transformed.arguments[0]
                    arguments[1] = transformed.arguments[1]
                    arguments[2] = builder.irGetEnumValue(
                        logicalOperatorClass.enumEntry(
                            when (fqName) {
                                both -> "And"
                                either -> "Or"
                                else -> error("Unexpected fqName: $fqName")
                            }
                        )
                    )
                }
            }

            if (fqName == lessThan) {
                val comparableMatcherConstructor =
                    context.kClass(BuiltinMatcherPackage, ComparableMatcher).constructor()
                val comparisonOperatorClass = context.kClass(BuiltinMatcherPackage, ComparisonOperator)

                return builder.irCall(comparableMatcherConstructor).apply {
                    arguments[0] = transformed.arguments[0]
                    arguments[1] = builder.irGetEnumValue(
                        comparisonOperatorClass.enumEntry("LessThan")
                    )
                }
            }

            return transformed
        }

        override fun visitConstructorCall(expression: IrConstructorCall): IrExpression {
            val transformed = super.visitConstructorCall(expression)
            if (transformed !is IrConstructorCall) return transformed

            val builder = builder(transformed) ?: return transformed

            val targetClass = transformed.symbol.owner.parentAsClass.symbol
            val classReference = builder.kClassReference(targetClass.defaultType)

            val mapOf = context.functions("kotlin.collections", "mapOf").first { fn ->
                val parameters = fn.owner.parameters
                parameters.size == 1 && parameters[0].isVararg
            }
            val to = context.functions("kotlin", "to").first()

            val pairs = transformed.symbol.owner.parameters.mapIndexedNotNull { index, param ->
                val arg = transformed.arguments[index] ?: return@mapIndexedNotNull null
                val property =
                    targetClass.owner.properties.find { it.name == param.name }?.symbol ?: return@mapIndexedNotNull null

                val propRef = builder.irPropertyReference(
                    kClass = targetClass,
                    property = property
                )

                builder.irCall(to).apply {
                    typeArguments[0] = context.irBuiltIns.anyNType
                    typeArguments[1] = context.irBuiltIns.anyNType

                    arguments[0] = propRef
                    arguments[1] = arg
                }
            }

            val propertiesMap = builder.irCall(mapOf).apply {
                typeArguments[0] = context.irBuiltIns.anyNType
                typeArguments[1] = context.irBuiltIns.anyNType

                val pairClass = context.kClass("kotlin", "Pair")
                arguments[0] = builder.irVararg(
                    elementType = pairClass.typeWith(context.irBuiltIns.anyNType, context.irBuiltIns.anyNType),
                    values = pairs
                )
            }

            // TODO Only do transformation if there's a nested matcher somewhere
            val objectMatcherConstructor = context.kClass(BuiltinMatcherPackage, ObjectMatcher).constructor()
            return builder.irCall(objectMatcherConstructor).apply {
                arguments[0] = classReference
                arguments[1] = propertiesMap
                arguments[2] = builder.irNull()
            }
        }
    }
}