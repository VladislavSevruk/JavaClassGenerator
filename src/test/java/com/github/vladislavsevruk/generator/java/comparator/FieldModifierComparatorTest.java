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
package com.github.vladislavsevruk.generator.java.comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class FieldModifierComparatorTest {

    @Test
    void fieldModifierComparatorTest() {
        List<String> fields = Arrays.asList("\nprivate final volatile String ...;", "    private final String ...;",
                "private transient String ...;", "private String ...;", "private static String ...;",
                "private static final String ...;", "final String ...;", "String ...;", "static String ...;",
                "static final String ...;", "protected String ...;", "protected final String ...;",
                "protected static String ...;", "protected static final String ...;", "public String ...;",
                "public final String ...;", "public static String ...;", "public static final String ...;");
        fields.sort(new FieldModifierComparator());
        List<String> expectedOrder = Arrays
                .asList("public static final String ...;", "public static String ...;", "public String ...;",
                        "public final String ...;", "protected static final String ...;",
                        "protected static String ...;", "protected String ...;", "protected final String ...;",
                        "static final String ...;", "static String ...;", "final String ...;", "String ...;",
                        "private static final String ...;", "private static String ...;",
                        "\nprivate final volatile String ...;", "    private final String ...;",
                        "private transient String ...;", "private String ...;");
        for (int i = 0; i < fields.size(); ++i) {
            Assertions.assertEquals(expectedOrder.get(i), fields.get(i));
        }
    }
}
