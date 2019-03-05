package com.example.urmi.eloquence;

public class WordsList {
    private String[] wordsList = new String[] {
            "please", "great", "sled", "pants",
            "rat", "bad", "pinch", "such", "bus",
            "need", "ways", "five", "mouth", "rag",
            "put", "fed", "fold", "hunt", "no", "box",
            "are", "teach", "slice", "is", "tree"
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
