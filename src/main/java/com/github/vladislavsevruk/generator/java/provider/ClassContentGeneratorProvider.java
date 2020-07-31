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
import com.github.vladislavsevruk.generator.java.generator.FieldAnnotationGenerator;
import com.github.vladislavsevruk.generator.java.generator.declaration.ClassDeclarationGenerator;
import com.github.vladislavsevruk.generator.java.generator.declaration.LombokClassAnnotationGenerator;
import com.github.vladislavsevruk.generator.java.generator.dependency.FieldTypeImportGenerator;
import com.github.vladislavsevruk.generator.java.generator.dependency.InterfaceImportGenerator;
import com.github.vladislavsevruk.generator.java.generator.dependency.JacksonImportGenerator;
import com.github.vladislavsevruk.generator.java.generator.dependency.LombokImportGenerator;
import com.github.vladislavsevruk.generator.java.generator.dependency.ObjectsImportGenerator;
import com.github.vladislavsevruk.generator.java.generator.dependency.SuperclassImportGenerator;
import com.github.vladislavsevruk.generator.java.generator.field.ClassFieldGenerator;
import com.github.vladislavsevruk.generator.java.generator.field.JacksonFieldAnnotationGenerator;
import com.github.vladislavsevruk.generator.java.generator.method.EqualsGenerator;
import com.github.vladislavsevruk.generator.java.generator.method.GetterSetterGenerator;
import com.github.vladislavsevruk.generator.java.generator.method.HashCodeGenerator;
import com.github.vladislavsevruk.generator.java.generator.method.ToStringGenerator;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of <code>JavaClassContentGeneratorProvider</code> for common class generation.
 *
 * @see JavaClassContentGeneratorProvider
 */
public class ClassContentGeneratorProvider extends AbstractJavaClassContentGeneratorProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(SchemaObject schemaObject) {
        return true;
    }

    @Override
    protected List<ClassElementGenerator> getDefaultClassAnnotationGenerators() {
        List<ClassElementGenerator> classAnnotationGenerators = new ArrayList<>();
        classAnnotationGenerators.add(new LombokClassAnnotationGenerator());
        return classAnnotationGenerators;
    }

    @Override
    protected ClassElementGenerator getDefaultClassDeclarationGenerator(
            List<ClassElementGenerator> defaultClassAnnotationGenerators) {
        return new ClassDeclarationGenerator(getDefaultClassAnnotationGenerators());
    }

    @Override
    protected List<ClassElementGenerator> getDefaultConstructorGenerators() {
        return new ArrayList<>();
    }

    protected List<FieldAnnotationGenerator> getDefaultFieldAnnotationGenerators() {
        List<FieldAnnotationGenerator> defaultFieldAnnotationGenerators = new ArrayList<>();
        defaultFieldAnnotationGenerators.add(new JacksonFieldAnnotationGenerator());
        return defaultFieldAnnotationGenerators;
    }

    @Override
    protected List<ClassElementGenerator> getDefaultFieldGenerators() {
        List<ClassElementGenerator> defaultFieldGenerators = new ArrayList<>();
        defaultFieldGenerators.add(new ClassFieldGenerator(getDefaultFieldAnnotationGenerators()));
        return defaultFieldGenerators;
    }

    protected List<FieldAnnotationGenerator> getDefaultGetterAnnotationGenerators() {
        return new ArrayList<>();
    }

    @Override
    protected List<ClassImportGenerator> getDefaultImportGenerators() {
        List<ClassImportGenerator> defaultImportGenerators = new ArrayList<>();
        defaultImportGenerators.add(new JacksonImportGenerator());
        defaultImportGenerators.add(new SuperclassImportGenerator());
        defaultImportGenerators.add(new InterfaceImportGenerator());
        defaultImportGenerators.add(new FieldTypeImportGenerator());
        defaultImportGenerators.add(new LombokImportGenerator());
        defaultImportGenerators.add(new ObjectsImportGenerator());
        return defaultImportGenerators;
    }

    @Override
    protected List<ClassElementGenerator> getDefaultMethodGenerators() {
        List<ClassElementGenerator> defaultMethodGenerators = new ArrayList<>();
        defaultMethodGenerators.add(new GetterSetterGenerator(getDefaultGetterAnnotationGenerators(),
                getDefaultSetterAnnotationGenerators()));
        defaultMethodGenerators.add(new EqualsGenerator());
        defaultMethodGenerators.add(new HashCodeGenerator());
        defaultMethodGenerators.add(new ToStringGenerator());
        return defaultMethodGenerators;
    }

    protected List<FieldAnnotationGenerator> getDefaultSetterAnnotationGenerators() {
        return new ArrayList<>();
    }
}
