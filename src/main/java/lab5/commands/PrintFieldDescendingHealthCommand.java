package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.collection_items.SpaceMarine;
import lab5.commands.base.CollectionCommand;

import java.util.Comparator;

public class PrintFieldDescendingHealthCommand extends CollectionCommand {

    public PrintFieldDescendingHealthCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        controller.getCollectionElements().stream()
                .sorted(Comparator.comparing(SpaceMarine::getHealth))
                .forEach(IOHelper.consoleOut::println);
    }

    @Override
    public String getDescription() {
        return "print_field_descending_health : вывести значения поля health всех элементов в порядке убывания";
    }
}
