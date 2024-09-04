package tars;

/**
 * Represents a task with a name and a completion status.
 *
 * <p>The Task class is a simple representation of a task, consisting of a name or description
 * and a boolean indicating whether the task is completed or not. It provides methods to
 * get and set the name and completion status, as well as a method to return a formatted
 * string representation of the task.
 */
public class Task {
    private String name;
    private boolean isDone;

    /**
     * Constructs a new Task with the specified name and completion status.
     *
     * @param name the name or description of the task.
     * @param isDone the completion status of the task; {@code true} if the task is done,
     *             {@code false} if the task is not done.
     */
    public Task(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void setDone() {
        this.isDone = true;
    }

    public void setUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return ("[" + (this.isDone ? "X" : " ") + "] " + this.name);
    }
}
