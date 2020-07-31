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
import com.github.vladislavsevruk.generator.java.picker.ClassContentGeneratorPickerImpl;
import com.github.vladislavsevruk.generator.java.storage.ClassContentGeneratorProviderStorage;
import com.github.vladislavsevruk.generator.java.storage.ClassContentGeneratorProviderStorageImpl;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of <code>ClassGenerationContext</code>.
 *
 * @see ClassGenerationContext
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
final class ClassGenerationContextImpl implements ClassGenerationContext {

    private static final Logger logger = LogManager.getLogger(ClassGenerationContextImpl.class);
    ClassContentGeneratorPicker classContentGeneratorPicker;
    ClassContentGeneratorProviderStorage classContentGeneratorProviderStorage;

    /**
     * Creates new instance using received modules or default implementations for nulls.
     *
     * @param classContentGeneratorPickerFactoryMethod          factory method for <code>ClassContentGeneratorPicker</code>
     *                                                          module implementation.
     * @param classContentGeneratorProviderStorageFactoryMethod factory method for <code>ClassContentGeneratorProviderStorage</code>
     *                                                          module implementation.
     */
    ClassGenerationContextImpl(
            ClassGenerationModuleFactoryMethod<ClassContentGeneratorPicker> classContentGeneratorPickerFactoryMethod,
            ClassGenerationModuleFactoryMethod<ClassContentGeneratorProviderStorage> classContentGeneratorProviderStorageFactoryMethod) {
        this.classContentGeneratorProviderStorage = orDefault(classContentGeneratorProviderStorageFactoryMethod,
                context -> new ClassContentGeneratorProviderStorageImpl());
        logger.debug(() -> String.format("Using '%s' as class content generator provider storage.",
                classContentGeneratorProviderStorage.getClass().getName()));
        this.classContentGeneratorPicker = orDefault(classContentGeneratorPickerFactoryMethod,
                context -> new ClassContentGeneratorPickerImpl(classContentGeneratorProviderStorage));
        logger.debug(() -> String.format("Using '%s' as class content generator picker.",
                classContentGeneratorPicker.getClass().getName()));
    }

    private <T> T orDefault(ClassGenerationModuleFactoryMethod<T> factoryMethod,
            ClassGenerationModuleFactoryMethod<T> defaultFactoryMethod) {
        if (factoryMethod != null) {
            T module = factoryMethod.get(this);
            if (module != null) {
                return module;
            }
        }
        return defaultFactoryMethod.get(this);
    }
}
