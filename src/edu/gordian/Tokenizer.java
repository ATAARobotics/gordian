package edu.gordian;

import java.util.StringTokenizer;

public class Tokenizer extends StringTokenizer {

    public Tokenizer(String str) {
        super(str, ";");
    }
}
