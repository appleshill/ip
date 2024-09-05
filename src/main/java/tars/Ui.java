package tars;

import java.util.List;
import java.util.Scanner;

/**
 * Handles the user interface for the TARS application, providing methods
 * to read user input and display responses.
 *
 * <p>The UI class is responsible for interacting with the user, displaying
 * messages, and receiving commands through the console. It provides various
 * methods to print different types of messages, such as task updates, errors,
 * and greetings.
 */
public class Ui {
    private final Scanner scanner;
    private final String line = "____________________________________";

    /**
     * Constructs a new UI object and initializes the scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a line of input from the user.
     *
     * @return the input string entered by the user.
     */
    public String readInput() {
        return scanner.nextLine();
    }

    /**
     * Generates a welcome message to be displayed when the application starts.
     *
     * @return a welcome message string.
     */
    public String getWelcomeMessage() {
        return "Hello! I'm TARS\nWhat can I do for you?";
    }

    /**
     * Generates a goodbye message to be displayed when the application exits.
     *
     * @return a goodbye message string.
     */
    public String getGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Generates a formatted response message, surrounded by lines.
     *
     * @param response the response message to be formatted.
     * @return a formatted response message string.
     */
    public String getResponseMessage(String response) {
        return "____________________________________\n" + response + "\n____________________________________";
    }

    /**
     * Generates a response message indicating that a task has been marked as done.
     *
     * @param task the task that was marked as done.
     * @return a response message indicating the task was marked as done.
     */
    public String getTaskMarkedResponse(Task task) {
        return "Nice! I've marked this task as done:\n  " + task.toString();
    }

    /**
     * Generates a response message indicating that a task has been marked as not done.
     *
     * @param task the task that was marked as not done.
     * @return a response message indicating the task was marked as not done.
     */
    public String getTaskUnmarkedResponse(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task.toString();
    }

    /**
     * Generates a response message indicating that a task has been added to the task list.
     *
     * @param task the task that was added.
     * @param size the current number of tasks in the list.
     * @return a response message indicating the task was added.
     */
    public String getTaskAddedResponse(Task task, int size) {
        return "Got it. I've added this task:\n" + task.toString() + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Generates a response message indicating that a task has been removed from the task list.
     *
     * @param taskDescription the description of the task that was removed.
     * @param size the current number of tasks in the list.
     * @return a response message indicating the task was removed.
     */
    public String getTaskRemovedResponse(String taskDescription, int size) {
        return "Noted. I've removed this task:\n" + taskDescription + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Generates a response message displaying tasks that match a search query.
     * If no tasks match, an appropriate message is returned.
     *
     * @param foundTasks the list of tasks that match the search query.
     * @return a response message displaying the matching tasks or indicating that no tasks were found.
     */
    public String getFoundTasksResponse(List<Task> foundTasks) {
        if (foundTasks.isEmpty()) {
            return "No matching tasks found.";
        } else {
            StringBuilder response = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < foundTasks.size(); i++) {
                response.append(i + 1).append(". ").append(foundTasks.get(i)).append("\n");
            }
            return response.toString().trim();
        }
    }

    /**
     * Generates an error message if there is an issue loading tasks from storage.
     *
     * @return an error message indicating a problem with loading tasks.
     */
    public String getLoadingError() {
        return "There was an error loading tasks.";
    }

}
