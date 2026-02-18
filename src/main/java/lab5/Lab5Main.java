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
        try {
            FileWriter fw = new FileWriter("tmp.txt");
            fw.write("123");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        while (true){
            try {
                manager.executeCommand(IOHelper.readConsoleLine());
            } catch (IllegalArgumentException ex){
                IOHelper.consoleOut.println(ex.getMessage());
            }
        }
    }
}
