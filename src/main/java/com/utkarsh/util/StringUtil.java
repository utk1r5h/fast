package com.utkarsh.util;

public class StringUtil{

    public static boolean isPalindrome(String str){
        String cleaned = str.replaceAll("\\s+", "").toLowerCase();
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }

    public static String reverse(String str){
        return new StringBuilder(str).reverse().toString();
    }

    public static String concatenateWithString(String[] words){
        String result = "";
        for(String word : words){
            result += word + " ";
        }
        return result.trim();
    }

    public static String concatenateWithStringBuilder(String[] words){
        StringBuilder sb = new StringBuilder();
        for(String word : words){
            sb.append(word).append(" ");
        }
        return sb.toString().trim();
    }

    public static String concatenateWithStringBuffer(String[] words){
        StringBuffer sb = new StringBuffer();
        for(String word : words){
            sb.append(word).append(" ");
        }
        return sb.toString().trim();
    }

    public static long measureConcatenationPerformance(String[] words, int method){
        long start = System.nanoTime();
        switch(method){
            case 1 -> concatenateWithString(words);
            case 2 -> concatenateWithStringBuilder(words);
            case 3 -> concatenateWithStringBuffer(words);
        }
        return System.nanoTime() - start;
    }

    public static boolean contains(String text, String keyword){
        return text.toLowerCase().contains(keyword.toLowerCase());
    }

    public static String substring(String text, int start, int end){
        if(start < 0 || end > text.length() || start > end){
            throw new IllegalArgumentException("Invalid substring indices");
        }
        return text.substring(start, end);
    }
}
