import java.util.ArrayList;
import java.util.*;

public class WordSquare 
{

	static class Pair implements Comparator<Pair>
	{
		String first;
		String second;
		
		public Pair(String first, String second)
		{
			this.first = first;
			this.second = second;
		}
		
		public int compare(Pair p0, Pair p1)
		{
			if (p0.first.equals(p1.first))
			{
				return p0.second.compareTo(p1.second);
			}
			return  p0.first.compareTo(p1.first);
		}
		
	}
	
	//find two words such that the first one(word) has the middle letter equal to 
	//the first or the last letter of the second(currentWord)
	public static HashSet<Pair> matchPairs(ArrayList<String> dictionary)
	{
		HashSet<Pair> pairMatch = new HashSet<Pair>();
		for (int i = 0; i < dictionary.size(); i++)
		{
			String word = dictionary.get(i);
			for (int j = 0; j < dictionary.size(); j++)
			{
				String currentWord = dictionary.get(j);	
				if (currentWord.charAt(0) == word.charAt(1) || currentWord.charAt(2) == word.charAt(1))
				{
					String possibleMatch = currentWord;
					pairMatch.add(new Pair(possibleMatch, word));
				}  
		    }
		}
		return pairMatch;	
	}
	
	//find pairs such that they share the "backbone" word
	public static boolean matchPairsToTriplets(String middleWord, String topWord, String bottomWord)
	{
		if (middleWord.charAt(0) == topWord.charAt(1) && middleWord.charAt(2) == bottomWord.charAt(1))
			return true;
		else if (middleWord.charAt(0) == bottomWord.charAt(1) && middleWord.charAt(2) == topWord.charAt(1))
		    return true;
		return false;	
	}
	
	//find the list of triplets that obey the rule of diagonals(diagonals are words in the dictionary) 
	public static ArrayList<ArrayList<String>> buildTriplet(HashSet<Pair> wardPairs, ArrayList<String> dictionary)
	{
		ArrayList<ArrayList<String>> triplets = new ArrayList<ArrayList<String>>();
		for (Pair pair0 : wardPairs)
		{
			String middleWord = pair0.first;
			for (Pair pair1: wardPairs)
			{
				if (pair1.first.equals(middleWord))
				{
					    ArrayList<String> entry = new ArrayList<String>();
						String topWord = pair0.second;
						String bottomWord = pair1.second;
					    
						if (wordInDictionary(middleWord.charAt(1), topWord.charAt(0), bottomWord.charAt(2), dictionary) && 
							wordInDictionary(middleWord.charAt(1), topWord.charAt(2), bottomWord.charAt(0), dictionary))
						{
							if (matchPairsToTriplets(middleWord, bottomWord, topWord))
							{
								entry.add(middleWord);
								entry.add(topWord);
								entry.add(bottomWord);
								triplets.add(entry);
							}
						}
  				}
			}
		}
		return triplets;
	}
	
	//verify if the word is in the dictionary 
	public static boolean wordInDictionary(char middle, char c1, char c2, ArrayList<String> dictionary)
	{
		String word1 = new String(new char[]{c1, middle, c2});
		String word2 = new String(new char[]{c2, middle, c1});
		for (int i= 0; i < dictionary.size(); i++)
		{
			
			if (dictionary.get(i).equals(word1) || dictionary.get(i).equals(word2))
				return true;
		}
        return false;	
	}
	
	//find a second line word such that the first and third column are words in the dictionary
	public static void searchSolution(ArrayList<ArrayList<String>> triplets, ArrayList<String> dictionary, HashSet<String> wordSet)
	{
		for (int i = 0; i < triplets.size(); i++)
		{
		    	ArrayList<String> triplet = triplets.get(i);
		    	for (int j = 0; j < dictionary.size(); j++)
		    	{
		    		verifyAllConditionsForWordSquare(triplet, dictionary.get(j), wordSet);		
		    	}	
		}
	}
	
	public static HashSet<String> createWordSet(ArrayList<String> dict)
	{
		HashSet<String> set = new HashSet<String>();
		for (int i = 0; i < dict.size(); i++)
		{
			set.add(dict.get(i));	
		}
		return set;
	}
	
	//verify all condition and print if solution found
	public static boolean verifyAllConditionsForWordSquare(ArrayList<String> triplet, String word, HashSet<String> dictionary)
	{
		char[] w1 = triplet.get(0).toCharArray();
		char[] w2 = triplet.get(1).toCharArray();
		char[] w3 = triplet.get(2).toCharArray();
		char[] w4 = word.toCharArray();
		
		//normal reading
		String wordOnColumnZero = new String(new char[]{w2[0], w4[0], w3[0]});
		String wordOnColumnZeroReverse = new String(new char[]{w3[0], w4[0], w2[0]});
		String wordOnColumnTwo = new String(new char[]{w2[2], w4[2], w3[2]});
		String wordOnColumnTwoReversed = new String(new char[]{w3[2], w4[2], w2[2]});
        
		//reverse reading
		String reverseWordOnColumnZero = new String(new char[]{w2[0], w4[2], w3[0]});
		String reverseWordOnColumnZeroReverse = new String(new char[]{w3[0], w4[2], w2[0]});
		String reverseWordOnColumnTwo = new String(new char[]{w2[2], w4[0], w3[2]});
        String reverseWordOnColumnTwoReversed = new String(new char[]{w3[2], w4[0], w2[2]});
        
		//if word does not have the same middle letter as the word on the second column
		if (word.charAt(1) != w1[1])
			return false;
		else if ((dictionary.contains(wordOnColumnZero) || dictionary.contains(wordOnColumnZeroReverse)) &&
				(dictionary.contains(wordOnColumnTwo) || dictionary.contains(wordOnColumnTwoReversed)))
		{
			System.out.println(triplet.get(1));
			System.out.println(word);
			System.out.println(triplet.get(2));
			System.out.println();
			return true;
		}
		
		else if ((dictionary.contains(reverseWordOnColumnZero) || dictionary.contains(reverseWordOnColumnZeroReverse)) &&
			(dictionary.contains(reverseWordOnColumnTwo) || dictionary.contains(reverseWordOnColumnTwoReversed)))
		{
			System.out.println(triplet.get(1));
			System.out.println(new String(new char[]{w4[2], w4[1], w4[0]}));
			System.out.println(triplet.get(2));
			System.out.println();
			return true;
		}
		return false;
	}
	
	public static void main(String args[])
	{
		ArrayList<String> dictionary = new ArrayList()
		{{
			add("ana");
			add("aaa");
			add("cna");
			add("arc");
			add("nan");
			add("ala");
			add("nur");
		}};
		HashSet<Pair> pairs = matchPairs(dictionary);
		ArrayList<ArrayList<String>> tripletList = buildTriplet(pairs, dictionary); 
		HashSet<String> wordset = createWordSet(dictionary);
		searchSolution(tripletList, dictionary, wordset);

	}
}

