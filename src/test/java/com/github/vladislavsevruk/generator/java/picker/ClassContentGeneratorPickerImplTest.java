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
package com.github.vladislavsevruk.generator.java.picker;

import com.github.vladislavsevruk.generator.java.provider.ClassContentGeneratorProvider;
import com.github.vladislavsevruk.generator.java.provider.EnumContentGeneratorProvider;
import com.github.vladislavsevruk.generator.java.provider.InterfaceContentGeneratorProvider;
import com.github.vladislavsevruk.generator.java.type.SchemaEnum;
import com.github.vladislavsevruk.generator.java.type.SchemaInterface;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClassContentGeneratorPickerImplTest {

    @Mock
    private SchemaEnum schemaEnum;
    @Mock
    private SchemaInterface schemaInterface;
    @Mock
    private SchemaObject schemaObject;

    @Test
    void defaultConstructorTest() {
        ClassContentGeneratorPickerImpl picker = new ClassContentGeneratorPickerImpl();
        Assertions.assertEquals(EnumContentGeneratorProvider.class,
                picker.pickClassContentGeneratorProvider(schemaEnum).getClass());
        Assertions.assertEquals(InterfaceContentGeneratorProvider.class,
                picker.pickClassContentGeneratorProvider(schemaInterface).getClass());
        Assertions.assertEquals(ClassContentGeneratorProvider.class,
                picker.pickClassContentGeneratorProvider(schemaObject).getClass());
    }
}
