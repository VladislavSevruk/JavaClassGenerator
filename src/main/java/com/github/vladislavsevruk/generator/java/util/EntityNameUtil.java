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

/**
 * Contains utility methods for modifying entities names.
 */
public final class EntityNameUtil {

    private EntityNameUtil() {
    }

    /**
     * Removes underscores and uppercases first letter for received value.
     *
     * @param name <code>String</code> with value to format.
     * @return <code>String</code> with removed underscores and uppercased first letter.
     */
    public static String getJavaFormatClassName(String name) {
        return uppercaseFirstLetter(removeUnderscores(name));
    }

    /**
     * Removes underscores and lowercases first letter for received value.
     *
     * @param name <code>String</code> with value to format.
     * @return <code>String</code> with removed underscores and lowercased first letter.
     */
    public static String getJavaFormatFieldName(String name) {
        return lowercaseFirstLetter(removeUnderscores(name));
    }

    /**
     * Lowercases first letter of received value if first letter isn't already in lowercase.
     *
     * @param name <code>String</code> with value to lowercase first letter for.
     * @return <code>String</code> with guaranteed lowercased first letter.
     */
    public static String lowercaseFirstLetter(String name) {
        int firstLetterCodePoint = name.codePointAt(0);
        // between 'A' and 'Z'
        if (firstLetterCodePoint >= 65 && firstLetterCodePoint <= 90) {
            // make lowercase; 32 is difference between code points for upper and lower cases
            firstLetterCodePoint += 32;
            return ((char) firstLetterCodePoint) + name.substring(1);
        }
        return name;
    }

    /**
     * Uppercases first letter of received value if first letter isn't already in uppercase.
     *
     * @param name <code>String</code> with value to uppercase first letter for.
     * @return <code>String</code> with guaranteed uppercased first letter.
     */
    public static String uppercaseFirstLetter(String name) {
        int firstLetterCodePoint = name.codePointAt(0);
        // between 'a' and 'z'
        if (firstLetterCodePoint >= 97 && firstLetterCodePoint <= 122) {
            // make uppercase; 32 is difference between code points for upper and lower cases
            firstLetterCodePoint -= 32;
            return ((char) firstLetterCodePoint) + name.substring(1);
        }
        return name;
    }

    private static String removeUnderscores(String name) {
        int underscoreIndex = name.indexOf('_');
        if (underscoreIndex == -1) {
            return name;
        }
        StringBuilder stringBuilder = new StringBuilder();
        do {
            stringBuilder.append(name, 0, underscoreIndex);
            name = uppercaseFirstLetter(name.substring(underscoreIndex + 1));
            underscoreIndex = name.indexOf('_');
        } while (underscoreIndex != -1);
        return stringBuilder.append(name).toString();
    }
}
