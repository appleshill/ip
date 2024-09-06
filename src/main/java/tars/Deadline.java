package tars;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;


/**
 * Represents a task with a deadline, including a due date.
 *
 * <p>The Deadline class extends the Task class by adding a due date to the task.
 * It uses the {@link DateTimeParser} class to parse and format date strings. The class provides
 * methods to get the due date, change the due date, and generate a string representation of the task.
 */
public class Deadline extends Task {
    private static final String INVALID_DATE_MESSAGE = "Invalid date format. Please use the format: yyyy-MM-dd HHmm.";
    private static final String NAME_OPTION = "/name";
    private static final String BY_OPTION = "/by";
    private LocalDateTime dueDate;

    /**
     * Creates a new Deadline task with a specified name, completion status, and due date.
     * The date is parsed using the {@link DateTimeParser} class. If the date cannot be parsed,
     * a {@link TarsException} is thrown.
     *
     * @param name The name or description of the deadline task.
     * @param done Indicates whether the task is completed or not.
     * @param date The due date of the task in the format "yyyy-MM-dd HHmm" or "dd MMM yyyy, HH:mm".
     * @throws TarsException If the provided date cannot be parsed into a {@link java.time.LocalDateTime}.
     */
    public Deadline(String name, boolean done, String date) throws TarsException {
        super(name, done);
        try {
            this.dueDate = DateTimeParser.parse(date);
        } catch (DateTimeParseException e) {
            throw new TarsException(INVALID_DATE_MESSAGE);
        }
    }

    /**
     * Changes the due date of the deadline task.
     *
     * <p>The new date is parsed using the {@link DateTimeParser} class. If the new date cannot
     * be parsed, a {@link TarsException} is thrown.
     *
     * @throws TarsException if the new date cannot be parsed into a {@link java.time.LocalDateTime}.
     */
    public String getDate() {
        return DateTimeParser.format(this.dueDate);
    }

    /**
     * Changes the due date of the deadline task.
     *
     * <p>The new date is parsed using the {@link DateTimeParser} class. If the new date cannot
     * be parsed, a {@link TarsException} is thrown.
     *
     * @param newDate the new due date in the format "yyyy-MM-dd HHmm" or "dd MMM yyyy, HH:mm".
     * @throws TarsException if the new date cannot be parsed into a {@link java.time.LocalDateTime}.
     */
    public void changeDate(String newDate) throws TarsException {
        try {
            this.dueDate = DateTimeParser.parse(newDate);
        } catch (DateTimeParseException e) {
            throw new TarsException(INVALID_DATE_MESSAGE);
        }
    }

    /**
     * Returns a string representation of this deadline task.
     * The format of the string is "[D] [task description] (by: [due date])".
     *
     * @return A string representation of the deadline task.
     */
    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + getDate() + ")";
    }

    @Override
    public void edit(String option, String newValue) throws TarsException {
        switch (option) {
        case NAME_OPTION:
            setName(newValue);
            break;
        case BY_OPTION:
            changeDate(newValue);
            break;
        default:
            throw new TarsException("Invalid edit option for deadline. Use /name or /date.");
        }
    }

}

