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
package com.github.vladislavsevruk.generator.java;

import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.context.ClassGenerationContextManager;
import com.github.vladislavsevruk.generator.java.picker.ClassContentGeneratorPicker;
import com.github.vladislavsevruk.generator.java.provider.JavaClassContentGeneratorProvider;
import com.github.vladislavsevruk.generator.java.storage.SchemaObjectStorage;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import com.github.vladislavsevruk.generator.java.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Generates Java class files for class schemas.
 */
public class JavaClassGenerator {

    private static final Logger logger = LogManager.getLogger(JavaClassGenerator.class);

    private ClassContentGeneratorPicker classContentGeneratorPicker;

    public JavaClassGenerator() {
        this(ClassGenerationContextManager.getContext().getClassContentGeneratorPicker());
    }

    public JavaClassGenerator(ClassContentGeneratorPicker classContentGeneratorPicker) {
        this.classContentGeneratorPicker = classContentGeneratorPicker;
    }

    /**
     * Generates Java class file for received class schema according to received configuration parameters.
     *
     * @param config       <code>JavaClassGeneratorConfig</code> with parameters for Java classes generation.
     * @param schemaObject <code>SchemaObject</code> to generate Java class file for.
     */
    public void generateJavaClass(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
        FileUtil.recursiveMkdir(config.getPathToTargetFolder());
        generateJavaClassFile(config, schemaObject);
    }

    /**
     * Generates Java class files for all class schemas at received storage according to received configuration
     * parameters.
     *
     * @param config  <code>JavaClassGeneratorConfig</code> with parameters for Java classes generation.
     * @param storage <code>SchemaObjectStorage</code> with class schemas to generate Java class content for.
     */
    public void generateJavaClasses(JavaClassGeneratorConfig config, SchemaObjectStorage storage) {
        FileUtil.recursiveMkdir(config.getPathToTargetFolder());
        storage.getAllObjects().forEach(object -> generateJavaClassFile(config, object));
    }

    private void generateJavaClassFile(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
        String className = schemaObject.getName();
        String filePath = String
                .format("%s%s.java", FileUtil.addPathSeparator(config.getPathToTargetFolder()), className);
        JavaClassContentGeneratorProvider classContentGeneratorProvider = classContentGeneratorPicker
                .pickClassContentGeneratorProvider(schemaObject);
        String javaClassContent = new JavaClassContentGenerator(classContentGeneratorProvider)
                .generate(config, schemaObject);
        String generatedFilePath = FileUtil.writeToNewFile(filePath, javaClassContent);
        if (generatedFilePath != null) {
            logger.info(() -> "Generated class: " + generatedFilePath);
        } else {
            logger.warn(() -> "Cannot generate: " + schemaObject.getName());
        }
    }
}
