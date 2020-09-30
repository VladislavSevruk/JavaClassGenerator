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
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of <code>SchemaObjectStorage</code>.
 *
 * @see SchemaObject
 * @see SchemaObjectStorage
 */
public final class SchemaObjectStorageImpl implements SchemaObjectStorage {

    private Map<String, SchemaObject> objectMap = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public SchemaObject get(String name) {
        return objectMap.get(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<SchemaObject> getAllObjects() {
        return objectMap.values();
    }

    /**
     * Adds class schema to this storage with object name as a key.
     *
     * @param schemaObject <code>SchemaObject</code> of some class.
     */
    public void put(SchemaObject schemaObject) {
        put(schemaObject.getName(), schemaObject);
    }

    /**
     * Adds class schema to this storage with received name as a key.
     *
     * @param name         <code>String</code> with class schema name.
     * @param schemaObject <code>SchemaObject</code> of some class.
     */
    public void put(String name, SchemaObject schemaObject) {
        objectMap.put(name, schemaObject);
    }
}
