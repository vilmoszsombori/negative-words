package com.euromoney.ConsoleContent;

import java.util.Arrays;

public class StringUtils {

    /**
     * 
     * @param length
     * @param character
     * @return a string filled with character of the given length
     */
    public static String fill(int length, char character) {
        if (length <= 0) {
            return "";
        } else {
            char[] array = new char[length];
            Arrays.fill(array, character);
            return new String(array);
        }
    }
    
}
