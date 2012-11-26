package edu.ata.script;

import java.util.Vector;

/**
 * @author Joel Gallant
 */
public class Script {

    private final String[] parts;

    public Script(String full) {
        full = StringUtils.replace(full, '\n', ";");
        String[] parts1 = StringUtils.split(full, ';');
        Vector parts2 = new Vector();
        for (int x = 0; x < parts1.length; x++) {
            if (!parts1[x].startsWith("#") && !parts1[x].trim().isEmpty()) {
                parts2.add(parts1[x]);
            }
        }
        Vector parts3 = new Vector(parts2.size());
        String tmp = "";
        for (int x = 0; x < parts2.size(); x++) {
            if (((String) parts2.get(x)).startsWith("$")) {
                if (tmp.isEmpty()) {
                    tmp += ((String) parts2.get(x)) + ";";
                } else {
                    parts3.add(tmp + "$");
                    tmp = "";
                }
            } else if (!tmp.isEmpty()) {
                tmp += ((String) parts2.get(x)) + ";";
            } else {
                parts3.add(parts2.get(x));
            }
        }
        this.parts = new String[parts3.size()];
        for (int x = 0; x < parts3.size(); x++) {
            this.parts[x] = ((String) parts3.get(x)).trim();
        }
    }

    public String[] getParts() {
        return parts;
    }

    public void run() {
        Data.DATA_STORAGE.clear();
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
}
