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

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Provides class generation context and refreshes it.
 */
public final class ClassGenerationContextManager {

    private static final ReadWriteLock DEFAULT_CONTEXT_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock SETTINGS_LOCK = new ReentrantReadWriteLock();
    private static boolean autoRefreshContext = true;
    private static ClassGenerationContext defaultContext = newContext();

    private ClassGenerationContextManager() {
    }

    /**
     * Enables class generation context refresh after updates at class generation modules.
     */
    public static void disableContextAutoRefresh() {
        autoRefreshContext(false);
    }

    /**
     * Disables class generation context refresh after updates at class generation modules.
     */
    public static void enableContextAutoRefresh() {
        autoRefreshContext(true);
    }

    /**
     * Returns default context with values from <code>ClassGenerationModuleFactory</code>.
     *
     * @see ClassGenerationModuleFactory
     */
    public static ClassGenerationContext getContext() {
        DEFAULT_CONTEXT_LOCK.readLock().lock();
        ClassGenerationContext contextToReturn = defaultContext;
        DEFAULT_CONTEXT_LOCK.readLock().unlock();
        return contextToReturn;
    }

    /**
     * Returns <code>true</code> if class generation context should be refreshed after updates at class generation
     * modules,
     * <code>false</code> otherwise.
     */
    static boolean isAutoRefreshContext() {
        SETTINGS_LOCK.readLock().lock();
        boolean valueToReturn = autoRefreshContext;
        SETTINGS_LOCK.readLock().unlock();
        return valueToReturn;
    }

    /**
     * Re-initializes <code>ClassGenerationContext</code> with values from <code>ClassGenerationModuleFactory</code>.
     *
     * @see ClassGenerationModuleFactory
     */
    static void refreshContext() {
        DEFAULT_CONTEXT_LOCK.writeLock().lock();
        defaultContext = newContext();
        DEFAULT_CONTEXT_LOCK.writeLock().unlock();
    }

    private static void autoRefreshContext(boolean isTrue) {
        SETTINGS_LOCK.writeLock().lock();
        autoRefreshContext = isTrue;
        SETTINGS_LOCK.writeLock().unlock();
    }

    private static ClassGenerationContext newContext() {
        return new ClassGenerationContextImpl(ClassGenerationModuleFactory.classContentGeneratorPicker(),
                ClassGenerationModuleFactory.classContentGeneratorProviderStorage());
    }
}
