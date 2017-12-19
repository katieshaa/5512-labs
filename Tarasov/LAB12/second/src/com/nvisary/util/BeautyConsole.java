package com.nvisary.util;

public class BeautyConsole {
    public static void redPrintLn(String text) {
        System.out.println((char) 27 + "[31m" + text + (char)27 + "[0m");
    }

    public static void greenPrintLn(String text) {
        System.out.println((char) 27 + "[32m" + text + (char)27 + "[0m");
    }
    public static void greenPrint(String text) {
        System.out.print((char) 27 + "[32m" + text + (char)27 + "[0m");
    }
    public static void printLn(String text, int index) {
        System.out.println((char) 27 + "[" + index + "m" + text + (char) 27 + "[0m");
    }
    public static void print(String text, int index) {
        System.out.print((char) 27 + "[" + index + "m" + text + (char) 27 + "[0m");
    }
}
