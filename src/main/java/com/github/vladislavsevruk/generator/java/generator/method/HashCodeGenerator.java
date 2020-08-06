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
import com.github.vladislavsevruk.generator.java.type.SchemaField;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import com.github.vladislavsevruk.generator.java.util.EntityNameUtil;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Generates 'hashCode()' method.
 */
@EqualsAndHashCode(callSuper = true)
public class HashCodeGenerator extends BaseMethodGenerator {

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
        if (config.isUseLombokAnnotations()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("\n");
        List<SchemaField> schemaFields = schemaObject.getFields();
        addOverrideAnnotation(stringBuilder, config);
        stringBuilder.append(config.getIndent().value()).append("public int hashCode() {\n");
        doubleIndents(stringBuilder, config).append("int hashCode = 1;\n");
        if (schemaObject.hasSuperclass()) {
            doubleIndents(stringBuilder, config).append("hashCode = 59 * hashCode + super.hashCode();\n");
        }
        schemaFields.forEach(field -> addHashCodeForField(stringBuilder, config, field));
        doubleIndents(stringBuilder, config).append("return hashCode;\n");
        closeMethod(stringBuilder, config);
        return stringBuilder.toString();
    }

    private void addHashCodeForBoolean(StringBuilder stringBuilder, JavaClassGeneratorConfig config,
            String getterPrefix, String fieldName) {
        doubleIndents(stringBuilder, config).append("hashCode = 59 * hashCode + (").append(getterPrefix)
                .append(fieldName).append("() ? 79 : 97);\n");
    }

    private void addHashCodeForCastingInt(StringBuilder stringBuilder, JavaClassGeneratorConfig config,
            String getterPrefix, String fieldName) {
        doubleIndents(stringBuilder, config).append("hashCode = 59 * hashCode + ");
        finishLineByGetter(stringBuilder, getterPrefix, fieldName);
    }

    private void addHashCodeForDouble(StringBuilder stringBuilder, JavaClassGeneratorConfig config, String getterPrefix,
            String fieldName) {
        doubleIndents(stringBuilder, config).append("long a").append(fieldName).append(" = Double.doubleToLongBits(")
                .append(getterPrefix).append(fieldName).append("());\n");
        addHashCodeForLong(stringBuilder, config, fieldName);
    }

    private void addHashCodeForField(StringBuilder stringBuilder, JavaClassGeneratorConfig config, String javaType,
            String getterPrefix, String fieldName) {
        switch (javaType) {
            case "int":
            case "byte":
            case "short":
            case "char":
                addHashCodeForCastingInt(stringBuilder, config, getterPrefix, fieldName);
                break;
            case "boolean":
                addHashCodeForBoolean(stringBuilder, config, getterPrefix, fieldName);
                break;
            case "long":
                addHashCodeForLong(stringBuilder, config, getterPrefix, fieldName);
                break;
            case "double":
                addHashCodeForDouble(stringBuilder, config, getterPrefix, fieldName);
                break;
            case "float":
                addHashCodeForFloat(stringBuilder, config, getterPrefix, fieldName);
                break;
            default:
                addHashCodeForObject(stringBuilder, config, getterPrefix, fieldName);
        }
    }

    private void addHashCodeForField(StringBuilder stringBuilder, JavaClassGeneratorConfig config,
            SchemaField schemaField) {
        String fieldName = EntityNameUtil.uppercaseFirstLetter(schemaField.getName());
        String getterPrefix = getterPrefix(schemaField);
        fieldName = EntityNameUtil.uppercaseFirstLetter(fieldName);
        String javaType = schemaField.getType().getName();
        addHashCodeForField(stringBuilder, config, javaType, getterPrefix, fieldName);
    }

    private void addHashCodeForFloat(StringBuilder stringBuilder, JavaClassGeneratorConfig config, String getterPrefix,
            String fieldName) {
        doubleIndents(stringBuilder, config).append("hashCode = 59 * hashCode + Float.floatToIntBits(")
                .append(getterPrefix).append(fieldName).append("());\n");
    }

    private void addHashCodeForLong(StringBuilder stringBuilder, JavaClassGeneratorConfig config, String fieldName) {
        doubleIndents(stringBuilder, config).append("hashCode = 59 * hashCode + (int)(a").append(fieldName)
                .append(" >>> 32 ^ a").append(fieldName).append(");\n");
    }

    private void addHashCodeForLong(StringBuilder stringBuilder, JavaClassGeneratorConfig config, String getterPrefix,
            String fieldName) {
        doubleIndents(stringBuilder, config).append("long a").append(fieldName).append(" = ");
        finishLineByGetter(stringBuilder, getterPrefix, fieldName);
        addHashCodeForLong(stringBuilder, config, fieldName);
    }

    private void addHashCodeForObject(StringBuilder stringBuilder, JavaClassGeneratorConfig config, String getterPrefix,
            String fieldName) {
        doubleIndents(stringBuilder, config).append("Object a").append(fieldName).append(" = ");
        finishLineByGetter(stringBuilder, getterPrefix, fieldName);
        doubleIndents(stringBuilder, config).append("hashCode = 59 * hashCode + (a").append(fieldName)
                .append(" == null ? 0 : a").append(fieldName).append(".hashCode());\n");
    }

    private void finishLineByGetter(StringBuilder stringBuilder, String getterPrefix, String fieldName) {
        stringBuilder.append(getterPrefix).append(fieldName).append("();\n");
    }
}
