package com.java;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFreq2 {
    public static void main(String[] args) {
        Path path = Paths.get("///ugamtest.txt");
        try {
            String text = Files.readString(path); // throws java.io.IOException
            text = text.toLowerCase();
            Pattern pttrn = Pattern.compile("[a-z]+");
            Matcher mtchr = pttrn.matcher(text);
            TreeMap<String, Integer> freq = new TreeMap<>();
            int longest = 0;
            while (mtchr.find()) {
                String word = mtchr.group();
                int letters = word.length();
                if (letters > longest) {
                    longest = letters;
                }
                if (freq.containsKey(word)) {
                    freq.computeIfPresent(word, (w, c) -> Integer.valueOf(c.intValue() + 1));
                } else {
                    freq.computeIfAbsent(word, (w) -> Integer.valueOf(1));
                }
            }
            String format = "%-" + longest + "s = %2d%n";
            freq.forEach((k, v) -> System.out.printf(format, k, v));
            System.out.println("Longest = " + longest);
        } catch (IOException xIo) {
            xIo.printStackTrace();
        }

    }
    }
