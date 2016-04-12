package com.euromoney.ConsoleContent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {

	public static final String PROPERTIES_FILE = "negative-words.properties";
	public static final String NEGATIVE_WORDS = "negativeWords";
	public static final char HASH = '#';

	private static Properties properties = null;

	private Set<String> negativeWords = new HashSet<>();

	public Analyzer() {
		// load properties file just once
		if (properties == null)
			loadNegariveWords();
		if (properties.containsKey(NEGATIVE_WORDS))
			setNegativeWords(properties.getProperty(NEGATIVE_WORDS));
	}

	public long analyze(String content) {
		if (content == null || content.isEmpty())
			return 0;
		return Arrays.stream(content.split("[\\W]")).filter(w -> negativeWords.contains(w)).count();
	}

	public String censor(String content) {
		if (content != null && !content.isEmpty())
			for (String negativeWord : negativeWords) {
				// horrible --> (h)(orribl)(e)
				Pattern pattern = Pattern.compile(
						  "(" + negativeWord.charAt(0) + ")"
						+ "(" + negativeWord.substring(1, negativeWord.length() - 1) + ")"
						+ "(" + negativeWord.charAt(negativeWord.length() - 1) + ")",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(content);
				StringBuffer replacement = new StringBuffer();
				while (matcher.find()) {
					matcher.appendReplacement(replacement,
							matcher.group(1) + StringUtils.fill(matcher.group(2).length(), HASH) + matcher.group(3));
				}
				matcher.appendTail(replacement);
				content = replacement.toString();
			}
		return content;
	}

	public void setNegativeWords(String words) {
		negativeWords.clear();
		if (words != null && !words.isEmpty())
			Arrays.stream(words.split("[\\W]")).map(String::toLowerCase).forEach(w -> negativeWords.add(w));
	}
	
	public void setNegativeWords(Set<String> negativeWords) {
        this.negativeWords.clear();
        if (negativeWords != null && !negativeWords.isEmpty())
            negativeWords.stream().map(String::toLowerCase).forEach(w -> this.negativeWords.add(w));
	}

	public Set<String> getNegativeWords() {
		return this.negativeWords;
	}

	private void loadNegariveWords() {
		properties = new Properties();
		try (InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties.load(fileStream);
		} catch (IOException e) {
			System.err.println("Cannot load properties file with the negative words.");
		}
	}

}
