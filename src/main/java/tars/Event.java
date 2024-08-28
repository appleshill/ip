package tars;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Event extends Task{

    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates a new Event task with a specified name, completion status, start time, and end time.
     * The start and end times are parsed using the {@link DateTimeParser} class. If either of the dates
     * cannot be parsed, a {@link TarsException} is thrown.
     *
     * @param name The name or description of the event task.
     * @param done Indicates whether the task is completed or not.
     * @param from The start time of the event in the format "yyyy-MM-dd HHmm" or "dd MMM yyyy, HH:mm".
     * @param to The end time of the event in the format "yyyy-MM-dd HHmm" or "dd MMM yyyy, HH:mm".
     * @throws TarsException If either the start or end time cannot be parsed into a {@link java.time.LocalDateTime}.
     */
    public Event (String name, boolean done, String from, String to) throws TarsException{
        super(name, done);
        try {
            this.from = DateTimeParser.parse(from);
            this.to = DateTimeParser.parse(to);
        } catch (DateTimeParseException e) {
            throw new TarsException("Invalid date format. Please use the format: yyyy-MM-dd HHmm.");
        }
    }

    public void setFrom(String newFrom) throws TarsException {
        try {
            this.from = DateTimeParser.parse(newFrom);
        }catch (DateTimeParseException e) {
            throw new TarsException("Invalid date format. Please use the format: yyyy-MM-dd HHmm.");
        }
    }

    public void setTo(String newTo) throws TarsException {
        try {
            this.to = DateTimeParser.parse(newTo);
        } catch (DateTimeParseException e) {
            throw new TarsException("Invalid date format. Please use the format: yyyy-MM-dd HHmm.");
        }
    }

    public String getFrom() {
        return DateTimeParser.format(this.from);
    }

    public String getTo() {
        return DateTimeParser.format(this.to);
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + getFrom() + " to: " + getTo() + ")";
    }
}
