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
package com.github.vladislavsevruk.generator.java.generator.method;

import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import com.github.vladislavsevruk.generator.java.type.SchemaField;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import com.github.vladislavsevruk.generator.java.type.predefined.sequence.ArraySchemaEntity;
import com.github.vladislavsevruk.generator.java.util.EntityNameUtil;

import java.util.List;

/**
 * Generates 'equals(Object)' method.
 */
public class EqualsGenerator extends BaseMethodGenerator {

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
        if (config.isUseLombokAnnotations()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("\n");
        String objectName = schemaObject.getName();
        List<SchemaField> schemaFields = schemaObject.getFields();
        addOverrideAnnotation(stringBuilder, config);
        String indent = config.getIndent().value();
        stringBuilder.append(indent).append("public boolean equals(Object o) {\n");
        doubleIndents(stringBuilder, config).append("if (o == this) {\n");
        doubleIndents(stringBuilder, config).append(indent).append("return true;\n");
        doubleIndents(stringBuilder, config).append("}\n");
        addEqualsCondition(stringBuilder, config, String.format("!(o instanceof %s)", objectName));
        doubleIndents(stringBuilder, config).append(objectName).append(" other = (").append(objectName)
                .append(") o;\n");
        if (schemaObject.hasSuperclass()) {
            addEqualsCondition(stringBuilder, config, "!super.equals(other)");
        }
        schemaFields.forEach(field -> addEqualsForField(stringBuilder, config, field));
        doubleIndents(stringBuilder, config).append("return true;\n");
        closeMethod(stringBuilder, config);
        return stringBuilder.toString();
    }

    private void addEqualsCondition(StringBuilder stringBuilder, JavaClassGeneratorConfig config, String condition) {
        doubleIndents(stringBuilder, config).append("if (").append(condition).append(") {\n");
        doubleIndents(stringBuilder, config).append(config.getIndent().value()).append("return false;\n");
        doubleIndents(stringBuilder, config).append("}\n");
    }

    private void addEqualsForField(StringBuilder stringBuilder, JavaClassGeneratorConfig config,
            SchemaField schemaField) {
        String fieldName = EntityNameUtil.uppercaseFirstLetter(schemaField.getName());
        addEqualsCondition(stringBuilder, config, getFieldEqualsCondition(schemaField.getType(), fieldName));
    }

    private String getFieldEqualsCondition(SchemaEntity javaType, String fieldName) {
        if (ArraySchemaEntity.class.equals(javaType.getClass())) {
            return String.format("!Arrays.equals(get%s(), other.get%<s())", fieldName);
        }
        switch (javaType.getName()) {
            case "boolean":
                return String.format("this.is%s() != other.is%<s()", fieldName);
            case "byte":
            case "char":
            case "int":
            case "long":
            case "short":
                return String.format("this.get%s() != other.get%<s()", fieldName);
            case "double":
                return String.format("Double.compare(this.get%s(), other.get%<s()) != 0", fieldName);
            case "float":
                return String.format("Float.compare(this.get%s(), other.get%<s()) != 0", fieldName);
            default:
                return String.format("!Objects.equals(get%s(), other.get%<s())", fieldName);
        }
    }
}
