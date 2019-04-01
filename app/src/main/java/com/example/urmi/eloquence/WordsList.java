package com.example.urmi.eloquence;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordsList {
    private List<String> wordsList = new ArrayList<String>(Arrays.asList(
            "please",
            "bus",
            "need",
            "great",
//            "sled",
//            "pants",
//            "rat",
//            "bad",
            "pinch",
            "such",
//            "ways",
//            "five",
//            "mouth",
//            "rag",
//            "put",
//            "fed",
//            "fold",
//            "hunt",
//            "no",
//            "box",
//            "are",
//            "teach",
//            "slice",
//            "is",
            "tree"
//            "smile",
//            "bath",
//            "slip",
//            "ride",
//            "end",
//            "pink",
//            "thank",
//            "take",
//            "cart",
//            "scab",
//            "lay",
//            "class",
//            "me",
//            "dish",
//            "neck",
//            "beef",
//            "few",
//            "use",
//            "did",
//            "hit",
//            "pond",
//            "hot",
//            "own",
//            "bead",
//            "shop",
//            "laugh",
//            "falls",
//            "paste",
//            "plow",
//            "page",
//            "weed",
//            "gray",
//            "park",
//            "wait",
//            "fat",
//            "ax",
//            "cage",
//            "knife",
//            "turn",
//            "grab",
//            "rose",
//            "lip",
//            "bee",
//            "bet",
//            "his",
//            "sing",
//            "all",
//            "bless",
//            "suit",
//            "splash",
//            "path",
//            "feed",
//            "next",
//            "wreck",
//            "waste",
//            "crab",
//            "peg",
//            "freeze",
//            "race",
//            "bud",
//            "darn",
//            "fair",
//            "sack",
//            "got",
//            "as",
//            "grew",
//            "knee",
//            "fresh",
//            "tray",
//            "cat",
//            "on",
//            "camp",
//            "find",
//            "yes",
//            "loud"
    ));

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