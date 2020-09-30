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
import com.github.vladislavsevruk.generator.java.provider.InterfaceContentGeneratorProvider;
import com.github.vladislavsevruk.generator.java.provider.JavaClassContentGeneratorProvider;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of <code>ClassContentGeneratorProviderStorage</code>.
 *
 * @see JavaClassContentGeneratorProvider
 * @see ClassContentGeneratorProviderStorage
 */
@EqualsAndHashCode
public class ClassContentGeneratorProviderStorageImpl implements ClassContentGeneratorProviderStorage {

    private static final ReadWriteLock PROVIDERS_LOCK = new ReentrantReadWriteLock();
    private static final Logger logger = LogManager.getLogger(ClassContentGeneratorProviderStorageImpl.class);
    private List<JavaClassContentGeneratorProvider> providers = new LinkedList<>();

    public ClassContentGeneratorProviderStorageImpl() {
        initProviders();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(JavaClassContentGeneratorProvider customClassContentGeneratorProvider) {
        PROVIDERS_LOCK.writeLock().lock();
        add(providers.size(), customClassContentGeneratorProvider);
        PROVIDERS_LOCK.writeLock().unlock();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAfter(JavaClassContentGeneratorProvider customClassContentGeneratorProvider,
            Class<? extends JavaClassContentGeneratorProvider> targetType) {
        PROVIDERS_LOCK.writeLock().lock();
        int targetTypeIndex = getIndexOfType(targetType);
        if (targetTypeIndex == -1) {
            logger.info(
                    "Target type is not present at list, class content generator provider will be added to list end.");
            add(providers.size(), customClassContentGeneratorProvider);
        } else {
            add(targetTypeIndex + 1, customClassContentGeneratorProvider);
        }
        PROVIDERS_LOCK.writeLock().unlock();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBefore(JavaClassContentGeneratorProvider customClassContentGeneratorProvider,
            Class<? extends JavaClassContentGeneratorProvider> targetType) {
        PROVIDERS_LOCK.writeLock().lock();
        int targetTypeIndex = getIndexOfType(targetType);
        if (targetTypeIndex == -1) {
            logger.info(
                    "Target type is not present at list, class content generator provider will be added to list end.");
            add(providers.size(), customClassContentGeneratorProvider);
        } else {
            add(targetTypeIndex, customClassContentGeneratorProvider);
        }
        PROVIDERS_LOCK.writeLock().unlock();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JavaClassContentGeneratorProvider> getAll() {
        PROVIDERS_LOCK.readLock().lock();
        List<JavaClassContentGeneratorProvider> providerList = providers.isEmpty() ? Collections.emptyList()
                : new ArrayList<>(providers);
        PROVIDERS_LOCK.readLock().unlock();
        return providerList;
    }

    private void add(int index, JavaClassContentGeneratorProvider customClassContentGeneratorProvider) {
        if (customClassContentGeneratorProvider == null) {
            logger.info("Received class content generator provider is null so it will not be added.");
            return;
        }
        int targetTypeIndex = getIndexOfType(customClassContentGeneratorProvider.getClass());
        if (targetTypeIndex != -1) {
            logger.info(
                    "Received class content generator provider is already present at list so it's copy will not be added.");
            return;
        }
        logger.debug(() -> String.format("Added '%s' class content generator provider.",
                customClassContentGeneratorProvider.getClass().getName()));
        providers.add(index, customClassContentGeneratorProvider);
    }

    private <T> int getIndexOfType(Class<? extends T> targetType) {
        int targetTypeIndex = -1;
        if (targetType == null) {
            logger.info("Target type is null.");
            return -1;
        }
        for (int i = 0; i < providers.size(); ++i) {
            if (targetType.equals(providers.get(i).getClass())) {
                targetTypeIndex = i;
                break;
            }
        }
        return targetTypeIndex;
    }

    private void initProviders() {
        providers.add(new EnumContentGeneratorProvider());
        providers.add(new InterfaceContentGeneratorProvider());
        providers.add(new ClassContentGeneratorProvider());
    }
}
