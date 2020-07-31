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
package com.github.vladislavsevruk.generator.java.comparator;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Compares <code>String</code>s with fields declaration by field modifiers.
 */
public final class FieldModifierComparator implements Comparator<String> {

    private static final Pattern FIELD_MODIFIERS_PATTERN = Pattern
            .compile("(private|protected|public|static|final|volatile|transient)\\s+");
    private static final List<Predicate<Integer>> FIELD_MODIFIERS_PREDICATES = Arrays
            .asList(Modifier::isStatic, Modifier::isPrivate, Modifier::isProtected, Modifier::isPublic);

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(String field1, String field2) {
        int field1Modifiers = getFieldModifiers(field1);
        int field2Modifiers = getFieldModifiers(field2);
        for (Predicate<Integer> fieldModifierPredicate : FIELD_MODIFIERS_PREDICATES) {
            int result = compareByPredicate(fieldModifierPredicate, field1Modifiers, field2Modifiers);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    private <T> int compareByPredicate(Predicate<T> predicate, T val1, T val2) {
        boolean val1Result = predicate.test(val1);
        if (val1Result != predicate.test(val2)) {
            return val1Result ? -1 : 1;
        }
        return 0;
    }

    private int pickModifier(String modifier) {
        switch (modifier) {
            case "private":
                return Modifier.PRIVATE;
            case "protected":
                return Modifier.PROTECTED;
            case "public":
                return Modifier.PUBLIC;
            case "static":
                return Modifier.STATIC;
            case "final":
                return Modifier.FINAL;
            case "volatile":
                return Modifier.VOLATILE;
            case "transient":
                return Modifier.TRANSIENT;
            default:
                // no other field modifiers at Java 8 but may be added at following ones - who knows?
                return 0;
        }
    }

    private int getFieldModifiers(String field) {
        Matcher matcher = FIELD_MODIFIERS_PATTERN.matcher(field);
        matcher.region(0, field.indexOf(';'));
        // max 5 modifiers
        int i = 5;
        int modifiers = 0;
        while (matcher.find() && i-- > 0) {
            modifiers = modifiers | pickModifier(matcher.group(1));
        }
        return modifiers;
    }
}
