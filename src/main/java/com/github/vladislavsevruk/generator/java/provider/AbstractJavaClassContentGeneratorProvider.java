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
package com.github.vladislavsevruk.generator.java.provider;

import com.github.vladislavsevruk.generator.java.generator.ClassElementGenerator;
import com.github.vladislavsevruk.generator.java.generator.ClassImportGenerator;
import com.github.vladislavsevruk.generator.java.generator.declaration.PackageGenerator;
import lombok.Getter;

import java.util.List;

/**
 * Abstract java class content generators provider with common logic.
 *
 * @see JavaClassContentGeneratorProvider
 */
public abstract class AbstractJavaClassContentGeneratorProvider implements JavaClassContentGeneratorProvider {

    @Getter
    private ClassElementGenerator classDeclarationGenerator = getDefaultClassDeclarationGenerator(
            getDefaultClassAnnotationGenerators());
    @Getter
    private List<ClassElementGenerator> constructorGenerators = getDefaultConstructorGenerators();
    @Getter
    private List<ClassElementGenerator> fieldGenerators = getDefaultFieldGenerators();
    @Getter
    private List<ClassImportGenerator> importGenerators = getDefaultImportGenerators();
    @Getter
    private List<ClassElementGenerator> methodGenerators = getDefaultMethodGenerators();
    @Getter
    private ClassElementGenerator packageGenerator = new PackageGenerator();

    protected abstract List<ClassElementGenerator> getDefaultClassAnnotationGenerators();

    protected abstract ClassElementGenerator getDefaultClassDeclarationGenerator(
            List<ClassElementGenerator> defaultClassAnnotationGenerators);

    protected abstract List<ClassElementGenerator> getDefaultConstructorGenerators();

    protected abstract List<ClassElementGenerator> getDefaultFieldGenerators();

    protected abstract List<ClassImportGenerator> getDefaultImportGenerators();

    protected abstract List<ClassElementGenerator> getDefaultMethodGenerators();
}
