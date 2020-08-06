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
import com.github.vladislavsevruk.generator.java.storage.SchemaObjectStorageImpl;
import com.github.vladislavsevruk.generator.java.test.JavaClassGeneratorConfigProvider;
import com.github.vladislavsevruk.generator.java.test.TestConstant;
import com.github.vladislavsevruk.generator.java.test.mock.MockedEntityBuilder;
import com.github.vladislavsevruk.generator.java.type.SchemaEnum;
import com.github.vladislavsevruk.generator.java.type.SchemaInterface;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class JavaClassGeneratorTest {

    private static final Logger logger = LogManager.getLogger(JavaClassGeneratorTest.class);

    @AfterAll
    static void cleanTempDirectory() {
        logger.debug("Deleting temp directory.");
        removeDirectory(new File(TestConstant.PATH_TO_TEMP_FOLDER));
    }

    @Test
    void generateJavaClassForClassSchemaTest() throws IOException {
        String postfix = String.valueOf(System.currentTimeMillis());
        SchemaObject schemaObject = MockedEntityBuilder.getMockedClassBuilder().setFields(4).setSuperclass()
                .setInterfaces(2).setName(postfix).build(postfix);
        String fileName = schemaObject.getName();
        JavaClassGeneratorConfig config = JavaClassGeneratorConfigProvider.getDefault();
        new JavaClassGenerator().generateJavaClass(config, schemaObject);
        String filePath = String.join("", TestConstant.PATH_TO_TEMP_FOLDER, File.separator, fileName, ".java");
        Assertions.assertTrue(new File(filePath).exists());
        String fileContent = readFileContent(filePath);
        String expectedFileContent = "// generated by com.github.vladislavsevruk:java-class-generator\n"
                + "package entity.mock;\n\nimport field.mock.MockedFieldType0;\n"
                + "import field.mock.MockedFieldType1;\nimport field.mock.MockedFieldType2;\n"
                + "import field.mock.MockedFieldType3;\n\nimport interface.mock.MockedInterface0;\n"
                + "import interface.mock.MockedInterface1;\n\nimport lombok.Data;\n"
                + "import lombok.EqualsAndHashCode;\nimport lombok.ToString;\n"
                + "import lombok.experimental.Accessors;\n\nimport superclass.mock.MockedSuperclass;\n\n"
                + "@Accessors(chain = true)\n@Data\n@EqualsAndHashCode(callSuper = true)\n"
                + "@ToString(callSuper = true)\npublic class " + fileName
                + " extends MockedSuperclass implements MockedInterface0, MockedInterface1 {\n\n"
                + "    private MockedFieldType0 mockedFieldName0;\n\n"
                + "    private MockedFieldType1 mockedFieldName1;\n\n"
                + "    private MockedFieldType2 mockedFieldName2;\n\n"
                + "    private MockedFieldType3 mockedFieldName3;\n}\n";
        Assertions.assertEquals(expectedFileContent, fileContent);
    }

    @Test
    void generateJavaClassForEnumSchemaTest() throws IOException {
        String postfix = String.valueOf(System.currentTimeMillis());
        SchemaEnum schemaObject = MockedEntityBuilder.getMockedEnumBuilder().setFields(4).setSuperclass()
                .setInterfaces(2).build(postfix);
        String fileName = schemaObject.getName();
        JavaClassGeneratorConfig config = JavaClassGeneratorConfigProvider.getDefault();
        new JavaClassGenerator().generateJavaClass(config, schemaObject);
        String filePath = String.join("", TestConstant.PATH_TO_TEMP_FOLDER, File.separator, fileName, ".java");
        Assertions.assertTrue(new File(filePath).exists());
        String fileContent = readFileContent(filePath);
        String expectedFileContent = "// generated by com.github.vladislavsevruk:java-class-generator\n"
                + "package entity.mock;\n\nimport interface.mock.MockedInterface0;\n"
                + "import interface.mock.MockedInterface1;\n\npublic enum " + fileName
                + " implements MockedInterface0, MockedInterface1 {\n"
                + "    mockedFieldName0,\n    mockedFieldName1,\n    mockedFieldName2,\n    mockedFieldName3\n}\n";
        Assertions.assertEquals(expectedFileContent, fileContent);
    }

    @Test
    void generateJavaClassForInterfaceSchemaTest() throws IOException {
        String postfix = String.valueOf(System.currentTimeMillis());
        SchemaInterface schemaObject = MockedEntityBuilder.getMockedInterfaceBuilder().setFields(4).setSuperclass()
                .setInterfaces(2).setName(postfix).build(postfix);
        String fileName = schemaObject.getName();
        JavaClassGeneratorConfig config = JavaClassGeneratorConfigProvider.getDefault();
        new JavaClassGenerator().generateJavaClass(config, schemaObject);
        String filePath = String.join("", TestConstant.PATH_TO_TEMP_FOLDER, File.separator, fileName, ".java");
        Assertions.assertTrue(new File(filePath).exists());
        String fileContent = readFileContent(filePath);
        String expectedFileContent = "// generated by com.github.vladislavsevruk:java-class-generator\n"
                + "package entity.mock;\n\nimport interface.mock.MockedInterface0;\n"
                + "import interface.mock.MockedInterface1;\n\npublic interface " + fileName
                + " extends MockedInterface0, MockedInterface1 {\n}\n";
        Assertions.assertEquals(expectedFileContent, fileContent);
    }

    @Test
    void generateJavaClassForSchemaStorageTest() throws IOException {
        SchemaObjectStorageImpl objectStorage = new SchemaObjectStorageImpl();
        String postfix1 = String.valueOf(System.currentTimeMillis());
        SchemaObject schemaObject1 = MockedEntityBuilder.getMockedClassBuilder().setFields(1).setSuperclass()
                .setInterfaces(1).setName(postfix1).build(postfix1);
        String file1Name = schemaObject1.getName();
        String postfix2 = String.valueOf(System.currentTimeMillis());
        SchemaObject schemaObject2 = MockedEntityBuilder.getMockedClassBuilder().setFields(2).setSuperclass()
                .setInterfaces(2).setName(postfix2).build(postfix2);
        String file2Name = schemaObject2.getName();
        JavaClassGeneratorConfig config = JavaClassGeneratorConfigProvider.getDefault();
        objectStorage.put(file1Name, schemaObject1);
        objectStorage.put(file2Name, schemaObject2);
        new JavaClassGenerator().generateJavaClasses(config, objectStorage);
        String file1Path = String.join("", TestConstant.PATH_TO_TEMP_FOLDER, File.separator, file1Name, ".java");
        Assertions.assertTrue(new File(file1Path).exists());
        String file1Content = readFileContent(file1Path);
        String expectedFile1Content = "// generated by com.github.vladislavsevruk:java-class-generator\n"
                + "package entity.mock;\n\nimport field.mock.MockedFieldType0;\n\n"
                + "import interface.mock.MockedInterface0;\n\nimport lombok.Data;\n"
                + "import lombok.EqualsAndHashCode;\nimport lombok.ToString;\n"
                + "import lombok.experimental.Accessors;\n\nimport superclass.mock.MockedSuperclass;\n\n"
                + "@Accessors(chain = true)\n@Data\n@EqualsAndHashCode(callSuper = true)\n"
                + "@ToString(callSuper = true)\n" + "public class " + file1Name
                + " extends MockedSuperclass implements MockedInterface0 {\n\n"
                + "    private MockedFieldType0 mockedFieldName0;\n}\n";
        Assertions.assertEquals(expectedFile1Content, file1Content);
        String file2Path = String.join("", TestConstant.PATH_TO_TEMP_FOLDER, File.separator, file2Name, ".java");
        Assertions.assertTrue(new File(file2Path).exists());
        String file2Content = readFileContent(file2Path);
        String expectedFile2Content = "// generated by com.github.vladislavsevruk:java-class-generator\n"
                + "package entity.mock;\n\nimport field.mock.MockedFieldType0;\n"
                + "import field.mock.MockedFieldType1;\n\nimport interface.mock.MockedInterface0;\n"
                + "import interface.mock.MockedInterface1;\n\nimport lombok.Data;\n"
                + "import lombok.EqualsAndHashCode;\nimport lombok.ToString;\n"
                + "import lombok.experimental.Accessors;\n\nimport superclass.mock.MockedSuperclass;\n\n"
                + "@Accessors(chain = true)\n@Data\n@EqualsAndHashCode(callSuper = true)\n"
                + "@ToString(callSuper = true)\n" + "public class " + file2Name
                + " extends MockedSuperclass implements MockedInterface0, MockedInterface1 {\n\n"
                + "    private MockedFieldType0 mockedFieldName0;\n\n"
                + "    private MockedFieldType1 mockedFieldName1;\n}\n";
        Assertions.assertEquals(expectedFile2Content, file2Content);
    }

    @Test
    void noContentChangesForExistentClassTest() throws IOException {
        String postfix = String.valueOf(System.currentTimeMillis());
        SchemaObject schemaObject = MockedEntityBuilder.getMockedClassBuilder().setFields(1).setSuperclass()
                .setInterfaces(1).setName(postfix).build(postfix);
        String fileName = schemaObject.getName();
        JavaClassGeneratorConfig config = JavaClassGeneratorConfigProvider.getDefault();
        JavaClassGenerator javaClassGenerator = new JavaClassGenerator();
        javaClassGenerator.generateJavaClass(config, schemaObject);
        String filePath = String.join("", TestConstant.PATH_TO_TEMP_FOLDER, File.separator, fileName, ".java");
        Assertions.assertTrue(new File(filePath).exists());
        String fileContent = readFileContent(filePath);
        schemaObject = MockedEntityBuilder.getMockedClassBuilder().setFields(2).setSuperclass().setInterfaces(2)
                .setName(postfix).build(postfix);
        Assertions.assertEquals(fileName, schemaObject.getName());
        javaClassGenerator.generateJavaClass(config, schemaObject);
        Assertions.assertEquals(fileContent, readFileContent(filePath));
    }

    private static String endBySeparator(String path) {
        if (path.endsWith(File.separator)) {
            return path;
        }
        return path + File.separator;
    }

    private static void removeDirectory(File directory) {
        for (String fileName : directory.list()) {
            File subFile = new File(endBySeparator(directory.getAbsolutePath()) + fileName);
            if (subFile.isDirectory()) {
                removeDirectory(subFile);
            } else {
                subFile.delete();
            }
        }
        directory.delete();
    }

    private String readFileContent(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
    }
}
