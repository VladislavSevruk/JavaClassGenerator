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
package com.github.vladislavsevruk.generator.java.config;

import com.github.vladislavsevruk.generator.java.constant.Indent;
import com.github.vladislavsevruk.generator.java.util.DefaultValueUtil;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.File;

/**
 * Builds immutable <code>JavaClassGeneratorConfigBuilder</code> instance with set parameters.
 */
@Accessors(fluent = true)
@Setter
public class JavaClassGeneratorConfigBuilder {

    private boolean addEmptyLineBetweenFields = true;
    private boolean addJacksonAnnotations = false;
    private String pathToTargetFolder;
    private boolean sortFieldsByModifiers = true;
    private Indent useIndent;
    private boolean useLombokAnnotations = true;

    JavaClassGeneratorConfigBuilder() {
    }

    /**
     * Builds <code>JavaClassGeneratorConfig</code> with set parameters.
     */
    public JavaClassGeneratorConfig build() {
        pathToTargetFolder = DefaultValueUtil.orDefault(pathToTargetFolder, getDefaultTargetFolder());
        useIndent = DefaultValueUtil.orDefault(useIndent, Indent.SPACES_4);
        return new JavaClassGeneratorConfig(addEmptyLineBetweenFields, addJacksonAnnotations, useIndent,
                pathToTargetFolder, sortFieldsByModifiers, useLombokAnnotations);
    }

    private String getDefaultTargetFolder() {
        String dirPath = System.getProperty("user.dir");
        if (!dirPath.endsWith(File.separator)) {
            dirPath += File.separator;
        }
        return dirPath + "model";
    }
}
