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

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Contains common logic for class schema of entity with elements like collections or arrays.
 */
public abstract class BaseSchemaElementSequence implements SchemaElementSequence {

    private Supplier<SchemaEntity> delayedElementTypeSupplier;
    private SchemaEntity elementType;

    public BaseSchemaElementSequence(SchemaEntity elementType) {
        this.elementType = elementType;
    }

    public BaseSchemaElementSequence(Supplier<SchemaEntity> delayedElementTypeResolver) {
        Objects.requireNonNull(delayedElementTypeResolver, "Delayed type initialization cannot be null.");
        this.delayedElementTypeSupplier = delayedElementTypeResolver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<SchemaEntity> getElementTypes() {
        return Collections.singletonList(getElementType());
    }

    protected SchemaEntity getElementType() {
        if (elementType == null && delayedElementTypeSupplier != null) {
            elementType = delayedElementTypeSupplier.get();
        }
        return elementType;
    }
}
