package syllableCounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import stopwatch.TaskTimer;

public class Main implements Runnable {
	public final static String DICT_URL = "http://se.cpe.ku.ac.th/dictionary.txt";
	public static InputStream input;
	public static BufferedReader reader;
	public String word;
	public int words = 0;
	public int syllables = 0;

	public static BufferedReader openDict() {
		try {
			URL url = new URL(DICT_URL);
			input = url.openStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new BufferedReader(new InputStreamReader(input));
	}

	public void run() {
		WordCounter countSyl = new WordCounter();
		reader = openDict();
		try {
			while ((word = reader.readLine()) != null) {
				if (word.length() != 0 && countSyl.countSyllables(word) != 0)
					words++;
				syllables += countSyl.countSyllables(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.printf("Counted %d syllables in %d words\n", syllables, words);
	}

	public String toString() {
		return "Reading words from " + DICT_URL;
	}

	public static void main(String[] args) {
		Main main = new Main();
		TaskTimer timer = new TaskTimer();
		timer.measureAndPrint(main);
	}
}
