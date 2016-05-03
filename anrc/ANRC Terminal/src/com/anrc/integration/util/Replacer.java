package com.anrc.integration.util;

public enum Replacer {
    INSTANCE;
    private static final String REGEX = "[?]";
    public static String replaceFirst(String text, String replace){
        return text.replaceFirst(REGEX, replace);
    }
    public static String replaceFirst(String text, int replace){
        return text.replaceFirst(REGEX, Integer.toString(replace));
    }
    public static String replaceFirst(String text, double replace){
        return text.replaceFirst(REGEX, Double.toString(replace));
    }
    public static String replaceFirst(String text, java.time.LocalDate replace, String format){
        return text.replaceFirst(REGEX,DateFormatter.toString(format, replace));
    }
    public static String replaceFirst(String text, boolean replace){
        return text.replaceFirst(REGEX,Boolean.toString(replace));
    }
    public static String replaceFirst(String text, java.time.LocalDate date,java.time.LocalTime time, String format){
        return text.replaceFirst(REGEX,DateFormatter.toString(format, date, time));
    }
}
