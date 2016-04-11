package com.euromoney.ConsoleContent;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

public class ProgramTest {

    @Test
    public final void testMain_noArguments() throws IOException {
        PrintStream stdout = System.out;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream custom = new PrintStream(baos);            
            System.setErr(custom);
            Program.main(new String[] {});
            assertTrue(baos.toString().contains("No arguments provided"));
        } finally {
            System.setErr(stdout);        
        }
    }

    @Test
    public final void testMain_story1() throws IOException {
        InputStream stdin = System.in;
        PrintStream stdout = System.out;
        try {
            System.setIn(this.getClass().getClassLoader().getResourceAsStream("content-test.txt"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream custom = new PrintStream(baos);            
            System.setOut(custom);
            Program.main(new String[] {"-u"});
            assertTrue(baos.toString().contains("Total number of banned words: 2"));
        } finally {
            System.setIn(stdin);
            System.setOut(stdout);        
        }
    }

}
