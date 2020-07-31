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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Provides replaceable modules schemas required for class generation mechanism.
 */
public final class ClassGenerationModuleFactory {

    private static final ReadWriteLock CLASS_CONTENT_GENERATOR_PICKER_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock CLASS_CONTENT_GENERATOR_PROVIDER_STORAGE_LOCK = new ReentrantReadWriteLock();
    private static final Logger logger = LogManager.getLogger(ClassGenerationModuleFactory.class);
    private static ClassGenerationModuleFactoryMethod<ClassContentGeneratorPicker> classContentGeneratorPicker;
    private static ClassGenerationModuleFactoryMethod<ClassContentGeneratorProviderStorage>
            classContentGeneratorProviderStorage;

    private ClassGenerationModuleFactory() {
    }

    /**
     * Returns current instance of <code>ClassGenerationModuleFactoryMethod</code> for
     * <code>ClassContentGeneratorPicker</code>.
     */
    public static ClassGenerationModuleFactoryMethod<ClassContentGeneratorPicker> classContentGeneratorPicker() {
        CLASS_CONTENT_GENERATOR_PICKER_LOCK.readLock().lock();
        ClassGenerationModuleFactoryMethod<ClassContentGeneratorPicker> pickerToReturn = classContentGeneratorPicker;
        CLASS_CONTENT_GENERATOR_PICKER_LOCK.readLock().unlock();
        return pickerToReturn;
    }

    /**
     * Returns current instance of <code>ClassGenerationModuleFactoryMethod</code> for
     * <code>ClassContentGeneratorProviderStorage</code>.
     */
    public static ClassGenerationModuleFactoryMethod<ClassContentGeneratorProviderStorage> classContentGeneratorProviderStorage() {
        CLASS_CONTENT_GENERATOR_PROVIDER_STORAGE_LOCK.readLock().lock();
        ClassGenerationModuleFactoryMethod<ClassContentGeneratorProviderStorage> storageToReturn
                = classContentGeneratorProviderStorage;
        CLASS_CONTENT_GENERATOR_PROVIDER_STORAGE_LOCK.readLock().unlock();
        return storageToReturn;
    }

    /**
     * Replaces instance of <code>ClassGenerationModuleFactoryMethod</code> for <code>ClassContentGeneratorPicker</code>.
     * All further class generations will use new instance.
     *
     * @param picker new instance of <code>ClassGenerationModuleFactoryMethod</code> for
     *               <code>ClassContentGeneratorPicker</code>.
     */
    public static void replaceClassContentGeneratorPicker(
            ClassGenerationModuleFactoryMethod<ClassContentGeneratorPicker> picker) {
        CLASS_CONTENT_GENERATOR_PICKER_LOCK.writeLock().lock();
        logger.info(() -> String.format("Replacing ClassContentGeneratorPicker by '%s'.",
                picker == null ? null : picker.getClass().getName()));
        classContentGeneratorPicker = picker;
        CLASS_CONTENT_GENERATOR_PICKER_LOCK.writeLock().unlock();
        if (ClassGenerationContextManager.isAutoRefreshContext()) {
            ClassGenerationContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>ClassGenerationModuleFactoryMethod</code> for <code>ClassContentGeneratorProviderStorage</code>.
     * All further class generations will use new instance.
     *
     * @param storage new instance of <code>ClassGenerationModuleFactoryMethod</code> for
     *                <code>ClassContentGeneratorProviderStorage</code>.
     */
    public static void replaceContentGeneratorProviderStorage(
            ClassGenerationModuleFactoryMethod<ClassContentGeneratorProviderStorage> storage) {
        CLASS_CONTENT_GENERATOR_PROVIDER_STORAGE_LOCK.writeLock().lock();
        logger.info(() -> String.format("Replacing ClassContentGeneratorProviderStorage by '%s'.",
                storage == null ? null : storage.getClass().getName()));
        classContentGeneratorProviderStorage = storage;
        CLASS_CONTENT_GENERATOR_PROVIDER_STORAGE_LOCK.writeLock().unlock();
        if (ClassGenerationContextManager.isAutoRefreshContext()) {
            ClassGenerationContextManager.refreshContext();
        }
    }
}
