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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

@ExtendWith(MockitoExtension.class)
class SchemaObjectStorageImplTest {

    @Mock
    private SchemaObject schemaObject1;
    @Mock
    private SchemaObject schemaObject2;

    @Test
    void getAllSchemasTest() {
        SchemaObjectStorageImpl schemaObjectStorage = new SchemaObjectStorageImpl();
        schemaObjectStorage.put("schema1", schemaObject1);
        schemaObjectStorage.put("schema2", schemaObject2);
        Iterator<SchemaObject> schemaObjects = schemaObjectStorage.getAllObjects().iterator();
        Assertions.assertTrue(schemaObjects.hasNext());
        SchemaObject firstSchemaObject = schemaObjects.next();
        Assertions.assertTrue(firstSchemaObject.equals(schemaObject1) || firstSchemaObject.equals(schemaObject2));
        Assertions.assertTrue(schemaObjects.hasNext());
        if (firstSchemaObject.equals(schemaObject1)) {
            Assertions.assertEquals(schemaObject2, schemaObjects.next());
        } else {
            Assertions.assertEquals(schemaObject1, schemaObjects.next());
        }
        Assertions.assertFalse(schemaObjects.hasNext());
    }

    @Test
    void getSchemaTest() {
        SchemaObjectStorageImpl schemaObjectStorage = new SchemaObjectStorageImpl();
        String key = "schema1";
        schemaObjectStorage.put(key, schemaObject1);
        Assertions.assertEquals(schemaObject1, schemaObjectStorage.get(key));
    }
}
