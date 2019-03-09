package com.example.urmi.eloquence;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordsList {
    private List<String> wordsList = new ArrayList<String>(Arrays.asList("please", "bus", "need"));

    public List<String> getAllWords () {
        return wordsList;
    }

    public String getWordAt (int index) {
        return wordsList.get(index);
    }

    public int getWordsLength () {
        return wordsList.size();
    }

    @TargetApi(Build.VERSION_CODES.N)
    public List<String> getTestWords (int num) {
        List<String> words = new ArrayList<String>();
        int[] rands = new Random().ints(0, wordsList.size()).limit(num).toArray();
        for (int n: rands) {
            words.add(wordsList.get(n));
        }
        return words;
    }
}