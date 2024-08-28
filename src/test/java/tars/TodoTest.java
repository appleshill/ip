package tars;

import org.junit.Test;
import static org.junit.Assert.*;

public class TodoTest {

    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("Read book", false);
        assertEquals("Read book", todo.getName());
        assertFalse(todo.isDone());
    }

    @Test
    public void testMarkDone() {
        Todo todo = new Todo("Read book", false);
        todo.setDone();
        assertTrue(todo.isDone());
    }

    @Test
    public void testMarkUndone() {
        Todo todo = new Todo("Read book", true);
        todo.setUndone();
        assertFalse(todo.isDone());
    }

    @Test
    public void testToString() {
        Todo todo = new Todo("Read book", false);
        assertEquals("[T][ ] Read book", todo.toString());
        todo.setDone();
        assertEquals("[T][X] Read book", todo.toString());
    }
}
