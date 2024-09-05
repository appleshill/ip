package tars;

import java.util.List;

/**
 * The main class for the TARS application, which manages tasks and interacts with the user.
 *
 * <p>The Tars class initializes the user interface, loads tasks from storage, and contains
 * the main application loop that processes user commands. It supports various commands for
 * task management, including adding, marking, unmarking, listing, removing, and finding tasks.
 */
public class Tars {
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a Tars object, initializing the user interface and loading tasks from storage.
     *
     * <p>The constructor attempts to load tasks from the specified file. If an error occurs
     * during loading, an empty task list is created instead.
     */
    public Tars() {
        ui = new Ui();
        Storage storage = new Storage("./data/tars.txt");
        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.loadTasks(), storage);
        } catch (TarsException e) {
            loadedTasks = new TaskList(storage);
        }
        tasks = loadedTasks;
    }

    /**
     * Processes the user's input and returns a response based on the command provided.
     *
     * This method parses the user's input into a command and its arguments, executes the
     * corresponding action, and returns the appropriate response message.
     * The supported commands include:
     *
     * @param input the user's input string.
     * @return a response message based on the command executed.
     */
    public String getResponse(String input) {
        String[] parsedInput = Parser.parse(input);
        String command = parsedInput[0];
        String arguments = parsedInput[1];

        try {
            switch (command) {
            case "bye":
                return ui.getGoodbyeMessage();
            case "list":
                return ui.getResponseMessage(tasks.listTasks());
            case "mark":
                int markIndex = Integer.parseInt(arguments.trim()) - 1;
                Task markedTask = tasks.markTaskDone(markIndex);
                return ui.getTaskMarkedResponse(markedTask);
            case "unmark":
                int unmarkIndex = Integer.parseInt(arguments.trim()) - 1;
                Task unmarkedTask = tasks.markTaskUndone(unmarkIndex);
                return ui.getTaskUnmarkedResponse(unmarkedTask);
            case "todo":
                Task todo = new Todo(arguments, false);
                tasks.addTask(todo);
                return ui.getTaskAddedResponse(todo, tasks.getSize());
            case "deadline":
                String[] deadlineParts = arguments.split(" /by ", 2);
                Task deadline = new Deadline(deadlineParts[0], false, deadlineParts[1]);
                tasks.addTask(deadline);
                return ui.getTaskAddedResponse(deadline, tasks.getSize());
            case "event":
                String[] eventParts = arguments.split(" /from | /to ", 3);
                Task event = new Event(eventParts[0], false, eventParts[1], eventParts[2]);
                tasks.addTask(event);
                return ui.getTaskAddedResponse(event, tasks.getSize());
            case "remove":
                int removeIndex = Integer.parseInt(arguments.trim()) - 1;
                tasks.removeTask(removeIndex);
                return ui.getTaskRemovedResponse("Task removed", tasks.getSize());
            case "find":
                List<Task> foundTasks = tasks.findTasks(arguments.trim());
                return ui.getFoundTasksResponse(foundTasks);
            default:
                return ui.getResponseMessage("I'm sorry, I can't quite help you with that.");
            }
        } catch (TarsException e) {
            return ui.getResponseMessage(e.getMessage());
        } catch (NumberFormatException e) {
            return ui.getResponseMessage("Invalid number format.");
        } catch (Exception e) {
            return ui.getResponseMessage("An unexpected error occurred: " + e.getMessage());
        }
    }
}

