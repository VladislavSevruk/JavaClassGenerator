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
class ClassGenerationContextManagerTest {

    private static boolean initialAutoRefreshContext;
    private static ClassGenerationModuleFactoryMethod<ClassContentGeneratorPicker>
            initialClassContentGeneratorPickerFactoryMethod;
    private static ClassGenerationModuleFactoryMethod<ClassContentGeneratorProviderStorage>
            initialClassContentGeneratorProviderStorageFactoryMethod;

    @Mock
    private ClassContentGeneratorPicker classContentGeneratorPicker;
    @Mock
    private ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage;

    @BeforeAll
    static void disableContextRefresh() {
        initialAutoRefreshContext = ClassGenerationContextManager.isAutoRefreshContext();
        initialClassContentGeneratorPickerFactoryMethod = ClassGenerationModuleFactory.classContentGeneratorPicker();
        initialClassContentGeneratorProviderStorageFactoryMethod = ClassGenerationModuleFactory
                .classContentGeneratorProviderStorage();
        ClassGenerationContextManager.enableContextAutoRefresh();
    }

    @AfterAll
    static void setInitialAutoContextRefresh() {
        ClassGenerationContextManager.disableContextAutoRefresh();
        ClassGenerationModuleFactory
                .replaceClassContentGeneratorPicker(initialClassContentGeneratorPickerFactoryMethod);
        ClassGenerationModuleFactory
                .replaceClassContentGeneratorProviderStorage(initialClassContentGeneratorProviderStorageFactoryMethod);
        ClassGenerationContextManager.refreshContext();
        if (initialAutoRefreshContext) {
            ClassGenerationContextManager.enableContextAutoRefresh();
        }
    }

