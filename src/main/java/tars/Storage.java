package tars;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the loading and saving of tasks to and from a file.
 *
 * <p>The Storage class is responsible for reading tasks from a specified file and
 * writing tasks back to that file. It supports task types such as Todo, Deadline,
 * and Event, and ensures that tasks are stored in a consistent format.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage object that manages tasks stored at the specified file path.
     *
     * @param filePath the file path where tasks will be loaded from and saved to.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file specified by the file path.
     *
     * <p>Reads the file line by line, parses each line into a task object,
     * and adds it to the list. Supports three types of tasks: Todo, Deadline,
     * and Event. If the file does not exist, an empty list is returned.
     *
     * @return a list of tasks loaded from the file.
     * @throws TarsException if the file contains corrupt data, invalid date formats,
     *         or if there is an error reading the file.
     */
    public List<Task> loadTasks() throws TarsException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) {
                    throw new TarsException("Corrupt task data: " + line);
                }
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                try {
                    switch (type) {
                    case "T":
                        tasks.add(new Todo(description, isDone));
                        break;
                    case "D":
                        if (parts.length < 4) {
                            throw new TarsException("Corrupt deadline task data: " + line);
                        }
                        String by = parts[3];
                        tasks.add(new Deadline(description, isDone, by));
                        break;
                    case "E":
                        if (parts.length < 5) {
                            throw new TarsException("Corrupt event task data: " + line);
                        }
                        String from = parts[3];
                        String to = parts[4];
                        tasks.add(new Event(description, isDone, from, to));
                        break;
                    default:
                        throw new TarsException("Unknown task type: " + type);
                    }
                } catch (DateTimeParseException e) {
                    throw new TarsException("Invalid date format in task data: " + line);
                }
            }
        } catch (IOException e) {
            throw new TarsException("Error reading from file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves the given list of tasks to the file specified by the file path.
     *
     * <p>Each task is converted to a string in a specific format and written
     * to the file. If the file's directory does not exist, it will be created.
     *
     * @param tasks the list of tasks to save to the file.
     * @throws TarsException if there is an error writing to the file.
     */
    public void saveTasks(List<Task> tasks) throws TarsException {
        File file = new File(filePath);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            try {
                directory.mkdirs(); // Create directory
            } catch (SecurityException e) {
                System.out.println("There was a security/permission issue: " + e.getMessage());
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                bw.write(taskToFileString(task));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new TarsException("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Converts a Task object into a formatted string for saving to a file.
     *
     * @param task the task to convert to a string.
     * @return a string representation of the task in the save file format.
     */
    private String taskToFileString(Task task) {
        String type = "";
        String additionalInfo = "";

        if (task instanceof Todo) {
            type = "T";
        } else if (task instanceof Deadline) {
            type = "D";
            additionalInfo = " | " + ((Deadline) task).getDate();
        } else if (task instanceof Event) {
            type = "E";
            additionalInfo = " | " + ((Event) task).getFrom() + " | " + ((Event) task).getTo();
        }
        return type + " | " + (task.getIsDone() ? "1" : "0") + " | " + task.getName() + additionalInfo;
    }
}
