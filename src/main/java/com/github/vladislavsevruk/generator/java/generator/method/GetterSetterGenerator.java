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
import com.github.vladislavsevruk.generator.java.generator.FieldAnnotationGenerator;
import com.github.vladislavsevruk.generator.java.type.SchemaField;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import com.github.vladislavsevruk.generator.java.util.DefaultValueUtil;
import com.github.vladislavsevruk.generator.java.util.EntityNameUtil;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

/**
 * Generates getter and setter methods for class fields.
 */
@EqualsAndHashCode(callSuper = true)
public class GetterSetterGenerator extends BaseMethodGenerator {

    private List<FieldAnnotationGenerator> getterAnnotationGenerators;
    private List<FieldAnnotationGenerator> setterAnnotationGenerators;

    public GetterSetterGenerator(List<FieldAnnotationGenerator> getterAnnotationGenerators,
            List<FieldAnnotationGenerator> setterAnnotationGenerators) {
        this.getterAnnotationGenerators = DefaultValueUtil
                .orDefault(getterAnnotationGenerators, Collections::emptyList);
        this.setterAnnotationGenerators = DefaultValueUtil
                .orDefault(setterAnnotationGenerators, Collections::emptyList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
        if (config.isUseLombokAnnotations()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (SchemaField field : schemaObject.getFields()) {
            addGetter(stringBuilder, config, field);
            addSetter(stringBuilder, config, schemaObject, field);
        }
        return stringBuilder.toString();
    }

    private void addGetter(StringBuilder stringBuilder, JavaClassGeneratorConfig config, SchemaField field) {
        stringBuilder.append("\n");
        String javaFieldName = field.getName();
        getterAnnotationGenerators.forEach(generator -> stringBuilder.append(generator.generate(config, field)));
        String javaType = field.getType().getParameterizedDeclaration();
        String getterPrefix = getterPrefix(javaType);
        stringBuilder.append(config.getIndent().value()).append("public ").append(javaType).append(" ")
                .append(getterPrefix).append(EntityNameUtil.uppercaseFirstLetter(javaFieldName)).append("() {\n");
        doubleIndents(stringBuilder, config).append("return ").append(javaFieldName).append(";\n");
        closeMethod(stringBuilder, config);
    }

    private void addSetter(StringBuilder stringBuilder, JavaClassGeneratorConfig config, SchemaObject schemaObject,
            SchemaField field) {
        stringBuilder.append("\n");
        String javaFieldName = field.getName();
        setterAnnotationGenerators.forEach(generator -> stringBuilder.append(generator.generate(config, field)));
        stringBuilder.append(config.getIndent().value()).append("public ")
                .append(schemaObject.getParameterizedDeclaration()).append(" ").append("set")
                .append(EntityNameUtil.uppercaseFirstLetter(javaFieldName)).append("(")
                .append(field.getType().getParameterizedDeclaration()).append(" ").append(javaFieldName)
                .append(") {\n");
        doubleIndents(stringBuilder, config).append("this.").append(javaFieldName).append(" = ").append(javaFieldName)
                .append(";\n");
        doubleIndents(stringBuilder, config).append("return this;\n");
        closeMethod(stringBuilder, config);
    }
}
