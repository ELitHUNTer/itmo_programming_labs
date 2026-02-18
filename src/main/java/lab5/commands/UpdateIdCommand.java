package lab5.commands;

import lab5.CollectionController;
import lab5.commands.base.CollectionCommand;

public class UpdateIdCommand extends CollectionCommand {

    public UpdateIdCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        // TODO
    }

    @Override
    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
