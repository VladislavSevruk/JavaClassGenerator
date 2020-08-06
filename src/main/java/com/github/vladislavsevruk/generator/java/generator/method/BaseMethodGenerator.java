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
import com.github.vladislavsevruk.generator.java.generator.ClassElementGenerator;
import com.github.vladislavsevruk.generator.java.type.SchemaField;
import lombok.EqualsAndHashCode;

/**
 * Contains common methods for class methods generation.
 */
@EqualsAndHashCode
public abstract class BaseMethodGenerator implements ClassElementGenerator {

    protected void addOverrideAnnotation(StringBuilder stringBuilder, JavaClassGeneratorConfig config) {
        stringBuilder.append(config.getIndent().value()).append("@Override\n");
    }

    protected void closeMethod(StringBuilder stringBuilder, JavaClassGeneratorConfig config) {
        stringBuilder.append(config.getIndent().value()).append("}\n");
    }

    protected StringBuilder doubleIndents(StringBuilder stringBuilder, JavaClassGeneratorConfig config) {
        String indent = config.getIndent().value();
        return stringBuilder.append(indent).append(indent);
    }

    protected String getterPrefix(SchemaField schemaField) {
        return getterPrefix(schemaField.getType().getName());
    }

    protected String getterPrefix(String javaType) {
        return "boolean".equals(javaType) ? "is" : "get";
    }
}
