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
package com.github.vladislavsevruk.generator.java.context;

import com.github.vladislavsevruk.generator.java.picker.ClassContentGeneratorPicker;
import com.github.vladislavsevruk.generator.java.storage.ClassContentGeneratorProviderStorage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClassGenerationModuleFactoryTest {

    private static boolean initialAutoRefreshContext;

    @Mock
    private ClassContentGeneratorPicker classContentGeneratorPicker;
    @Mock
    private ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage;

    @BeforeAll
    static void disableContextRefresh() {
        initialAutoRefreshContext = ClassGenerationContextManager.isAutoRefreshContext();
        ClassGenerationContextManager.disableContextAutoRefresh();
    }

    @AfterAll
    static void setInitialAutoContextRefresh() {
        if (initialAutoRefreshContext) {
            ClassGenerationContextManager.enableContextAutoRefresh();
        }
    }

    @Test
    void defaultModulesTest() {
        Assertions.assertNull(ClassGenerationModuleFactory.classContentGeneratorPicker());
        Assertions.assertNull(ClassGenerationModuleFactory.classContentGeneratorProviderStorage());
    }

    @Test
    void replaceClassContentGeneratorProviderStorageTest() {
        ClassGenerationModuleFactoryMethod<ClassContentGeneratorProviderStorage> factoryMethod
                = context -> classContentGeneratorProviderStorage;
        ClassGenerationModuleFactory.replaceClassContentGeneratorProviderStorage(factoryMethod);
        Assertions.assertEquals(factoryMethod, ClassGenerationModuleFactory.classContentGeneratorProviderStorage());
    }

    @Test
    void replaceTypeConverterStorageTest() {
        ClassGenerationModuleFactoryMethod<ClassContentGeneratorPicker> factoryMethod
                = context -> classContentGeneratorPicker;
        ClassGenerationModuleFactory.replaceClassContentGeneratorPicker(factoryMethod);
        Assertions.assertEquals(factoryMethod, ClassGenerationModuleFactory.classContentGeneratorPicker());
    }
}
