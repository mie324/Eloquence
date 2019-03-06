package com.example.urmi.eloquence;

public class WordsList {
    private String[] wordsList = new String[] {
            "please", "bus", "need"
    };

    public String[] getAllWords () {
        return wordsList;
    }

    public String getWordAt (int index) {
        return wordsList[index];
    }

    public int getWordsLength () {
        return wordsList.length;
    }
}
