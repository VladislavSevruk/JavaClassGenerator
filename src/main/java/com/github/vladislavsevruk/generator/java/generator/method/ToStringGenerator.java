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

import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates 'toString()' method.
 */
public class ToStringGenerator extends BaseMethodGenerator {

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
        if (config.isUseLombokAnnotations()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        List<SchemaField> schemaFields = schemaObject.getFields();
        addOverrideAnnotation(stringBuilder, config);
        stringBuilder.append(config.getIndent().value()).append("public String toString() {\n");
        doubleIndents(stringBuilder, config).append("return String.format(\"").append(schemaObject.getName())
                .append("(");
        String pattern = schemaFields.stream().map(field -> field.getName() + "=%s").collect(Collectors.joining(", "));
        if (schemaObject.hasSuperclass()) {
            stringBuilder.append("super=%s");
            if (!pattern.isEmpty()) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(pattern).append(")\", ");
        String fieldsSequence = schemaFields.stream().map(field -> String
                .format("%s%s()", getterPrefix(field), EntityNameUtil.uppercaseFirstLetter(field.getName())))
                .collect(Collectors.joining(", "));
        if (schemaObject.hasSuperclass()) {
            stringBuilder.append("super.toString()");
            if (!fieldsSequence.isEmpty()) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(fieldsSequence).append(");\n");
        closeMethod(stringBuilder, config);
        return stringBuilder.toString();
    }
}
