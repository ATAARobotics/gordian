package org.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Class used to interact with text files.
 *
 * @author Joel Gallant
 */
public class TextFiles {

    /**
     * Returns a string representation of all the contents inside of a file.
     *
     * @param file file to get string from
     * @return contents of the file
     * @throws FileNotFoundException thrown when file does not exist
     */
    public static String getStringFromFile(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        try {
            return new Scanner(file).useDelimiter("\\A").next().trim();
        } catch (java.util.NoSuchElementException e) {
            return "";
        }
    }

    /**
     * Returns a string representation of all the contents inside of an
     * {@link InputStream}.
     *
     * @param file input stream with text contents
     * @return contents of input stream
     */
    public static String getStringFromFile(InputStream file) {
        try {
            return new Scanner(file).useDelimiter("\\A").next().trim();
        } catch (Exception e) {
            return "";
        }
    }
}
