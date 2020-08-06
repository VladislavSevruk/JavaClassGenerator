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
package com.github.vladislavsevruk.generator.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EntityNameUtilTest {

    @Test
    void javaFormatClassNameFromLowerCamelcaseTest() {
        String input = "testValue";
        String result = EntityNameUtil.getJavaFormatClassName(input);
        Assertions.assertEquals("TestValue", result);
    }

    @Test
    void javaFormatClassNameFromLowercaseWithUnderscoresTest() {
        String input = "test_value";
        String result = EntityNameUtil.getJavaFormatClassName(input);
        Assertions.assertEquals("TestValue", result);
    }

    @Test
    void javaFormatClassNameFromUpperCamelcaseTest() {
        String input = "TestValue";
        String result = EntityNameUtil.getJavaFormatClassName(input);
        Assertions.assertEquals("TestValue", result);
    }

    @Test
    void javaFormatClassNameFromUppercaseWithUnderscoresTest() {
        String input = "Test_Value";
        String result = EntityNameUtil.getJavaFormatClassName(input);
        Assertions.assertEquals("TestValue", result);
    }

    @Test
    void javaFormatFieldNameFromLowerCamelcaseTest() {
        String input = "testValue";
        String result = EntityNameUtil.getJavaFormatFieldName(input);
        Assertions.assertEquals("testValue", result);
    }

    @Test
    void javaFormatFieldNameFromLowercaseWithUnderscoresTest() {
        String input = "test_value";
        String result = EntityNameUtil.getJavaFormatFieldName(input);
        Assertions.assertEquals("testValue", result);
    }

    @Test
    void javaFormatFieldNameFromUpperCamelcaseTest() {
        String input = "TestValue";
        String result = EntityNameUtil.getJavaFormatFieldName(input);
        Assertions.assertEquals("testValue", result);
    }

    @Test
    void javaFormatFieldNameFromUppercaseWithUnderscoresTest() {
        String input = "Test_Value";
        String result = EntityNameUtil.getJavaFormatFieldName(input);
        Assertions.assertEquals("testValue", result);
    }
}
