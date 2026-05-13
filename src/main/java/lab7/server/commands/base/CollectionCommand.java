package lab7.server.commands.base;

import lab7.server.CollectionController;

public abstract class CollectionCommand implements Command {

    protected CollectionController controller;

    public CollectionCommand(CollectionController controller){
        this.controller = controller;
    }
}
