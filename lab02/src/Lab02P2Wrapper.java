public class Lab02P2Wrapper {


    /**
     * Exercise #2
     * Returns true if and only if a patterns (a String) appears inside a text at least once
     *
     * YOUR METHOD MUST BE RECURSIVE.  Non-recursive methods will receive half the credit obtained.
     * HINT: The startsWith method from the String class may come in handy
     *
     * @param pattern
     * @param text
     * @return
     */

    public static void main(String[] args) {
        String text = "DatadataDataStructures";
        String pattern = "at";
        boolean out = patternExists(pattern, text);
        System.out.println(out);

    }
    public static boolean patternExists(String pattern, String text) {
        if(text.length()-pattern.length() < 0)
            return false;

        if(text.startsWith(pattern))
            return true;

        return patternExists(pattern, text.substring(1)); //Dummy Return
    }

}