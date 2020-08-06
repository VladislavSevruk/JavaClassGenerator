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
package com.github.vladislavsevruk.generator.java.util;

import java.util.function.Supplier;

/**
 * Contains utility methods for picking default values for <code>null</code> ones.
 */
public final class DefaultValueUtil {

    private DefaultValueUtil() {
    }

    /**
     * Returns received value if it's not <code>null</code> or default one otherwise.
     *
     * @param value        value to check.
     * @param defaultValue default value to pick if tested value is <code>null</code>.
     * @param <T>          type of value.
     * @return received value if it's not <code>null</code> or default one otherwise.
     */
    public static <T> T orDefault(T value, T defaultValue) {
        if (value != null) {
            return value;
        }
        return defaultValue;
    }

    /**
     * Returns received value if it's not <code>null</code> or default value from received <code>Supplier</code>
     * otherwise.
     *
     * @param value                value to check.
     * @param defaultValueSupplier <code>Supplier</code> that provides default value.
     * @param <T>                  type of value.
     * @return received value if it's not <code>null</code> or default value from received <code>Supplier</code>
     * otherwise.
     */
    public static <T> T orDefault(T value, Supplier<T> defaultValueSupplier) {
        if (value != null) {
            return value;
        }
        return defaultValueSupplier.get();
    }
}
