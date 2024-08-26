import java.util.Objects;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Tars {

    private List<Task> tasks;
    private Storage storage;

    public Tars() {
        this.storage = new Storage("./data/tars.txt");
        try {
            tasks = storage.loadTasks();
        } catch (TarsException e) {
            tasks = new ArrayList<>();
            System.out.println("There was an error loading tasks: " + e.getMessage());
        }
    }

    // Takes the input
    public String doSomething(String input) throws TarsException {
        String[] words = input.split(" ", 2);
        String first = words[0];
        String second = words.length > 1 ? words[1] : "";
        return switch (first) {
            case "bye" -> ("Bye. Hope to see you again soon!");
            case "list" -> listAllTasks();
            case "mark" -> {
                if (!second.isEmpty()) {
                    yield markDone(second);
                } else {
                    yield "Please specify which task to mark.";
                }
            }
            case "unmark" -> {
                if (!second.isEmpty()) {
                    yield markUndone(second);
                } else {
                    yield "Please specify which task to unmark.";
                }
            }
            case "todo" -> {
                if (second.isEmpty()) {
                    throw new TarsException("The todo is missing a description. Please fill one in.");
                }
                Todo todo = new Todo(second, false);
                addTask(todo);
                yield getResponse(todo);
            }
            case "deadline" -> {
                if (second.isEmpty()) {
                    throw new TarsException("Please specify the deadline in the following format: deadline task /by date");
                }
                String[] parts = second.split(" /by ", 2);
                if (parts.length > 1) {
                    Deadline deadline = new Deadline(parts[0], false, parts[1]);
                    addTask(deadline);
                    yield getResponse(deadline);
                } else {
                    throw new TarsException("Please specify the deadline in the following format: deadline task /by date");
                }
            }
            case "event" -> {
                if (second.isEmpty()) {
                    throw new TarsException("Please specify the event in the following format: event task /from time /to time");
                }
                String[] parts = second.split(" /from | /to ", 3);
                if (parts.length > 2) {
                    Event event = new Event(parts[0], false, parts[1], parts[2]);
                    addTask(event);
                    yield getResponse(event);
                } else {
                    yield "Please specify the event in the following format: event task /from time /to time";
                }
            }
            case "remove" -> {
                if (second.isEmpty()) {
                    throw new TarsException("Please specify the index of the task you want to remove");
                }
                try {
                    int index = Integer.parseInt(second.trim());
                    yield removeAndRespond(index);
                } catch (NumberFormatException e) {
                    throw new TarsException("The specified index is not a valid number.");
                }

            }
            case "humour" -> ("One hundred percent");
            case "honesty" -> ("Ninety percent");
            case "caution" -> ("Cooper, this is no time for caution!");
            default -> "I'm sorry, I can't quite help you with that";
        };
    }

    private String listAllTasks() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            result.append(i + 1).append(". ").append(tasks.get(i).toString()).append("\n");
        }
        return ("Here are the tasks in your list: \n" + result.toString().trim());
    }

    private void addTask(Task task) throws TarsException{
        this.tasks.add(task);
        saveTasks();
    }

    private String markDone(String input) throws TarsException{
        if (input.isEmpty()) return "Task could not be found!";
        boolean canFind = false;
        for (Task currTask : this.tasks) {
            String taskName = currTask.getName();
            if (Objects.equals(taskName, input)) {
                canFind = true;
                currTask.setDone();
                saveTasks();
                return ("Nice! I've marked this task as done:\n" + "  " + currTask.toString());
            }
        }
        return ("The task you specified cannot be found. Please try again");
    }
    private String markUndone(String input) throws TarsException{
        if (input.isEmpty()) return "Task could not be found!";
        boolean canFind = false;
        for (Task currTask : this.tasks) {
            String taskName = currTask.getName();
            if (Objects.equals(taskName, input)) {
                canFind = true;
                currTask.setUndone();
                saveTasks();
                return ("OK, I've marked this task as not done yet:\n" + "  " + currTask.toString());
            }
        }
        return ("The task you specified cannot be found. Please try again");
    }

    private String getResponse(Task task) {
        return "Got it. I've added this task: \n" + task.toString() + "\n" + "Now you have "
                + this.tasks.size() + " tasks in the list\n";
    }

    private String removeAndRespond(int idx) throws TarsException, IndexOutOfBoundsException{
        if (idx <= 0 || idx > tasks.size()) {
            throw new IndexOutOfBoundsException("The specified task number is out of bounds.");
        }
        String taskDescription = tasks.get(idx - 1).toString();
        tasks.remove(idx - 1);
        saveTasks();
        return "Noted. I've removed this task:\n" + taskDescription + "\n" + "Now you have "
                + this.tasks.size() + " tasks in the list\n";
    }

    private void saveTasks() throws TarsException {
        try {
            storage.saveTasks(this.tasks);
        } catch (TarsException e) {
            System.out.println("Error saving task: "+ e.getMessage());
        }
    }
    public static void main(String[] args) throws TarsException {
        System.out.println("____________________________________");
        System.out.println("Hello! I'm TARS");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________");

        Tars tars = new Tars();
        Scanner scanner = new Scanner(System.in);
        String input;
        String output;
        do {
            input = scanner.nextLine();
            output = tars.doSomething(input);
            System.out.println("____________________________________");
            System.out.println(output);
            System.out.println("____________________________________");
        } while (!input.equals("bye"));

        scanner.close();


    }
}
