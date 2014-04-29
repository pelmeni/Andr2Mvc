package com.example.andr2mvc.andr2mvc;

/**
 * Created by PichuginSI on 29.04.2014.
 */
public  class ArrayUtil {
    public static int[] convert(String[] string) {
        int number[] = new int[string.length];

        for (int i = 0; i < string.length; i++) {
            number[i] = Integer.parseInt(string[i]); // error here
        }
        return number;
    }
}
