package com.minogin.confirm

import org.jetbrains.kotlin.backend.common.lower.*
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.expressions.impl.*
import org.jetbrains.kotlin.ir.symbols.*
import org.jetbrains.kotlin.ir.util.*

fun DeclarationIrBuilder.irGetEnumValue(entry: IrEnumEntrySymbol): IrGetEnumValue =
    IrGetEnumValueImpl(
        startOffset = startOffset,
        endOffset = endOffset,
        type = (entry.owner.parent as IrClass).defaultType,
        symbol = entry
    )

fun DeclarationIrBuilder.irPropertyReference(
    kClass: IrClassSymbol,
    property: IrPropertySymbol
): IrPropertyReference =
    IrPropertyReferenceImpl(
        startOffset = startOffset,
        endOffset = endOffset,
        type = context.kProperty1Type(kClass.owner.defaultType, property.owner.getter!!.returnType),
        symbol = property,
        typeArgumentsCount = 2,
        field = property.owner.backingField?.symbol,
        getter = property.owner.getter?.symbol,
        setter = property.owner.setter?.symbol
    ).apply {
        typeArguments[0] = kClass.owner.defaultType
        typeArguments[1] = property.owner.getter!!.returnType

        if (arguments.isEmpty()) {
            arguments.add(null)
        } else {
            arguments[0] = null
        }
    }