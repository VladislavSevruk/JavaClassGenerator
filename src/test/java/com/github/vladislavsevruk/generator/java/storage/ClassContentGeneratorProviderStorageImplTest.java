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

import com.github.vladislavsevruk.generator.java.provider.ClassContentGeneratorProvider;
import com.github.vladislavsevruk.generator.java.provider.EnumContentGeneratorProvider;
import com.github.vladislavsevruk.generator.java.provider.JavaClassContentGeneratorProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClassContentGeneratorProviderStorageImplTest {

    @Mock
    private JavaClassContentGeneratorProvider classContentGeneratorProvider;
    @Mock
    private JavaClassContentGeneratorProvider nonPresentClassContentGeneratorProvider;
    private JavaClassContentGeneratorProvider presentClassContentGeneratorProvider
            = new ClassContentGeneratorProvider();
    private Class<? extends JavaClassContentGeneratorProvider> presentClassContentGeneratorProviderClass
            = EnumContentGeneratorProvider.class;

    @Test
    void addExistentClassContentGeneratorProviderAfterExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage
                .addAfter(presentClassContentGeneratorProvider, presentClassContentGeneratorProviderClass);
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentClassContentGeneratorProviderAfterNonExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage
                .addAfter(presentClassContentGeneratorProvider, nonPresentClassContentGeneratorProvider.getClass());
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentClassContentGeneratorProviderAfterNullTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.addAfter(presentClassContentGeneratorProvider, null);
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentClassContentGeneratorProviderBeforeExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage
                .addBefore(presentClassContentGeneratorProvider, presentClassContentGeneratorProviderClass);
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentClassContentGeneratorProviderBeforeNonExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage
                .addBefore(presentClassContentGeneratorProvider, nonPresentClassContentGeneratorProvider.getClass());
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentClassContentGeneratorProviderBeforeNullTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.addBefore(presentClassContentGeneratorProvider, null);
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentClassContentGeneratorProviderTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.add(presentClassContentGeneratorProvider);
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNewClassContentGeneratorProviderAfterExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage
                .addAfter(classContentGeneratorProvider, presentClassContentGeneratorProviderClass);
        List<JavaClassContentGeneratorProvider> providers = classContentGeneratorProviderStorage.getAll();
        int sizeAfterAdd = providers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(classContentGeneratorProvider, providers.get(1));
    }

    @Test
    void addNewClassContentGeneratorProviderAfterNonExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage
                .addAfter(classContentGeneratorProvider, nonPresentClassContentGeneratorProvider.getClass());
        List<JavaClassContentGeneratorProvider> providers = classContentGeneratorProviderStorage.getAll();
        int sizeAfterAdd = providers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(classContentGeneratorProvider, providers.get(sizeAfterAdd - 1));
    }

    @Test
    void addNewClassContentGeneratorProviderAfterNullTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.addAfter(classContentGeneratorProvider, null);
        List<JavaClassContentGeneratorProvider> providers = classContentGeneratorProviderStorage.getAll();
        int sizeAfterAdd = providers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(classContentGeneratorProvider, providers.get(sizeAfterAdd - 1));
    }

    @Test
    void addNewClassContentGeneratorProviderBeforeExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage
                .addBefore(classContentGeneratorProvider, presentClassContentGeneratorProviderClass);
        List<JavaClassContentGeneratorProvider> providers = classContentGeneratorProviderStorage.getAll();
        int sizeAfterAdd = providers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(classContentGeneratorProvider, providers.get(0));
    }

    @Test
    void addNewClassContentGeneratorProviderBeforeNonExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage
                .addBefore(classContentGeneratorProvider, nonPresentClassContentGeneratorProvider.getClass());
        List<JavaClassContentGeneratorProvider> providers = classContentGeneratorProviderStorage.getAll();
        int sizeAfterAdd = providers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(classContentGeneratorProvider, providers.get(sizeAfterAdd - 1));
    }

    @Test
    void addNewClassContentGeneratorProviderBeforeNullTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.addBefore(classContentGeneratorProvider, null);
        List<JavaClassContentGeneratorProvider> providers = classContentGeneratorProviderStorage.getAll();
        int sizeAfterAdd = providers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(classContentGeneratorProvider, providers.get(sizeAfterAdd - 1));
    }

    @Test
    void addNewClassContentGeneratorProviderTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.add(classContentGeneratorProvider);
        List<JavaClassContentGeneratorProvider> providers = classContentGeneratorProviderStorage.getAll();
        int sizeAfterAdd = providers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(classContentGeneratorProvider, providers.get(sizeAfterAdd - 1));
    }

    @Test
    void addNullClassContentGeneratorProviderAfterExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.addAfter(null, presentClassContentGeneratorProviderClass);
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullClassContentGeneratorProviderAfterNonExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.addAfter(null, nonPresentClassContentGeneratorProvider.getClass());
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullClassContentGeneratorProviderAfterNullTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.addAfter(null, null);
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullClassContentGeneratorProviderBeforeExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.addBefore(null, presentClassContentGeneratorProviderClass);
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullClassContentGeneratorProviderBeforeNonExistentOneTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.addBefore(null, nonPresentClassContentGeneratorProvider.getClass());
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullClassContentGeneratorProviderBeforeNullTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.addBefore(null, null);
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullClassContentGeneratorProviderTest() {
        ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage
                = new ClassContentGeneratorProviderStorageImpl();
        int sizeBeforeAdd = classContentGeneratorProviderStorage.getAll().size();
        classContentGeneratorProviderStorage.add(null);
        int sizeAfterAdd = classContentGeneratorProviderStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }
}
