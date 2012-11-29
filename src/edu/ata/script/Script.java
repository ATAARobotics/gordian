package edu.ata.script;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author Joel Gallant
 */
public class Script {

    public static void run(String full) {
        Data.DATA_STORAGE.clear();
        new Script(full).run();
    }
    
    private final String[] parts;
    private String block = "";

    public Script(String full) {
        StringTokenizer tokenizer = new StringTokenizer(full, ";\n\r");
        Vector parts = new Vector();
        int bracesCount = 0;
        while (tokenizer.hasMoreTokens()) {
            String next = tokenizer.nextToken().trim();
            if (next.startsWith("#")) {
                continue;
            }
            if (StringUtils.contains(next, "{")) {
                bracesCount++;
                addToBlock(next);
            } else if (StringUtils.contains(next, "}")) {
                if (--bracesCount == 0) {
                    parts.add(block + next + ";");
                    block = "";
                } else {
                    addToBlock(next);
                }
            } else if (block.length() > 0) {
                addToBlock(next);
            } else {
                parts.add(next);
            }
        }
        this.parts = new String[parts.size()];
        for (int x = 0; x < parts.size(); x++) {
            this.parts[x] = ((String) parts.get(x)).trim();
        }
    }

    public String[] getParts() {
        return parts;
    }

    public void run() {
        for (int x = 0; x < parts.length; x++) {
            try {
                if (Instruction.isType(parts[x])) {
                    Instruction.get(parts[x]).run();
                } else {
                    System.err.println("Part " + (x + 1) + " is not an instruction - "
                            + parts[x]);
                }
            } catch (Throwable ex) {
                System.err.println("Error occured in part " + (x + 1) + " - "
                        + parts[x] + "\n\t" + ex.getMessage());
                ex.printStackTrace(System.err);
            }
        }
    }

    private void addToBlock(String instruction) {
        block += (instruction + ";");
    }
}
