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

import com.github.vladislavsevruk.generator.java.generator.ClassElementCollectionGenerator;
import com.github.vladislavsevruk.generator.java.generator.ClassElementGenerator;
import com.github.vladislavsevruk.generator.java.generator.declaration.EnumDeclarationGenerator;
import com.github.vladislavsevruk.generator.java.generator.dependency.InterfaceImportGenerator;
import com.github.vladislavsevruk.generator.java.generator.field.EnumConstantGenerator;
import com.github.vladislavsevruk.generator.java.type.SchemaEnum;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of <code>JavaClassContentGeneratorProvider</code> for enumerations generation.
 *
 * @see JavaClassContentGeneratorProvider
 */
@EqualsAndHashCode(callSuper = true)
public class EnumContentGeneratorProvider extends AbstractJavaClassContentGeneratorProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(SchemaObject schemaObject) {
        return SchemaEnum.class.isAssignableFrom(schemaObject.getClass());
    }

    @Override
    protected List<ClassElementGenerator> getDefaultClassAnnotationGenerators() {
        return new ArrayList<>();
    }

    @Override
    protected ClassElementGenerator getDefaultClassDeclarationGenerator(
            List<ClassElementGenerator> defaultClassAnnotationGenerators) {
        return new EnumDeclarationGenerator(getDefaultClassAnnotationGenerators());
    }

    @Override
    protected List<ClassElementGenerator> getDefaultConstructorGenerators() {
        return new ArrayList<>();
    }

    @Override
    protected List<ClassElementCollectionGenerator> getDefaultFieldGenerators() {
        List<ClassElementCollectionGenerator> defaultFieldGenerators = new ArrayList<>();
        defaultFieldGenerators.add(new EnumConstantGenerator());
        return defaultFieldGenerators;
    }

    @Override
    protected List<ClassElementCollectionGenerator> getDefaultImportGenerators() {
        List<ClassElementCollectionGenerator> defaultImportGenerators = new ArrayList<>();
        defaultImportGenerators.add(new InterfaceImportGenerator());
        return defaultImportGenerators;
    }

    @Override
    protected List<ClassElementGenerator> getDefaultMethodGenerators() {
        return new ArrayList<>();
    }
}
