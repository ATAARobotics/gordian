package edu.ata.script;

public class InstructionIterator {

    private String transcript;

    public InstructionIterator(String transcript) {
        this.transcript = transcript;
    }

    public boolean hasNext() {
        return transcript.length() > 0;
    }

    public Instruction getNext() throws NullPointerException {
        Instruction i = Instruction.getNextInstruction(transcript);
        transcript = transcript.substring(i.getLiteral().length()).trim();
        while(transcript.indexOf(";") == 0) {
            transcript = transcript.substring(1).trim();
        }
        return i;
    }
}
