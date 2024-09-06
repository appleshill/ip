package tars;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of tasks and handles operations such as adding, removing,
 * marking tasks as done or undone, and searching for tasks.
 *
 * <p>The TaskList class also interacts with the Storage class to persist the tasks to a file.
 */
public class TaskList {
    private static final String ERROR_OUT_OF_BOUNDS = "The specified task number is out of bounds.";
    private List<Task> tasks;
    private Storage storage;

    /**
     * Constructs a TaskList with an empty task list and the specified storage.
     *
     * @param storage the Storage object used to save and load tasks.
     */
    public TaskList(Storage storage) {
        assert storage != null : "Storage should not be null.";
        this.tasks = new ArrayList<>();
        this.storage = storage;
    }


    /**
     * Constructs a TaskList with a pre-existing list of tasks and the specified storage.
     *
     * @param tasks the list of tasks to be managed by this TaskList.
     * @param storage the Storage object used to save and load tasks.
     */
    public TaskList(List<Task> tasks, Storage storage) {
        assert tasks != null : "Tasks list should not be null.";
        assert storage != null : "Storage should not be null.";
        this.tasks = tasks;
        this.storage = storage;
    }

    /**
     * Adds a new task to the task list and saves the updated list to storage.
     *
     * @param task the task to be added.
     * @throws TarsException if there is an error saving the tasks.
     */
    public void addTask(Task task) throws TarsException {
        assert task != null : "Task should not be null.";
        tasks.add(task);
        saveTasks();
    }

    /**
     * Removes a task from the task list at the specified index and saves the updated list to storage.
     *
     * @param index the index of the task to be removed.
     * @throws TarsException if the specified index is out of bounds or if there is an error saving the tasks.
     */
    public void removeTask(int index) throws TarsException {
        if (index < 0 || index >= tasks.size()) {
            throw new TarsException(ERROR_OUT_OF_BOUNDS);
        }
        tasks.remove(index);
        saveTasks();
    }

    /**
     * Saves the current list of tasks to storage.
     *
     * @throws TarsException if there is an error during the save process.
     */
    public void saveTasks() throws TarsException {
        assert tasks != null : "Tasks list should not be null when saving.";
        try {
            storage.saveTasks(this.tasks);
        } catch (TarsException e) {
            throw new TarsException("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Marks the task at the specified index as done and saves the updated list to storage.
     *
     * @param index the index of the task to be marked as done.
     * @return the task that was marked as done.
     * @throws TarsException if the specified index is out of bounds or if there is an error saving the tasks.
     */
    public Task markTaskDone(int index) throws TarsException {
        if (index < 0 || index >= tasks.size()) {
            throw new TarsException(ERROR_OUT_OF_BOUNDS);
        }
        Task task = tasks.get(index);
        assert task != null : "Task at index should not be null.";
        task.setDone();
        saveTasks();
        return task;
    }

    /**
     * Marks the task at the specified index as not done and saves the updated list to storage.
     *
     * @param index the index of the task to be marked as not done.
     * @return the task that was marked as not done.
     * @throws TarsException if the specified index is out of bounds or if there is an error saving the tasks.
     */
    public Task markTaskUndone(int index) throws TarsException {
        if (index < 0 || index >= tasks.size()) {
            throw new TarsException(ERROR_OUT_OF_BOUNDS);
        }
        Task task = tasks.get(index);
        assert task != null : "Task at index should not be null.";
        task.setUndone();
        saveTasks();
        return task;
    }

    /**
     * Returns a formatted string representation of all tasks in the task list.
     *
     * @return a string listing all tasks with their indices.
     */
    public String listTasks() {
        StringBuilder result = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            result.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return result.toString().trim();
    }

    /**
     * Finds all tasks in the task list that contain the specified keyword in their description.
     * The search is case-insensitive.
     *
     * @param keyword The keyword to search for in the task descriptions.
     * @return A list of tasks that contain the keyword in their descriptions.
     */
    public List<Task> findTasks(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            assert task.getName() != null : "Task name should not be null.";
            if (task.getName().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    public int getSize() {
        assert tasks != null : "Tasks list should not be null when getting size.";
        return tasks.size();
    }

    /**
     * Edits a task at the specified index with the given option and new value, then saves the updated task list.
     *
     * @param editIndex the index of the task to be edited
     * @param option the option specifying what aspect of the task to edit
     * @param newValue the new value to set for the specified task
     * @return the updated {@link Task} object after editing
     * @throws TarsException if {@code editIndex} is out of bounds of the task list
     */
    public Task editAndSave(int editIndex, String option, String newValue) throws TarsException {
        if (editIndex < 0 || editIndex >= tasks.size()) {
            throw new TarsException(ERROR_OUT_OF_BOUNDS);
        }
        Task task = tasks.get(editIndex);
        task.edit(option, newValue);
        saveTasks();
        return task;
    }
}
