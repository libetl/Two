package org.twixml.technoproxy.jsoup;

import java.util.EventObject;

public class ActionEvent extends EventObject {

    long id;
    String actionCommand;
    long when;
    int modifiers;

    /*
     * JDK 1.1 serialVersionUID
     */
    private static final long serialVersionUID = -7671078796273832149L;

    public ActionEvent(Object source, int id, String command) {
        this(source, id, command, 0);
    }

    /**
     * Constructs an <code>ActionEvent</code> object with modifier keys.
     * <p>
     * Note that passing in an invalid <code>id</code> results in
     * unspecified behavior. This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     * A <code>null</code> <code>command</code> string is legal,
     * but not recommended.
     *
     * @param source    the object that originated the event
     * @param id        an integer that identifies the event
     * @param command   a string that may specify a command (possibly one
     *                  of several) associated with the event
     * @param modifiers the modifier keys held down during this action
     * @throws IllegalArgumentException if <code>source</code> is null
     */
    public ActionEvent(Object source, int id, String command, int modifiers) {
        this(source, id, command, 0, modifiers);
    }

    /**
     * Constructs an <code>ActionEvent</code> object with the specified
     * modifier keys and timestamp.
     * <p>
     * Note that passing in an invalid <code>id</code> results in
     * unspecified behavior. This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     * A <code>null</code> <code>command</code> string is legal,
     * but not recommended.
     *
     * @param source    the object that originated the event
     * @param id        an integer that identifies the event
     * @param command   a string that may specify a command (possibly one
     *                  of several) associated with the event
     * @param when      the time the event occurred
     * @param modifiers the modifier keys held down during this action
     * @throws IllegalArgumentException if <code>source</code> is null
     *
     * @since 1.4
     */
    public ActionEvent(Object source, int id, String command, long when,
                       int modifiers) {
        super (source);
        this.id = id;
        this.actionCommand = command;
        this.when = when;
        this.modifiers = modifiers;
    }

    /**
     * Returns the command string associated with this action.
     * This string allows a "modal" component to specify one of several
     * commands, depending on its state. For example, a single button might
     * toggle between "show details" and "hide details". The source object
     * and the event would be the same in each case, but the command string
     * would identify the intended action.
     * <p>
     * Note that if a <code>null</code> command string was passed
     * to the constructor for this <code>ActionEvent</code>, this
     * this method returns <code>null</code>.
     *
     * @return the string identifying the command for this event
     */
    public String getActionCommand() {
        return actionCommand;
    }

    /**
     * Returns the timestamp of when this event occurred. Because an
     * ActionEvent is a high-level, semantic event, the timestamp is typically
     * the same as an underlying InputEvent.
     *
     * @return this event's timestamp
     * @since 1.4
     */
    public long getWhen() {
        return when;
    }

    /**
     * Returns the modifier keys held down during this action event.
     *
     * @return the bitwise-or of the modifier constants
     */
    public int getModifiers() {
        return modifiers;
    }
}
