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
import com.github.vladislavsevruk.generator.java.type.predefined.CommonJavaSchemaEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class MapSchemaEntityTest {

    @Test
    void getDelayedElementTypeTest() {
        MapSchemaEntity schemaEntity = new MapSchemaEntity(() -> CommonJavaSchemaEntity.STRING,
                () -> CommonJavaSchemaEntity.BIG_INTEGER);
        Iterator<SchemaEntity> elementTypes = schemaEntity.getElementTypes().iterator();
        Assertions.assertTrue(elementTypes.hasNext());
        Assertions.assertEquals(CommonJavaSchemaEntity.STRING, elementTypes.next());
        Assertions.assertTrue(elementTypes.hasNext());
        Assertions.assertEquals(CommonJavaSchemaEntity.BIG_INTEGER, elementTypes.next());
        Assertions.assertFalse(elementTypes.hasNext());
    }

    @Test
    void getNullKeyElementTypeTest() {
        MapSchemaEntity schemaEntity = new MapSchemaEntity(null, CommonJavaSchemaEntity.STRING);
        Iterator<SchemaEntity> elementTypes = schemaEntity.getElementTypes().iterator();
        Assertions.assertTrue(elementTypes.hasNext());
        Assertions.assertNull(elementTypes.next());
        Assertions.assertTrue(elementTypes.hasNext());
        Assertions.assertEquals(CommonJavaSchemaEntity.STRING, elementTypes.next());
        Assertions.assertFalse(elementTypes.hasNext());
    }

    @Test
    void getNullKeyTypeParameterizedDeclarationTest() {
        MapSchemaEntity schemaEntity = new MapSchemaEntity(null, CommonJavaSchemaEntity.STRING);
        Assertions.assertEquals("Map<Object, String>", schemaEntity.getParameterizedDeclaration());
    }

    @Test
    void getNullKeyValueElementTypeTest() {
        MapSchemaEntity schemaEntity = new MapSchemaEntity((SchemaEntity) null, null);
        Iterator<SchemaEntity> elementTypes = schemaEntity.getElementTypes().iterator();
        Assertions.assertTrue(elementTypes.hasNext());
        Assertions.assertNull(elementTypes.next());
        Assertions.assertTrue(elementTypes.hasNext());
        Assertions.assertNull(elementTypes.next());
        Assertions.assertFalse(elementTypes.hasNext());
    }

    @Test
    void getNullKeyValueTypeParameterizedDeclarationTest() {
        MapSchemaEntity schemaEntity = new MapSchemaEntity((SchemaEntity) null, null);
        Assertions.assertEquals("Map", schemaEntity.getParameterizedDeclaration());
    }

    @Test
    void getNullValueElementTypeTest() {
        MapSchemaEntity schemaEntity = new MapSchemaEntity(CommonJavaSchemaEntity.STRING, null);
        Iterator<SchemaEntity> elementTypes = schemaEntity.getElementTypes().iterator();
        Assertions.assertTrue(elementTypes.hasNext());
        Assertions.assertEquals(CommonJavaSchemaEntity.STRING, elementTypes.next());
        Assertions.assertTrue(elementTypes.hasNext());
        Assertions.assertNull(elementTypes.next());
        Assertions.assertFalse(elementTypes.hasNext());
    }

    @Test
    void getNullValueTypeParameterizedDeclarationTest() {
        MapSchemaEntity schemaEntity = new MapSchemaEntity(CommonJavaSchemaEntity.STRING, null);
        Assertions.assertEquals("Map<String, Object>", schemaEntity.getParameterizedDeclaration());
    }
}

