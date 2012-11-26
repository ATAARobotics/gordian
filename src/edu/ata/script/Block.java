package edu.ata.script;

/**
 * @author Joel Gallant
 */
public class Block {

    private final String[] parts;

    public Block(String full) {
        parts = StringUtils.split(full, ';');
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
}
