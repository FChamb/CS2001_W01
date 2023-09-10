import java.util.Arrays;

/**
 * SpellChecker class contains several methods which work together to take a user input and
 * appropriate a valid response. This class implements the ISpellChecker interface class, as
 * the building ground for which to create dictionary checking methods.
 */
public class SpellChecker implements ISpellChecker {

    private String[] dictionary;

    /**
     * The default constructor for SpellChecker does one task. It fills the private
     * attribute, dictionary, with the results of DictionaryLoader's loadDictionary
     * method. This provides the class with a string array of all the dictionary words
     * sorted and formatted properly.
     */
    public SpellChecker() {
        this.dictionary = DictionaryLoader.getInstance().loadDictionary();
    }

    /**
     * The main method of SpellChecker acts as the connection between the user
     * and the core of the spell checking methods. The array of strings, args, is
     * used to take a user input. First the length of the array is checked to see
     * whether the user properly input any words to be checked. A try catch statement
     * grabs any ArrayIndexOutOfBoundsException and reports the appropriate method
     * to the user. If all user arguments are valid they are first transformed to
     * lower case and then run through runChecker.
     * @param args string array of user inputted arguments
     */
    public static void main(String[] args) {
        SpellChecker checker = new SpellChecker();
        try {
            if (args.length == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java SpellChecker <words_to_check>");
            System.exit(1);
        }
        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].toLowerCase();
        }
        checker.runChecker(args);
    }

    /**
     * runChecker takes an array of words to check. A new instance variables
     * is created for the result array of SpellCheckResult.
     * First a for loop passes every word to the check method and puts the result
     * into the above-mentioned SpellCheckResult array. Then another for loop determines
     * if each result is correct or invalid. The appropriate statements are printed.
     * @param words the array of words to check
     */
    @Override
    public void runChecker(String[] words) {
        SpellCheckResult[] results = new SpellCheckResult[words.length];
        for (int i = 0; i < words.length; i++) {
            results[i] = check(words[i]);
        }
        for (int i = 0; i < results.length; i++) {
            if (results[i].isCorrect()) {
                System.out.println(words[i] + " correct");
            } else {
                System.out.println(words[i] + " not found - nearest neighbour(s) " + results[i].getBefore() + " and " + results[i].getAfter());
            }
        }
    }

    /**
     * The check method takes a singular word to search. Using the java.util.Arrays binary
     * search method, the index of the closest or valid word is returned. If the index is a
     * positive number than a new SpellCheckResult is returned with a true boolean value.
     * If the index is not positive, its value is converted to positive and the word before/after
     * is added to the returned SpellCheckResult.
     * @param word the word to be checked
     * @return the SpellCheckResult instance, which represents the outcome of the search
     */
    @Override
    public SpellCheckResult check(String word) {
        int index = Arrays.binarySearch(this.dictionary, word);
        if (index > 0) {
            return new SpellCheckResult(true, null, null);
        } else {
            index = Math.abs(index + 1);
        }
        return new SpellCheckResult(false, dictionary[index - 1], dictionary[index]);
    }

    /**
     * getDictionary is an accessor method which returns the instance of dictionary
     * in this class.
     * @return a string array of dictionary words
     */
    public String[] getDictionary() {
        return this.dictionary;
    }
}
