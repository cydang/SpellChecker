package cs146F20.dang.project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Checks if words in the peom/song are in the dictionary
 * @author chloedang
 *
 */
public class WordChecker {
	private static RedBlackTree dictionary;
	private static int wordsNotInDictionary;
	
	/**
	 * 
	 * @param d
	 * @param poem
	 */
	public WordChecker(File d, File poem) {
		dictionary = fileToRBT(d);
		wordsNotInDictionary = wordCheckFile(poem);
	}
	
	/**
	 * gets number from wordsNotInDictionary variable
	 * @return
	 */
	public static int getwordsNotInDictionaryCount() {
		return wordsNotInDictionary;
	}
	
	/**
	 * inserts the dictionary file into a Red Black Tree
	 */
	public RedBlackTree fileToRBT(File file) {
		RedBlackTree dictionary = new RedBlackTree();
		
		// scans each line of the file
		Scanner s; 
		try {
			s = new Scanner(file);
			while (s.hasNextLine()) { 
				dictionary.insert(s.nextLine());
			}
			s.close();
			return dictionary;
		} 
		//e is the exception thrown is file input is invalid
		catch (FileNotFoundException e) {
			System.out.println(e); 
			return null;
		}
	}
	
	/**
	 * checks the words in the file using lookup()
	 * @param file
	 * @return number of words not in the dictionary
	 */
	public int wordCheckFile(File file) {
		
		int wordsNotInDictionary = 0;
		
		// scans each line of the file
		Scanner s; 
		try {
			s = new Scanner(file);
			// while there is a next line in the file that needs to be read
			while (s.hasNextLine()) { 
				wordsNotInDictionary += wordCheckLine(s.nextLine());
			}
			s.close();
		} 
		// exception thrown if file input is invalid
		catch (FileNotFoundException e) {
			System.out.println(e); 
		}
		return wordsNotInDictionary;
	}
	
	/**
	 * removes punctuation marks and separates string into words
	 * @param line
	 * @return array of words
	 */
	public String[] parseLineIntoArray(String line) {
		String[] parsedLines = line.trim().split("[\\p{Punct}\\s]+"); 
		return parsedLines;
	}
	
	/**
	 * returns total number of words not found in the dictionary
	 * @param line
	 * @return
	 */
	public int wordCheckLine(String line) {
		String[] words = parseLineIntoArray(line);
		int wordsNotInDictionary = 0;
		// if the array is empty indicating an empty line, do nothing
		for(String w: words) {
			if (w.isEmpty()) {
				
			}
			// else if the word in the array isn't found in the dictionary, increment wordsNotInDictionary count
			else if(dictionary.lookup(w.toLowerCase()) == null)
				wordsNotInDictionary++;
		}
		return wordsNotInDictionary;
	}
	
	
}
