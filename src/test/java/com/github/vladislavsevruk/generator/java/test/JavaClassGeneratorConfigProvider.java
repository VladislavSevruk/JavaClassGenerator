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
package com.github.vladislavsevruk.generator.java.test;

import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfigBuilder;
import com.github.vladislavsevruk.generator.java.constant.Indent;

public final class JavaClassGeneratorConfigProvider {

    private JavaClassGeneratorConfigProvider() {
    }

    public static JavaClassGeneratorConfig getDefault() {
        return defaultSettings().build();
    }

    public static JavaClassGeneratorConfig getWithJacksonAnnotations() {
        return defaultSettings().addJacksonAnnotations(true).build();
    }

    public static JavaClassGeneratorConfig getWithNonDefaultIndents() {
        return defaultSettings().useIndent(Indent.TAB).build();
    }

    public static JavaClassGeneratorConfig getWithoutEmptyLinesBetweenFields() {
        return defaultSettings().addEmptyLineBetweenFields(false).build();
    }

    public static JavaClassGeneratorConfig getWithoutFieldsSortingByModifiers() {
        return defaultSettings().sortFieldsByModifiers(false).build();
    }

    public static JavaClassGeneratorConfig getWithoutLombokAnnotations() {
        return defaultSettings().useLombokAnnotations(false).build();
    }

    private static JavaClassGeneratorConfigBuilder defaultSettings() {
        return JavaClassGeneratorConfig.builder().pathToTargetFolder(TestConstant.PATH_TO_TEMP_FOLDER);
    }
}
