package org.reader;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Main reader of the script. Uses {@code runScript} as a class method to run a
 * string.
 *
 * @author joel
 */
public class ScriptReader {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void main(String[] args) throws FileNotFoundException {
//        if (args.length != 1) {
//            System.out.println("Invalid arguments.");
//            return;
//        }
//        String s = TextFiles.getStringFromFile(new File(args[0]));
//        System.out.println("Running script in file: "+new File(args[0]).getPath());
        String s = TextFilesUtils.getStringFromFile(new File("/home/joel/Documents/testscript.txt"));
        ScriptReader.runScript(s);
    }

    /**
     * Runs a script. Instructions are split by line separators.
     *
     * @param fullScript string of the full script
     */
    public static void runScript(String fullScript) {
        int line = 0;
        String[] s = fullScript.split(LINE_SEPARATOR);
        while (line < s.length) {
            try {
                if (s[line].trim().isEmpty() || s[line].trim().startsWith("**")) {
                    // This eleminates errors / comments being processed
                    continue;
                }
                try {
                    if (s[line].contains("**")) {
                        s[line] = s[line].substring(0, s[line].indexOf("**"));
                    }
                    Statement currentLine = Statement.getStatementFrom(s[line].trim());
                    currentLine.line = line;
                    currentLine.fullScript = fullScript;
                    if (currentLine instanceof Instruction) {
                        ((Instruction) currentLine).run();
                    }
                    line += currentLine.linesToSkip;
                } catch (InvalidStatementException ex) {
                    ex.printStackTrace(System.out);
                    line++;
                }
            } catch (RuntimeException ex) {
                throw new RuntimeException("Runtime exception on line " + (line + 1), ex);
            }
        }
    }
}