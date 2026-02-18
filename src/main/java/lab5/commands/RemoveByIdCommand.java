package lab5.commands;

import lab5.CollectionController;
import lab5.commands.base.CollectionCommand;

public class RemoveByIdCommand extends CollectionCommand {

    public RemoveByIdCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        try {
            int id = Integer.parseInt(args[0]);
            controller.removeById(id);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex){
            throw new IllegalArgumentException("Для работы команды нужно предоставить корректный id элемента");
        }
    }

    @Override
    public String getDescription() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }
}
