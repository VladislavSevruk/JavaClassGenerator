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
package com.github.vladislavsevruk.generator.java.generator.field;

import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.generator.ClassElementCollectionGenerator;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import com.github.vladislavsevruk.generator.java.type.SchemaUnit;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Generates Java enumeration constant fields.
 */
public class EnumConstantGenerator implements ClassElementCollectionGenerator {

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> generate(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
        StringBuilder stringBuilder = new StringBuilder();
        String values = schemaObject.getFields().stream().map(SchemaUnit::getName)
                .collect(Collectors.joining(",\n" + config.getIndent().value()));
        if (!values.isEmpty()) {
            stringBuilder.append(config.getIndent().value()).append(values).append("\n");
        }
        return Collections.singletonList(stringBuilder.toString());
    }
}
