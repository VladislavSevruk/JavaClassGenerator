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
package com.github.vladislavsevruk.generator.java.storage;

import com.github.vladislavsevruk.generator.java.type.SchemaObject;

import java.util.Collection;

/**
 * Contains class schemas to generate Java class content for.
 *
 * @see SchemaObject
 */
public interface SchemaObjectStorage {

    /**
     * Returns class schema with received name from this storage.
     *
     * @param name <code>String</code> with class schema name to get.
     * @return <code>SchemaObject</code> that have received name or <code>null</code> if there is no such schema.
     */
    SchemaObject get(String name);

    /**
     * Returns <code>Collection</code> of all <code>SchemaObject</code> present at this storage.
     */
    Collection<SchemaObject> getAllObjects();
}
