/*
 * MIT License
 *
 * Copyright (c) 2020 Uladzislau Seuruk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.vladislavsevruk.generator.java.type.predefined.sequence;

import com.github.vladislavsevruk.generator.java.type.SchemaElementSequence;
import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.CommonJavaSchemaEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Represents schema entity for <code>Map</code> class.
 */
public class MapSchemaEntity implements SchemaElementSequence {

    private Supplier<SchemaEntity> delayedKeyTypeSupplier;
    private Supplier<SchemaEntity> delayedValueTypeSupplier;
    private SchemaEntity keyType;
    private SchemaEntity valueType;

    public MapSchemaEntity(SchemaEntity keyType, SchemaEntity valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    public MapSchemaEntity(Supplier<SchemaEntity> delayedKeyTypeSupplier,
            Supplier<SchemaEntity> delayedValueTypeSupplier) {
        this.delayedKeyTypeSupplier = delayedKeyTypeSupplier;
        this.delayedValueTypeSupplier = delayedValueTypeSupplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<SchemaEntity> getElementTypes() {
        return Arrays.asList(getKeyType(), getValueType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Map";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPackage() {
        return "java.util";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getParameterizedDeclaration() {
        SchemaEntity keySchemaEntity = getKeyType();
        SchemaEntity valueSchemaEntity = getValueType();
        if (keySchemaEntity == null && valueSchemaEntity == null) {
            return getName();
        }
        if (keySchemaEntity == null) {
            keySchemaEntity = CommonJavaSchemaEntity.OBJECT;
        }
        if (valueSchemaEntity == null) {
            valueSchemaEntity = CommonJavaSchemaEntity.OBJECT;
        }
        return String.format("%s<%s, %s>", getName(), keySchemaEntity.getName(), valueSchemaEntity.getName());
    }

    protected SchemaEntity getKeyType() {
        if (keyType == null && delayedKeyTypeSupplier != null) {
            keyType = delayedKeyTypeSupplier.get();
        }
        return keyType;
    }

    protected SchemaEntity getValueType() {
        if (valueType == null && delayedValueTypeSupplier != null) {
            valueType = delayedValueTypeSupplier.get();
        }
        return valueType;
    }
}
