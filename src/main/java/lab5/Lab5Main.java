package lab5;

import lab5.commands.base.CommandManager;
import main.Solution;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Lab5Main implements Solution {

    private CommandManager manager;

    /**
     * static void main(String... args) for this package
     */
    @Override
    public void solve() {
        CollectionController cc = new CollectionController();
        manager = new CommandManager(cc);
        manager.executeCommand("help");

        while (true) {
            try {
                IOHelper.consoleOut.print(">>");
                manager.executeCommand(IOHelper.readConsoleLine());
            } catch (IllegalArgumentException ex){
                IOHelper.consoleOut.println(ex.getMessage());
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
    }
}
