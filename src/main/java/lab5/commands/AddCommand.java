package lab5.commands;

import lab5.CollectionController;
import lab5.commands.base.CollectionCommand;

import java.io.InputStream;

public class AddCommand extends CollectionCommand {

    public AddCommand(CollectionController controller, InputStream source) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        // TODO
    }

    @Override
    public String getDescription() {
        return "add {Имя Здоровье} : добавить новый элемент в коллекцию";
    }
}
