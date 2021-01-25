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

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Contains file related utility methods.
 */
@Log4j2
public final class FileUtil {

    private FileUtil() {
    }

    /**
     * Adds path separator symbol to received directory path if it's not already ends by it.
     *
     * @param dirPath <code>String</code> with directory path.
     * @return <code>String</code> with directory path that guaranteed ends with path separator symbol.
     */
    public static String addPathSeparator(String dirPath) {
        if (dirPath.endsWith(File.separator)) {
            return dirPath;
        }
        return dirPath + File.separator;
    }

    /**
     * Creates directory at received path creating all parent folders if they not exist.
     *
     * @param path <code>String</code> with path for folder to create.
     */
    public static void recursiveMkdir(String path) {
        if (!new File(path).exists()) {
            int pathSeparatorIndex = path.lastIndexOf(File.separator);
            if (pathSeparatorIndex != -1) {
                recursiveMkdir(path.substring(0, pathSeparatorIndex));
            }
            log.info(() -> String.format("Creating '%s' folder.", path));
            new File(path).mkdir();
        }
    }

    /**
     * Writes received content to received file.
     *
     * @param file         <code>File</code> to write content to.
     * @param classContent <code>String</code> with file content.
     * @return <code>String</code> with file path or <code>null</code> if failed to write to received <code>File</code>.
     */
    public static String writeToFile(File file, String classContent) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(classContent);
            return file.getPath();
        } catch (IOException ioEx) {
            log.error(() -> "Failed to write to file: " + file.getAbsolutePath());
            return null;
        }
    }

    /**
     * Writes received content to received file if there is no already file with such path.
     *
     * @param file         <code>File</code> to write content to.
     * @param classContent <code>String</code> with file content.
     * @return <code>String</code> with file path or <code>null</code> if failed to write to received <code>File</code>
     * or file with such path already exists.
     */
    public static String writeToNewFile(File file, String classContent) {
        if (file.exists()) {
            log.warn(() -> "File already exists: " + file.getAbsolutePath());
            return null;
        }
        return writeToFile(file, classContent);
    }

    /**
     * Writes received content to file with received path if there is no already file with such path.
     *
     * @param filePath     <code>String</code> with path to file to write content to.
     * @param classContent <code>String</code> with file content.
     * @return <code>String</code> with file path or <code>null</code> if failed to write to received <code>File</code>
     * or file with such path already exists.
     */
    public static String writeToNewFile(String filePath, String classContent) {
        return writeToNewFile(new File(filePath), classContent);
    }
}
