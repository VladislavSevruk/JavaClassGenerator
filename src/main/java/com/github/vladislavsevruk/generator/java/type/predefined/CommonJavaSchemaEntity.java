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
package com.github.vladislavsevruk.generator.java.type.predefined;

import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Contains implementations of schema for common java entities.
 */
@AllArgsConstructor
public enum CommonJavaSchemaEntity implements SchemaEntity {

    BIG_DECIMAL("BigDecimal", "java.math"),
    BIG_INTEGER("BigInteger", "java.math"),
    BOOLEAN("Boolean"),
    BYTE("Byte"),
    CHARACTER("Character"),
    CHAR_SEQUENCE("CharSequence"),
    DOUBLE("Double"),
    FLOAT("Float"),
    INTEGER("Integer"),
    LONG("Long"),
    NUMBER("Number"),
    OBJECT("Object"),
    SHORT("Short"),
    STRING("String"),
    VOID("Void");

    @Getter
    private String name;
    private String packageName;

    CommonJavaSchemaEntity(String name) {
        // null for 'java.lang' package to avoid explicit import generation
        this(name, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPackage() {
        return packageName;
    }
}
