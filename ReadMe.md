[![Build Status](https://travis-ci.org/VladislavSevruk/JavaClassGenerator.svg?branch=master)](https://travis-ci.com/VladislavSevruk/JavaClassGenerator)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=VladislavSevruk_JavaClassGenerator&metric=alert_status)](https://sonarcloud.io/dashboard?id=VladislavSevruk_JavaClassGenerator)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=VladislavSevruk_JavaClassGenerator&metric=coverage)](https://sonarcloud.io/component_measures?id=VladislavSevruk_JavaClassGenerator&metric=coverage)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vladislavsevruk/java-class-generator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vladislavsevruk/java-class-generator)

# Java Class Generator
This utility library helps to generate java classes based on provided class schemas with some predefined code 
generation parameters.

## Table of contents
* [Getting started](#getting-started)
  * [Maven](#maven)
  * [Gradle](#gradle)
* [Class generation configuration](#class-generation-configuration)
* [Usage](#usage)
* [Customization](#customization)
  * [Add custom class content generators](#add-custom-class-content-generators)
* [License](#license)

## Getting started
To add library to your project perform next steps:

### Maven
Add the following dependency to your pom.xml:
```xml
<dependency>
      <groupId>com.github.vladislavsevruk</groupId>
      <artifactId>java-class-generator</artifactId>
      <version>1.0.1</version>
</dependency>
```
### Gradle
Add the following dependency to your build.gradle:
```groovy
implementation 'com.github.vladislavsevruk:java-class-generator:1.0.1'
```

## Class generation configuration
Library supports some configuration parameters for class generation which are present at 
[JavaClassGeneratorConfig](/src/main/java/com/github/vladislavsevruk/generator/java/config/JavaClassGeneratorConfig.java) 
class. Here is short description for them:
* __Path to target folder__ - points to folder where generated classes should be placed.
* __Indents to use__ - indicates what indents should be used for class generation. Possible variants are represented by 
[Indent](src/main/java/com/github/vladislavsevruk/generator/java/constant/Indent.java) class.
* __Add Jackson library annotations__ - indicates if Jackson library annotations should be added for schema items that 
implements [JacksonSchemaField](/src/main/java/com/github/vladislavsevruk/generator/java/type/JacksonSchemaField.java) 
interface.
* __Use Lombok library annotations__ - indicates if Lombok library annotations should be added for generated classes.
* __Add empty line between fields__ - indicates if empty line should be added between class fields.
* __Sort fields by modifiers__ - indicates if class fields should be sorted by modifiers in following order:
    * public static final FieldType ...
    * public static FieldType ...
    * public FieldType ...
    * protected static final FieldType ...
    * protected static FieldType ...
    * protected FieldType ...
    * static final FieldType ...
    * static FieldType ...
    * FieldType ...
    * private static final FieldType ...
    * private static FieldType ...
    * private FieldType ...

## Usage
For generation single class it's required to implement [SchemaObject](src/main/java/com/github/vladislavsevruk/generator/java/type/SchemaObject.java) 
interface with information about class elements (library has 
[schemas of some basic Java classes](src/main/java/com/github/vladislavsevruk/generator/java/type/predefined) which can 
be used for class schema elements description). Once schema is ready all you need is to call 
```kotlin
// creating generator config with path to target folder
JavaClassGeneratorConfig config = JavaClassGeneratorConfig.builder()
        .pathToTargetFolder("generated/classes").build();
// generating schema object with target class elements description
SchemaObject schemaObject = generateSchemaObject();
// generating class according to received schema and config with target folder
new JavaClassGenerator().generateJavaClass(config, schemaObject);
```

Also several classes can be generated at once using 
[SchemaObjectStorage](src/main/java/com/github/vladislavsevruk/generator/java/storage/SchemaObjectStorage.java) 
(library has [default implementation](src/main/java/com/github/vladislavsevruk/generator/java/storage/SchemaObjectStorageImpl.java) 
of this interface):
```kotlin
// creating generator config with path to target folder
JavaClassGeneratorConfig config = JavaClassGeneratorConfig.builder()
        .pathToTargetFolder("generated/classes").build();
// generating several schema objects with class elements description
SchemaObject schemaObject1 = generateSchemaObject1();
SchemaObject schemaObject2 = generateSchemaObject2();
// create storage instance
SchemaObjectStorageImpl storage = new SchemaObjectStorageImpl();
// put generated schema objects to storage
storage.put(schemaObject1.getName(), schemaObject1);
storage.put(schemaObject2.getName(), schemaObject2);
// generating class according to received schema and config with target folder
new JavaClassGenerator().generateJavaClasses(config, storage);
```

## Customization
### Add custom class content generators
You can add your own class content generators to customize logic of class elements generation. Simply implement 
[JavaClassContentGeneratorProvider](src/main/java/com/github/vladislavsevruk/generator/java/provider/JavaClassContentGeneratorProvider.java)
interface and add it to 
[ClassContentGeneratorProviderStorage](src/main/java/com/github/vladislavsevruk/generator/java/storage/ClassContentGeneratorProviderStorage.java)
from context (you can reach it calling ``ClassGenerationContextManager.getContext().getClassContentGeneratorProviderStorage()``). 
Library has [default implementations](src/main/java/com/github/vladislavsevruk/generator/java/provider) of this 
interface for simple class, enums and interfaces that can be overridden to update logic of some elements generation.

## License
This project is licensed under the MIT License, you can read the full text [here](LICENSE).