    @Test
    void autoRefreshContextAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        ClassGenerationContextManager.enableContextAutoRefresh();
        ClassGenerationContext resolvingContext1 = ClassGenerationContextManager.getContext();
        ClassGenerationModuleFactory.replaceClassContentGeneratorPicker(context -> classContentGeneratorPicker);
        ClassGenerationModuleFactory
                .replaceClassContentGeneratorProviderStorage(context -> classContentGeneratorProviderStorage);
        ClassGenerationContext resolvingContext2 = ClassGenerationContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(classContentGeneratorPicker, resolvingContext2.getClassContentGeneratorPicker());
        Assertions.assertEquals(classContentGeneratorProviderStorage,
                resolvingContext2.getClassContentGeneratorProviderStorage());
    }

    @Test
    void autoRefreshContextAfterClassContentGeneratorPickerUpdatesTest() {
        resetModulesAndContext();
        ClassGenerationContextManager.enableContextAutoRefresh();
        ClassGenerationContext resolvingContext1 = ClassGenerationContextManager.getContext();
        ClassGenerationModuleFactory.replaceClassContentGeneratorPicker(context -> classContentGeneratorPicker);
        ClassGenerationContext resolvingContext2 = ClassGenerationContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(classContentGeneratorPicker, resolvingContext2.getClassContentGeneratorPicker());
        Assertions.assertEquals(resolvingContext1.getClassContentGeneratorProviderStorage(),
                resolvingContext2.getClassContentGeneratorProviderStorage());
    }

    @Test
    void autoRefreshContextAfterClassContentGeneratorProviderStorageUpdatesTest() {
        resetModulesAndContext();
        ClassGenerationContextManager.enableContextAutoRefresh();
        ClassGenerationContext resolvingContext1 = ClassGenerationContextManager.getContext();
        ClassGenerationModuleFactory
                .replaceClassContentGeneratorProviderStorage(context -> classContentGeneratorProviderStorage);
        ClassGenerationContext resolvingContext2 = ClassGenerationContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(classContentGeneratorProviderStorage,
                resolvingContext2.getClassContentGeneratorProviderStorage());
        Assertions.assertNotEquals(resolvingContext1.getClassContentGeneratorPicker(),
                resolvingContext2.getClassContentGeneratorPicker());
    }

    @Test
    void equalContextAfterRefreshWithoutUpdatesTest() {
        ClassGenerationContext resolvingContext1 = ClassGenerationContextManager.getContext();
        ClassGenerationContextManager.refreshContext();
        ClassGenerationContext resolvingContext2 = ClassGenerationContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(resolvingContext1, resolvingContext2);
    }

    @Test
    void newContextAfterRefreshAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        ClassGenerationContextManager.disableContextAutoRefresh();
        ClassGenerationContext resolvingContext1 = ClassGenerationContextManager.getContext();
        ClassGenerationModuleFactory.replaceClassContentGeneratorPicker(context -> classContentGeneratorPicker);
        ClassGenerationModuleFactory
                .replaceClassContentGeneratorProviderStorage(context -> classContentGeneratorProviderStorage);
        ClassGenerationContextManager.refreshContext();
        ClassGenerationContext resolvingContext2 = ClassGenerationContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(classContentGeneratorPicker, resolvingContext2.getClassContentGeneratorPicker());
        Assertions.assertEquals(classContentGeneratorProviderStorage,
                resolvingContext2.getClassContentGeneratorProviderStorage());
    }

    @Test
    void newContextAfterRefreshAfterClassContentGeneratorPickerUpdatesTest() {
        resetModulesAndContext();
        ClassGenerationContextManager.disableContextAutoRefresh();
        ClassGenerationContext resolvingContext1 = ClassGenerationContextManager.getContext();
        ClassGenerationModuleFactory.replaceClassContentGeneratorPicker(context -> classContentGeneratorPicker);
        ClassGenerationContextManager.refreshContext();
        ClassGenerationContext resolvingContext2 = ClassGenerationContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertNotEquals(resolvingContext1.getClassContentGeneratorPicker(),
                resolvingContext2.getClassContentGeneratorPicker());
        Assertions.assertEquals(classContentGeneratorPicker, resolvingContext2.getClassContentGeneratorPicker());
        Assertions.assertEquals(resolvingContext1.getClassContentGeneratorProviderStorage(),
                resolvingContext2.getClassContentGeneratorProviderStorage());
    }

    @Test
    void newContextAfterRefreshAfterClassContentGeneratorProviderStorageUpdatesTest() {
        resetModulesAndContext();
        ClassGenerationContextManager.disableContextAutoRefresh();
        ClassGenerationContext resolvingContext1 = ClassGenerationContextManager.getContext();
        ClassGenerationModuleFactory
                .replaceClassContentGeneratorProviderStorage(context -> classContentGeneratorProviderStorage);
        ClassGenerationContextManager.refreshContext();
        ClassGenerationContext resolvingContext2 = ClassGenerationContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(classContentGeneratorProviderStorage,
                resolvingContext2.getClassContentGeneratorProviderStorage());
        Assertions.assertNotEquals(resolvingContext1.getClassContentGeneratorPicker(),
                resolvingContext2.getClassContentGeneratorPicker());
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterClassContentGeneratorPickerUpdatesTest() {
        resetModulesAndContext();
        ClassGenerationContextManager.disableContextAutoRefresh();
        ClassGenerationContext resolvingContext1 = ClassGenerationContextManager.getContext();
        ClassGenerationModuleFactory.replaceClassContentGeneratorPicker(context -> classContentGeneratorPicker);
        ClassGenerationContext resolvingContext2 = ClassGenerationContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterClassContentGeneratorProviderStorageUpdatesTest() {
        resetModulesAndContext();
        ClassGenerationContextManager.disableContextAutoRefresh();
        ClassGenerationContext resolvingContext1 = ClassGenerationContextManager.getContext();
        ClassGenerationModuleFactory
                .replaceClassContentGeneratorProviderStorage(context -> classContentGeneratorProviderStorage);
        ClassGenerationContext resolvingContext2 = ClassGenerationContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterRefreshAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        ClassGenerationContextManager.disableContextAutoRefresh();
        ClassGenerationContext resolvingContext1 = ClassGenerationContextManager.getContext();
        ClassGenerationModuleFactory.replaceClassContentGeneratorPicker(context -> classContentGeneratorPicker);
        ClassGenerationModuleFactory
                .replaceClassContentGeneratorProviderStorage(context -> classContentGeneratorProviderStorage);
        ClassGenerationContext resolvingContext2 = ClassGenerationContextManager.getContext();
        Assertions.assertEquals(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedTest() {
        ClassGenerationContext resolvingContext1 = ClassGenerationContextManager.getContext();
        ClassGenerationContext resolvingContext2 = ClassGenerationContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    private void resetModulesAndContext() {
        ClassGenerationContextManager.disableContextAutoRefresh();
        ClassGenerationModuleFactory.replaceClassContentGeneratorPicker(null);
        ClassGenerationModuleFactory.replaceClassContentGeneratorProviderStorage(null);
        ClassGenerationContextManager.refreshContext();
    }
}
