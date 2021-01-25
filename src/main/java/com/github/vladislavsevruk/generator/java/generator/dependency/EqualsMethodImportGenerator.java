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
package com.github.vladislavsevruk.generator.java.generator.dependency;

import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.generator.ClassElementCollectionGenerator;
import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import com.github.vladislavsevruk.generator.java.type.SchemaField;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import com.github.vladislavsevruk.generator.java.type.predefined.PrimitiveSchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.sequence.ArraySchemaEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Generates imports for equals method.
 */
public class EqualsMethodImportGenerator implements ClassElementCollectionGenerator {

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> generate(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
        if (config.isUseLombokAnnotations() || schemaObject.getFields().isEmpty()) {
            return Collections.emptyList();
        }
        List<String> imports = new ArrayList<>();
        if (hasNonPrimitives(schemaObject.getFields())) {
            imports.add("import java.util.Objects;\n");
        }
        if (hasArrays(schemaObject.getFields())) {
            imports.add("import java.util.Arrays;\n");
        }
        return imports;
    }

    private boolean hasArrays(List<SchemaField> fields) {
        return fields.stream().anyMatch(field -> ArraySchemaEntity.class.equals(field.getType().getClass()));
    }

    private boolean hasNonPrimitives(List<SchemaField> fields) {
        return fields.stream().anyMatch(
                field -> !ArraySchemaEntity.class.equals(field.getType().getClass()) && isNonPrimitive(
                        field.getType()));
    }

    private boolean isNonPrimitive(SchemaEntity type) {
        for (PrimitiveSchemaEntity primitiveSchemaEntity : PrimitiveSchemaEntity.values()) {
            if (primitiveSchemaEntity.equals(type)) {
                return false;
            }
        }
        return true;
    }
}
