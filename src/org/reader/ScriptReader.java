package org.reader;

import java.io.File;
import java.io.FileNotFoundException;
import org.reader.Statement.InvalidStatementException;
import org.reader.instruction.Method;
import org.reader.instruction.methods.Keywords;

/**
 * Main reader of the script. Uses {@code runScript} as a class method to run a
 * string.
 *
 * @author joel
 */
public class ScriptReader {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Invalid arguments.");
            return;
        }
        String s = TextFiles.getStringFromFile(new File(args[0]));
        System.out.println("Running script in file: "+new File(args[0]).getPath());
        ScriptReader.runScript(s);
    }

    /**
     * Runs a script. Instructions are split by line separators.
     *
     * @param fullScript string of the full script
     */
    public static void runScript(String fullScript) {
        int line = 0;
        try {
            String[] s = fullScript.split(System.getProperty("line.separator"));
            Statement currentLine = null;
            for (; line < s.length; line += ((currentLine == null) ? 1 : currentLine.linesToSkip)) {
                if (s[line].trim().isEmpty() || s[line].trim().startsWith("**")) {
                    // This eleminates errors / comments being processed
                    continue;
                }
                try {
                    if (s[line].contains("**")) {
                        s[line] = s[line].substring(0, s[line].indexOf("**"));
                    }
                    currentLine = Statement.getStatementFrom(s[line].trim());
                    currentLine.line = line;
                    currentLine.fullScript = fullScript;
                    if (currentLine instanceof Runnable) {
                        ((Instruction) currentLine).run();
                    }
                } catch (InvalidStatementException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        } catch (RuntimeException ex) {
            throw new RuntimeException("Runtime exception on line " + (line + 1), ex);
        }
    }
}
