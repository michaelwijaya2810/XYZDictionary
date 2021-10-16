package com.michaelwijaya.xyzdictionary;

import java.util.ArrayList;

public class Words {
    private String word;
    private ArrayList<Definitions> definitions;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<Definitions> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<Definitions> definitions) {
        this.definitions = definitions;
    }
}
