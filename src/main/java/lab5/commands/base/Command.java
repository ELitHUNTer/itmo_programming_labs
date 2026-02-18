package lab5.commands.base;

public interface Command {

    /**
     * Starts execution of a command
     * @param args arguments for command
     */
    void execute(String... args);

    /**
     * Get Description of a command
     * @return description of a command
     */
    String getDescription();

}
