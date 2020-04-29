/*************************************************************************
 *  Compilation:  javac RunLengthEncoding.java
 *  Execution:    java RunLengthEncoding
 *
 *  @author:
 *
 *************************************************************************/

public class RunLengthEncoding {

    /* 
     * Encode the original string by finding sequences in the string
     * where the same character repeats. Replace each such sequence
     * by a token consisting of: the number of characters in the sequence
     * followed by the repeating character.
     * Return the encoded string.
     */
    public static String encode (String original)  {
        String encoded = "";
        char current = original.charAt(0);
        int count = 1;
        for(int i = 1; i < original.length(); i++)
        {
           if(original.charAt(i) == current) {
              count++;
           } 
           else 
           {
              if(count > 1) encoded += ""+ count;
              encoded += current;
              current = original.charAt(i);
              count = 1;
           }
        }
        if(count > 1) encoded += count;
        encoded += current;
        return encoded;
      }
    /*
     * Decodes the original string encoded with the encode method.
     * Returns the decoded string.
     *
     * YOUR decode METHOD MUST BE RECURSIVE, do not use while, do/while, 
     * or for loops
     */
    public static String decode (String original) {
	String second = original;
    if(second.length() <= 1) return second;
    else {
        char current = second.charAt(0);
        char next = second.charAt(1);
        if(!Character.isDigit(current)){
            second = second.substring(1);
        }
        else {
            int num = current - '0';
            if(num == 1){
                second = second.substring(2);
            }
            else {
                current--;
                second = current + second.substring(1);
            }
            current = next;
        }
        return current + decode(second);
    }
}
    public static void main (String[] args) {
    	System.out.println(encode("aaabbbccc"));
    	System.out.println(decode("3a4b5c"));
    }
}
