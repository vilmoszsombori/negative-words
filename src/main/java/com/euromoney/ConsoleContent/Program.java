package com.euromoney.ConsoleContent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Program {

    /**
     * Initialises the application in the console.
     * 
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws IOException {

        Options options = new Options();

        options.addOption("u", "user", false, "Story 1: users see the number of negative words");
        options.addOption("a", "admin", true, "Story 2: admins can change the negative words. Arg: comma-separated negative words, ex. \"dreadful,ghastly\"");
        options.addOption("r", "read", false, "Story 3: readers' text is censored (negative words are rewritten)");
        options.addOption("c", "curate", false, "Story 4: curators see both the original text and the count of negative words");

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);

            Analyzer analyzer = new Analyzer();
            
            if (args.length == 0) {
                throw new ParseException("No arguments provided");
            } else if (commandLine.hasOption("u") || commandLine.hasOption("c")) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String content = br.readLine();
                long i = analyzer.analyze(content);
                System.out.println("\nScanned the text sequence: " + content + "\n");
                System.out.println("\nTotal number of banned words: " + i + "\n");
            } else if (commandLine.hasOption("a")) {
                String negativeWords = commandLine.getOptionValue("a");
                analyzer.setNegativeWords(negativeWords);
                System.out.println("\nNegative words updated to: " + analyzer.getNegativeWords() + "\n");
            } else if (commandLine.hasOption("r")) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String content = br.readLine();
                String censored = analyzer.censor(content);
                System.out.println("\nCensored text: " + censored + "\n");
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("com.euromoney.ConsoleContent.Program", options);            
        }

    }

}
