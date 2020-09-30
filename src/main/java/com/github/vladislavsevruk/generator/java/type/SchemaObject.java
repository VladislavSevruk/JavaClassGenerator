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
package com.github.vladislavsevruk.generator.java.type;

import java.util.List;

/**
 * Represents class schema of simple class.
 */
public interface SchemaObject extends SchemaEntity {

    /**
     * Returns <code>List</code> of <code>SchemaField</code> with field schemas of this class.
     */
    List<SchemaField> getFields();

    /**
     * Returns <code>List</code> of <code>SchemaEntity</code> with interface schemas of this class.
     */
    List<SchemaEntity> getInterfaces();

    /**
     * Returns <code>SchemaEntity</code> with superclass schema of this class.
     */
    SchemaEntity getSuperclass();

    /**
     * Returns <code>true</code> if this class schema has declared superclass schema, <code>false</code> otherwise.
     */
    default boolean hasSuperclass() {
        return getSuperclass() != null;
    }
}
