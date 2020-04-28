package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		TrieNode root = new TrieNode(null, null, null);
		if(allWords.length == 0) return root;
		root.firstChild = new TrieNode(new Indexes(0, (short)0, (short)(allWords[0].length() - 1)), null, null);
		TrieNode ptr = root.firstChild, before = root.firstChild;
		int min = -1;
		int start = min;
		int end = min;
		int index = min; 
		int upto = min;
		for(int i = 1; i < allWords.length; i++) {
			String curr = allWords[i];
			while(ptr!=null) {
				start = ptr.substr.startIndex;
				end = ptr.substr.endIndex;
				index = ptr.substr.wordIndex;
				if(start > curr.length()) {
					before = ptr;
					ptr = ptr.sibling;
					continue;
				}
				upto = similar(allWords[index].substring(start, end+1), curr.substring(start));
				if(upto != min) upto = upto + start;
				if(upto == min) {
					before = ptr;
					ptr = ptr.sibling;
				}
				else {
					if(upto == end) {
						before = ptr;
						ptr = ptr.firstChild;
					}
					else if(upto < end) {
						before = ptr;
						break;
					}
				}
			}
			if(ptr == null) {
				Indexes ind = new Indexes(i, (short)start, (short)(curr.length()-1)); //C
				before.sibling = new TrieNode(ind, null, null);
			}
			else {
				TrieNode currchild = before.firstChild;
				Indexes cind = before.substr;
				Indexes wordcind = new Indexes(cind.wordIndex, (short)(upto+1), cind.endIndex);
				cind.endIndex = (short)upto;
				before.firstChild = new TrieNode(wordcind, null, null);
				before.firstChild.firstChild = currchild;
				before.firstChild.sibling = new TrieNode(new Indexes((short)i, (short)(upto+1), (short)(curr.length()-1)), null, null);
			}
			ptr = root.firstChild;
			before = root.firstChild;
			start = min;
			end = min;
			index = min; 
			upto = min;
		}
		return root;
	}
	
	private static int similar(String a, String b) {
		int index = 0;
		while(index < a.length() && index < b.length() && a.charAt(index) == b.charAt(index))
			index++;
		return index-1;
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix) {
		ArrayList<TrieNode> arr = new ArrayList<TrieNode>();
		if(root == null) return null;
		for(TrieNode ptr = root; ptr != null; ptr = ptr.sibling) {
			if(ptr.substr == null) ptr = ptr.firstChild;
			String word = allWords[ptr.substr.wordIndex];
			String pre = word.substring(0, ptr.substr.endIndex+1);
			if(word.startsWith(prefix) || prefix.startsWith(pre)) {
				if(ptr.firstChild == null) {
					arr.add(ptr);
					continue;
				}
				else arr.addAll(completionList(ptr.firstChild, allWords, prefix));
				continue;
			}
		}
		return arr;
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
