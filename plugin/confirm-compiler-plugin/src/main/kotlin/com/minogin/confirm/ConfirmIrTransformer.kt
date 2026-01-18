package com.minogin.confirm

import org.jetbrains.kotlin.backend.common.extensions.*
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.expressions.impl.*
import org.jetbrains.kotlin.ir.symbols.*
import org.jetbrains.kotlin.ir.types.*
import org.jetbrains.kotlin.ir.util.*
import org.jetbrains.kotlin.ir.visitors.*
import org.jetbrains.kotlin.name.*

class ConfirmIrTransformer(
    private val context: IrPluginContext
) : IrElementTransformerVoid() {
    companion object {
        const val MatcherPackage = "com.minogin.confirm.matcher"
    }

    override fun visitCall(expression: IrCall): IrExpression {
        val fqName = expression.symbol.owner.kotlinFqName.asString()
        if (fqName == "com.minogin.confirm.api.Confirmation.deepMatches") {
            val expectedLambda = expression.arguments[1] as? IrFunctionExpression
            if (expectedLambda != null) {
                val function = expectedLambda.function

                function.transformChildren(object : IrElementTransformerVoid() {
                    override fun visitConst(expression: IrConst): IrExpression = transformConst(expression)
                }, null)

                function.returnType = equalsMatcherClassSymbol().defaultType
                val receiverType = function.parameters[0].type
                expectedLambda.type = context.irBuiltIns.functionN(1).typeWith(receiverType, function.returnType)
            }

            return expression
        }

        return super.visitCall(expression)
    }

    private fun equalsMatcherClassSymbol(): IrClassSymbol {
        val classId = ClassId(FqName(MatcherPackage), Name.identifier("EqualsMatcher"))
        return context.referenceClass(classId) ?: error("Could not find EqualsMatcher")
    }

    private fun transformConst(expression: IrConst): IrExpression {
        val constructorSymbol = equalsMatcherClassSymbol().owner.constructors.first().symbol

        return IrConstructorCallImpl.fromSymbolOwner(
            startOffset = expression.startOffset,
            endOffset = expression.endOffset,
            type = constructorSymbol.owner.returnType,
            constructorSymbol = constructorSymbol
        ).apply {
            arguments[0] = expression
        }
    }

    private fun transformInternalCall(expression: IrCall): IrExpression {
//        if (expression.type.isIterable())

        return expression
    }

//    override fun visitCall(declaration: IrFunction): IrStatement {
//        val body = declaration.body as? IrBlockBody ?: return super.visitFunction(declaration)
//
//        val myAnalyticsClassSymbol = context.referenceClass(
//            ClassId(FqName("com.minogin.deep"), Name.identifier("MyAnalytics"))
//        ) ?: return super.visitFunction(declaration)
//
//        val listenerFuncSymbol = context.referenceFunctions(
//            CallableId(
//                FqName("com.minogin.deep"),
//                FqName("MyAnalytics"),
//                Name.identifier("onFunctionStart")
//            )
//        ).firstOrNull() ?: return super.visitFunction(declaration)
//
//        // 3. Use a Builder to create the new IR code
//        declaration.body = DeclarationIrBuilder(context, declaration.symbol).irBlockBody {
//            // Create the call: MyAnalytics.onFunctionStart("functionName")
//            val call = irCall(listenerFuncSymbol).apply {
//                arguments[0] = irGetObject(myAnalyticsClassSymbol)
//                arguments[1] = irString(declaration.name.asString())
//            }
//
//            // Add the listener call to the TOP of the function
//            +call
//
//            // Add all original statements back after our call
//            for (statement in body.statements) {
//                +statement
//            }
//        }
//
//        return super.visitFunction(declaration)
//    }
//
//    override fun visitExpression(expression: IrExpression): IrExpression {
//        // Logic to transform specific expressions goes here
//        return super.visitExpression(expression)
//    }
}