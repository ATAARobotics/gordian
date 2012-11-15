package edu.ata.script.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Class used to interact with text files.
 *
 * @author Joel Gallant
 */
public class TextFilesUtils {

    private static final String DELIMETER_STRING = "\\A";

    /**
     * Returns a string representation of all the contents inside of a file.
     *
     * @param filePath file path to get string from
     * @return contents of the file
     * @throws FileNotFoundException thrown when file does not exist
     */
    public static String getStringFromFile(String filePath) throws FileNotFoundException {
        return getStringFromFile(new FileInputStream(filePath));
    }

    /**
     * Returns a string representation of all the contents inside of a file.
     *
     * @param file file to get string from
     * @return contents of the file
     * @throws FileNotFoundException thrown when file does not exist
     */
    public static String getStringFromFile(File file) throws FileNotFoundException {
        return getStringFromFile(new FileInputStream(file));
    }

    /**
     * Returns a string representation of all the contents inside of an
     * {@link InputStream}.
     *
     * @param stream input stream with text contents
     * @return contents of input stream
     */
    public static String getStringFromFile(InputStream stream) {
        Scanner scanner = new Scanner(stream).useDelimiter(DELIMETER_STRING);
        if (!scanner.hasNext()) {
            return "";
        }
        return scanner.next().trim();
    }
}
