package lab6.server.commands.base;

import lab6.server.CollectionController;

public abstract class CollectionCommand implements Command {

    protected CollectionController controller;

    public CollectionCommand(CollectionController controller){
        this.controller = controller;
    }
}
