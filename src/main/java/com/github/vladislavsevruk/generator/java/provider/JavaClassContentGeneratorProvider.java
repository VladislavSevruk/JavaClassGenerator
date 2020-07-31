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
import com.github.vladislavsevruk.generator.java.type.SchemaObject;

import java.util.Collection;

/**
 * Provides generators for class content generation.
 */
public interface JavaClassContentGeneratorProvider {

    /**
     * Returns <code>ClassElementGenerator</code> for class declaration generation.
     */
    ClassElementGenerator getClassDeclarationGenerator();

    /**
     * Returns <code>Collection</code> of <code>ClassElementGenerator</code> for class constructors generation.
     */
    Collection<ClassElementGenerator> getConstructorGenerators();

    /**
     * Returns <code>Collection</code> of <code>ClassElementGenerator</code> for class fields generation.
     */
    Collection<ClassElementGenerator> getFieldGenerators();

    /**
     * Returns <code>Collection</code> of <code>ClassImportGenerator</code> for class imports generation.
     */
    Collection<ClassImportGenerator> getImportGenerators();

    /**
     * Returns <code>Collection</code> of <code>ClassElementGenerator</code> for class methods generation.
     */
    Collection<ClassElementGenerator> getMethodGenerators();

    /**
     * Returns <code>ClassElementGenerator</code> for package declaration generation.
     */
    ClassElementGenerator getPackageGenerator();

    /**
     * Checks if this provider contains necessary generators for received class schema.
     *
     * @param schemaObject <code>SchemaObject</code> to check.
     * @return <code>true</code> if this provider contains necessary generators for received <code>SchemaObject</code>
     * type, <code>false otherwise</code>.
     */
    boolean matches(SchemaObject schemaObject);
}
