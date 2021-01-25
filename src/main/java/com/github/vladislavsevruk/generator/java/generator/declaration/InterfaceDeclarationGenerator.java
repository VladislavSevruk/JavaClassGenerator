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
package com.github.vladislavsevruk.generator.java.generator.declaration;

import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.generator.ClassElementGenerator;
import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates declaration line for Java interface.
 */
public class InterfaceDeclarationGenerator extends BaseDeclarationGenerator {

    public InterfaceDeclarationGenerator(List<ClassElementGenerator> interfaceAnnotationGenerators) {
        super(interfaceAnnotationGenerators);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
        StringBuilder stringBuilder = new StringBuilder("\n");
        addAnnotations(stringBuilder, config, schemaObject);
        stringBuilder.append("public interface ").append(schemaObject.getParameterizedDeclaration());
        List<SchemaEntity> interfaces = schemaObject.getInterfaces();
        if (!interfaces.isEmpty()) {
            String interfacesDeclaration = interfaces.stream().map(SchemaEntity::getParameterizedDeclaration)
                    .collect(Collectors.joining(", "));
            stringBuilder.append(" extends ").append(interfacesDeclaration);
        }
        stringBuilder.append(" {\n");
        return stringBuilder.toString();
    }
}