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
package com.github.vladislavsevruk.generator.java.storage;

import com.github.vladislavsevruk.generator.java.provider.JavaClassContentGeneratorProvider;

import java.util.List;

/**
 * Contains class content generators for different java types.
 *
 * @see JavaClassContentGeneratorProvider
 */
public interface ClassContentGeneratorProviderStorage {

    /**
     * Adds new <code>ClassContentGeneratorProvider</code> to list. Class content generator will not be added provided
     * it is <code>null</code> or list already contains content generator of such type.
     *
     * @param customClassContentGeneratorProvider custom class content generator to add.
     */
    void add(JavaClassContentGeneratorProvider customClassContentGeneratorProvider);

    /**
     * Adds new <code>ClassContentGeneratorProvider</code> to list after specified <code>ClassContentGeneratorProvider</code>
     * or at list end provided target <code>ClassContentGeneratorProvider</code> is not present at storage. New class
     * content generator will not be added provided it is <code>null</code> or list already contains class content
     * generator of such type.
     *
     * @param customClassContentGeneratorProvider custom class content generator to add.
     * @param targetType                          type after which new class content generator should be added.
     */
    void addAfter(JavaClassContentGeneratorProvider customClassContentGeneratorProvider,
            Class<? extends JavaClassContentGeneratorProvider> targetType);

    /**
     * Adds new <code>ClassContentGeneratorProvider</code> to list before specified
     * <code>ClassContentGeneratorProvider</code> or at list end provided target <code>ClassContentGeneratorProvider</code>
     * is not present at storage. New class content generator will not be added provided it is <code>null</code> or list
     * already contains class content generator of such type.
     *
     * @param customClassContentGeneratorProvider custom class content generator to add.
     * @param targetType                          type after which new class content generator should be added.
     */
    void addBefore(JavaClassContentGeneratorProvider customClassContentGeneratorProvider,
            Class<? extends JavaClassContentGeneratorProvider> targetType);

    /**
     * Returns list of all <code>ClassContentGeneratorProvider</code>-s that are present at storage.
     */
    List<JavaClassContentGeneratorProvider> getAll();
}
