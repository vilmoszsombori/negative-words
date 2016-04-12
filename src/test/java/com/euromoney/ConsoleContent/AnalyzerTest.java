package com.euromoney.ConsoleContent;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class AnalyzerTest {
    
    private Analyzer analyzer; 

    @Before
    public void setUp() throws Exception {
        analyzer = new Analyzer();
    }

    @Test
    @UseDataProvider("provideStory1Data")
    public void story1_analyze(String content, long expectedNegativeWordscount) {
        assertEquals(expectedNegativeWordscount, analyzer.analyze(content));
    }

    @DataProvider
    public static Object[][] provideStory1Data() {
        return new Object[][] {
            {"The weather in Manchester in winter is bad. It rains all the time - it must be horrible for people visiting.", 2}, 
            {"The weather in Manchester in winter is horrible. It rains all the time - it must be horrible for people visiting.", 2}, 
            {"The weather in Manchester in winter is bad. It rains all the time - it must be horrible for swine visiting.", 3}, 
            {"The weather in Manchester in winter is great. The sun shines all the time - it must be brilliant for people visiting.", 0},
            {"", 0},
            {null, 0}
        };
    }    
    
    @Test
    public void story2_setNegativeWords() {
        analyzer.setNegativeWords("dreadful,shocking,appalling,ghastly");
        assertTrue(analyzer.getNegativeWords().contains("ghastly"));
        assertFalse(analyzer.getNegativeWords().contains("horrible"));        
    }

    @Test
    @UseDataProvider("provideStory2Data")
    public void story2_analyze(String content, String negativeWords, long expectedNegativeWordscount) {
        analyzer.setNegativeWords(negativeWords);
        assertEquals(expectedNegativeWordscount, analyzer.analyze(content));
    }
    
    @DataProvider
    public static Object[][] provideStory2Data() {
        return new Object[][] {
            {"The weather in Manchester in winter is bad. It rains all the time - it must be horrible for people visiting.", "swine,bad,nasty,horrible", 2}, 
            {"The weather in Manchester in winter is bad. It rains all the time - it must be horrible for people visiting.", "dreadful,shocking,appalling,ghastly", 0}, 
            {"The weather in Manchester in winter is dreadful. It rains all the time - it must be ghastly for people visiting.", "dreadful,shocking,appalling,ghastly", 2}, 
            {"The weather in Manchester in winter is bad. It rains all the time - it must be horrible for people visiting.", "", 0}, 
            {"The weather in Manchester in winter is bad. It rains all the time - it must be horrible for people visiting.", null, 0}, 
        };
    }
    
    @Test
    @UseDataProvider("provideStory3Data")
    public void story3_censor(String content, String expected) {        
        String censored = analyzer.censor(content);
        assertEquals(expected, censored);
    }
    
    @DataProvider
    public static Object[][] provideStory3Data() {
        return new Object[][] {
            {
            	"The weather in Manchester in winter is bad. It rains all the time - it must be horrible for people visiting.",
            	"The weather in Manchester in winter is b#d. It rains all the time - it must be h######e for people visiting.", 
            }, 
            {
            	"Horrible weather. In Manchester it is a bad winter. It's a horrible rain all the time.",  
            	"H######e weather. In Manchester it is a b#d winter. It's a h######e rain all the time.",  
            }, 
        };
    }        
    
}