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
package com.github.vladislavsevruk.generator.java.generator.dependency;

import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.generator.ClassImportGenerator;
import com.github.vladislavsevruk.generator.java.type.SchemaElementSequence;
import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Contains common logic for class import generators.
 *
 * @see ClassImportGenerator
 */
public abstract class AbstractImportGenerator implements ClassImportGenerator {

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> generate(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
        return collectInnerEntities(schemaObject).stream()
                .filter(entity -> !schemaObject.getPackage().equals(entity.getPackage())).map(this::addImport)
                .collect(Collectors.toSet());
    }

    protected void collectInnerEntities(Set<SchemaEntity> entities, SchemaEntity schemaEntity) {
        if (schemaEntity.getPackage() != null) {
            entities.add(schemaEntity);
        }
        if (isSequence(schemaEntity)) {
            ((SchemaElementSequence) schemaEntity).getElementTypes()
                    .forEach(entity -> collectInnerEntities(entities, entity));
        }
    }

    protected abstract Set<SchemaEntity> collectInnerEntities(SchemaObject schemaObject);

    private String addImport(SchemaEntity schemaEntity) {
        return String.format("import %s.%s;%n", schemaEntity.getPackage(), schemaEntity.getName());
    }

    private boolean isSequence(SchemaEntity schemaEntity) {
        return SchemaElementSequence.class.isAssignableFrom(schemaEntity.getClass());
    }
}
