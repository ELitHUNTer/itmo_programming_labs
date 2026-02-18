package lab5.commands;

import lab5.CollectionController;
import lab5.commands.base.CollectionCommand;

public class SaveCommand extends CollectionCommand {

    public SaveCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        controller.save();
    }

    @Override
    public String getDescription() {
        return "save : сохранить коллекцию в файл";
    }
}
