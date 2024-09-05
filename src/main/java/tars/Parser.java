package tars;

/**
 * Provides utility methods to parse user input into commands and arguments for the TARS application.
 *
 * <p>The Parser class is responsible for taking raw input from the user and breaking it down
 * into a command and its associated arguments. This allows the application to understand and
 * execute the user's intended actions.
 */
public class Parser {

    /**
     * Parses the user input into a command and its arguments.
     *
     * @param input the raw input string from the user
     * @return an array where the first element is the command and the second element is the arguments (if any)
     */
    public static String[] parse(String input) {
        String[] result = new String[2];
        String[] splitInput = input.trim().split(" ", 2);
        result[0] = splitInput[0];
        result[1] = splitInput.length > 1 ? splitInput[1] : "";
        return result;
    }
}
