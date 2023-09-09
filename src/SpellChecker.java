import java.util.Arrays;

public class SpellChecker implements ISpellChecker {

    String[] dictionary;

    public SpellChecker() {
        this.dictionary = DictionaryLoader.getInstance().loadDictionary();
    }

    public static void main(String[] args) {
        SpellChecker checker = new SpellChecker();
        String[] input = new String[args.length];
        try {
            if (input.length == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java SpellChecker");
            System.exit(1);
        }
        for (int i = 0; i < args.length; i++) {
            input[i] = args[i].toLowerCase();
        }
        checker.runChecker(input);
    }

    @Override
    public void runChecker(String[] words) {
        int index = 0;
        SpellCheckResult[] results = new SpellCheckResult[words.length];
        for (int i = 0; i < words.length; i++) {
            results[i] = check(words[i]);
        }
        for (SpellCheckResult result : results) {
            if (result.isCorrect()) {
                System.out.println(words[index] + " correct");
            } else {
                System.out.println(words[index] + " not found - nearest neighbour(s) " + result.getBefore() + " and " + result.getAfter());
            }
            index++;
        }
    }

    @Override
    public SpellCheckResult check(String word) {
        int index = Arrays.binarySearch(this.dictionary, word);
        if (index > 0) {
            return new SpellCheckResult(true, null, null);
        } else {
            index = Math.abs(index);
        }
        return new SpellCheckResult(false, dictionary[index - 1], dictionary[index + 1]);
    }
}
