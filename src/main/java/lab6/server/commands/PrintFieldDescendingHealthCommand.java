package lab6.server.commands;

import lab6.server.CollectionController;
import lab6.server.IOHelper;
import lab6.server.collection_items.SpaceMarine;
import lab6.server.commands.base.CollectionCommand;

import java.util.Comparator;

public class PrintFieldDescendingHealthCommand extends CollectionCommand {

    public PrintFieldDescendingHealthCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public String execute(String... args) {
        StringBuilder sb = new StringBuilder();
        controller.getCollectionElements().stream()
                .sorted(Comparator.comparing(SpaceMarine::getHealth))
                //.forEach(IOHelper.consoleOut::println);
                .forEach(sb::append);
        return sb.toString();
    }

    @Override
    public String getDescription() {
        return "print_field_descending_health : вывести значения поля health всех элементов в порядке убывания";
    }
}
