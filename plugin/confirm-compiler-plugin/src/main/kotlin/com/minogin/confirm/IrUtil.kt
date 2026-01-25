package com.minogin.confirm

import org.jetbrains.kotlin.backend.common.extensions.*
import org.jetbrains.kotlin.ir.builders.*
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.expressions.impl.*
import org.jetbrains.kotlin.ir.symbols.*
import org.jetbrains.kotlin.ir.types.*
import org.jetbrains.kotlin.ir.util.*
import org.jetbrains.kotlin.name.*

fun IrCall.fqName(): String = this.symbol.owner.kotlinFqName.asString()

fun IrPluginContext.kClass(pkg: String, cls: String): IrClassSymbol {
    val classId = ClassId(packageFqName = FqName(pkg), topLevelName = Name.identifier(cls))
    return referenceClass(classId) ?: error("Could not find class $pkg.$cls")
}

fun IrPluginContext.functions(pkg: String, fn: String): Collection<IrSimpleFunctionSymbol> {
    val callableId = CallableId(packageName = FqName(pkg), callableName = Name.identifier(fn))
    return referenceFunctions(callableId)
}

fun IrClassSymbol.constructor(): IrConstructorSymbol =
    owner.constructors.first().symbol

fun IrClassSymbol.enumEntry(name: String): IrEnumEntrySymbol =
    owner.declarations.filterIsInstance<IrEnumEntry>().first { it.name.asString() == name }.symbol

fun IrGeneratorContext.kProperty1Type(type1: IrSimpleType, type2: IrType): IrType =
    irBuiltIns.kProperty1Class.typeWith(type1, type2)

fun IrPluginContext.retypeToAny(vararg: IrVararg): IrVararg =
    IrVarargImpl(
        startOffset = vararg.startOffset,
        endOffset = vararg.endOffset,
        type = irBuiltIns.arrayClass.typeWith(irBuiltIns.anyNType), // Array<Any?>
        varargElementType = irBuiltIns.anyNType, // Any?
        elements = vararg.elements // Keep the already transformed elements (ValueMatchers)
    )