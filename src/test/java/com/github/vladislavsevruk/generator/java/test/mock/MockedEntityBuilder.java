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
package com.github.vladislavsevruk.generator.java.test.mock;

import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import com.github.vladislavsevruk.generator.java.type.SchemaEnum;
import com.github.vladislavsevruk.generator.java.type.SchemaField;
import com.github.vladislavsevruk.generator.java.type.SchemaInterface;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import com.github.vladislavsevruk.generator.java.type.predefined.CommonJavaSchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.PrimitiveSchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.sequence.ArraySchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.sequence.CollectionSchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.sequence.IterableSchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.sequence.ListSchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.sequence.MapSchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.sequence.SetSchemaEntity;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MockedEntityBuilder<T extends SchemaObject> {

    private final Class<T> entityType;
    private final T schemaObject;

    private MockedEntityBuilder(T schemaObject, Class<T> entityType) {
        this.schemaObject = schemaObject;
        this.entityType = entityType;
    }

    public static MockedEntityBuilder<SchemaObject> getMockedClassBuilder() {
        return new MockedEntityBuilder<>(Mockito.mock(SchemaObject.class), SchemaObject.class);
    }

    public static MockedEntityBuilder<SchemaEnum> getMockedEnumBuilder() {
        return new MockedEntityBuilder<>(Mockito.mock(SchemaEnum.class), SchemaEnum.class);
    }

    public static MockedEntityBuilder<SchemaInterface> getMockedInterfaceBuilder() {
        return new MockedEntityBuilder<>(Mockito.mock(SchemaInterface.class), SchemaInterface.class);
    }

    public T build() {
        return build("");
    }

    public T build(String classNamePostfix) {
        if (schemaObject.getFields() == null) {
            setFields(0);
        }
        if (schemaObject.getInterfaces() == null) {
            setInterfaces(0);
        }
        if (SchemaEnum.class.isAssignableFrom(entityType)) {
            Mockito.when(schemaObject.getName()).thenReturn("MockedEnum" + classNamePostfix);
        } else {
            String name = SchemaInterface.class.isAssignableFrom(entityType) ? "MockedInterface" : "MockedClass";
            name += classNamePostfix;
            Mockito.when(schemaObject.getParameterizedDeclaration()).thenReturn(name);
        }
        Mockito.when(schemaObject.getPackage()).thenReturn("entity.mock");
        return schemaObject;
    }

    public MockedEntityBuilder<T> setFields(int count) {
        return setFields(count, false);
    }

    public MockedEntityBuilder<T> setFields(int count, boolean withJacksonFields) {
        if (count < 1) {
            Mockito.when(schemaObject.getFields()).thenReturn(Collections.emptyList());
            return this;
        }
        if (count == 1) {
            SchemaField mockedField = getMockedField(0);
            Mockito.when(schemaObject.getFields()).thenReturn(Collections.singletonList(mockedField));
            return this;
        }
        List<SchemaField> fieldList = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            if (withJacksonFields && i % 2 == 1) {
                fieldList.add(getMockedJacksonField(i));
            } else {
                fieldList.add(getMockedField(i));
            }
        }
        Mockito.when(schemaObject.getFields()).thenReturn(fieldList);
        return this;
    }

    public MockedEntityBuilder<T> setInterfaces(int count) {
        if (count < 1) {
            Mockito.when(schemaObject.getInterfaces()).thenReturn(Collections.emptyList());
            return this;
        }
        if (count == 1) {
            SchemaEntity mockedInterface = getMockedInterface(0);
            Mockito.when(schemaObject.getInterfaces()).thenReturn(Collections.singletonList(mockedInterface));
            return this;
        }
        List<SchemaEntity> interfaceList = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            interfaceList.add(getMockedInterface(i));
        }
        Mockito.when(schemaObject.getInterfaces()).thenReturn(interfaceList);
        return this;
    }

    public MockedEntityBuilder<T> setName(String classNamePostfix) {
        String name = SchemaInterface.class.isAssignableFrom(entityType) ? "MockedInterface" : "MockedClass";
        name += classNamePostfix;
        Mockito.when(schemaObject.getName()).thenReturn(name);
        return this;
    }

    public MockedEntityBuilder<T> setName() {
        return setName("");
    }

    public MockedEntityBuilder<T> setPrimitivesAndCollectionsToFields() {
        int counter = 0;
        List<SchemaField> fieldList = new ArrayList<>(40);
        for (PrimitiveSchemaEntity primitiveSchemaEntity : PrimitiveSchemaEntity.values()) {
            if (!PrimitiveSchemaEntity.VOID.equals(primitiveSchemaEntity)) {
                fieldList.add(getMockedField(counter, primitiveSchemaEntity));
                ++counter;
            }
        }
        for (CommonJavaSchemaEntity commonJavaSchemaEntity : CommonJavaSchemaEntity.values()) {
            if (!CommonJavaSchemaEntity.VOID.equals(commonJavaSchemaEntity)) {
                fieldList.add(getMockedField(counter, commonJavaSchemaEntity));
                ++counter;
            }
        }
        fieldList.add(getMockedField(counter++, new ArraySchemaEntity(CommonJavaSchemaEntity.STRING)));
        fieldList.add(getMockedField(counter++, new CollectionSchemaEntity(CommonJavaSchemaEntity.STRING)));
        fieldList.add(getMockedField(counter++, new CollectionSchemaEntity()));
        fieldList.add(getMockedField(counter++, new IterableSchemaEntity(CommonJavaSchemaEntity.STRING)));
        fieldList.add(getMockedField(counter++, new IterableSchemaEntity()));
        fieldList.add(getMockedField(counter++, new ListSchemaEntity(CommonJavaSchemaEntity.STRING)));
        fieldList.add(getMockedField(counter++, new ListSchemaEntity()));
        fieldList.add(getMockedField(counter++,
                new MapSchemaEntity(CommonJavaSchemaEntity.STRING, CommonJavaSchemaEntity.BIG_INTEGER)));
        fieldList.add(getMockedField(counter++, new MapSchemaEntity()));
        fieldList.add(getMockedField(counter++, new SetSchemaEntity(CommonJavaSchemaEntity.STRING)));
        fieldList.add(getMockedField(counter, new SetSchemaEntity()));
        Mockito.when(schemaObject.getFields()).thenReturn(fieldList);
        return this;
    }

    public MockedEntityBuilder<T> setSuperclass() {
        SchemaEntity superclassType = Mockito.mock(SchemaEntity.class);
        Mockito.when(superclassType.getName()).thenReturn("MockedSuperclass");
        Mockito.when(superclassType.getParameterizedDeclaration()).thenReturn("MockedSuperclass");
        Mockito.when(superclassType.getPackage()).thenReturn("superclass.mock");
        Mockito.when(schemaObject.getSuperclass()).thenReturn(superclassType);
        Mockito.when(schemaObject.hasSuperclass()).thenReturn(true);
        return this;
    }

    private SchemaField getMockedField(int counter) {
        return getMockedField(counter, getMockedFieldType(counter));
    }

    private SchemaField getMockedField(int counter, SchemaEntity schemaEntity) {
        SchemaField field = Mockito.mock(SchemaField.class);
        Mockito.when(field.getName()).thenReturn("mockedFieldName" + counter);
        Mockito.when(field.getType()).thenReturn(schemaEntity);
        return field;
    }

    private SchemaEntity getMockedFieldType(int counter) {
        SchemaEntity fieldType = Mockito.mock(SchemaEntity.class);
        Mockito.when(fieldType.getName()).thenReturn("MockedFieldType" + counter);
        Mockito.when(fieldType.getParameterizedDeclaration()).thenReturn("MockedFieldType" + counter);
        Mockito.when(fieldType.getPackage()).thenReturn("field.mock");
        return fieldType;
    }

    private SchemaEntity getMockedInterface(int counter) {
        SchemaEntity interfaceType = Mockito.mock(SchemaEntity.class);
        Mockito.when(interfaceType.getName()).thenReturn("MockedInterface" + counter);
        Mockito.when(interfaceType.getParameterizedDeclaration()).thenReturn("MockedInterface" + counter);
        Mockito.when(interfaceType.getPackage()).thenReturn("interface.mock");
        return interfaceType;
    }

    private TestJacksonSchemaField getMockedJacksonField(int counter) {
        TestJacksonSchemaField field = Mockito.mock(TestJacksonSchemaField.class);
        Mockito.when(field.getName()).thenReturn("mockedFieldName" + counter);
        SchemaEntity mockedFieldType = getMockedFieldType(counter);
        Mockito.when(field.getType()).thenReturn(mockedFieldType);
        Mockito.when(field.getNameForJackson()).thenReturn("mockedFieldName" + counter);
        return field;
    }
}
