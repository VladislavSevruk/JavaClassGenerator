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

import com.github.vladislavsevruk.generator.java.type.SchemaEntity;

import java.util.function.Supplier;

/**
 * Represents schema entity for <code>Iterable</code> class.
 */
public class IterableSchemaEntity extends BaseIterableSchemaEntity {

    public IterableSchemaEntity() {
        super();
    }

    public IterableSchemaEntity(SchemaEntity elementType) {
        super(elementType);
    }

    public IterableSchemaEntity(Supplier<SchemaEntity> delayedElementTypeSupplier) {
        super(delayedElementTypeSupplier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Iterable";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPackage() {
        // null for 'java.lang' package to avoid explicit import generation
        return null;
    }
}
