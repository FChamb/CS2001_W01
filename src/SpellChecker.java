import java.util.Arrays;

public class SpellChecker implements ISpellChecker {

    String[] dictionary;
    boolean validWord = false;

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
        if (input.length > 1) {
            checker.runChecker(input);
        } else {
            checker.check(input[0]);
        }
    }

    @Override
    public void runChecker(String[] words) {

    }

    @Override
    public SpellCheckResult check(String word) {
        int index = Arrays.binarySearch(this.dictionary, 0, this.dictionary.length, word);
        if (index != -1) {
            this.validWord = true;
        }
        return null;
    }
}
