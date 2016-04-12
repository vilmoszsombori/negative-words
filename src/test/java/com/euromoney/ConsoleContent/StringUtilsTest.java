package com.euromoney.ConsoleContent;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void testFill_negativeLenght() {
        assertEquals("", StringUtils.fill(-5, 'n'));
    }

    @Test
    public void testFill_zeroLenght() {
        assertEquals("", StringUtils.fill(0, 'z'));
    }
    
    @Test
    public void testFill_positiveLenght() {
        assertEquals("pppp", StringUtils.fill(4, 'p'));
    }    
    
}
