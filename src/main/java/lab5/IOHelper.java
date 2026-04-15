package lab5;

import lab5.collection_items.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class IOHelper {
    public static final PrintStream consoleOut = System.out;
    public static final PrintStream errOut = System.err;
    public static final InputStreamReader consoleIn = new InputStreamReader(System.in);
    public static InputStreamReader defaultIn = consoleIn;

    /**
     * reads line from stdin
     * @return line from stdin
     */
    public static String readConsoleLine(){
        return getLine(consoleIn); //sc.nextLine();
    }

    /**
     * reads line from file
     * @param fileReader file to readFrom
     * @return line from file
     */
    public static String readFileLine(InputStreamReader fileReader)  {
        return getLine(fileReader);
    }

    /**
     * Reads line
     * @param reader InputStreamReader to read from
     * @return String until \n on EOF
     */
    private static String getLine(InputStreamReader reader){
        try {
            StringBuilder sb = new StringBuilder();
            while (true) {
                int c = reader.read();
                if (c == -1 || c == '\n')
                    break;
                sb.append((char)c);
            }
            return sb.toString().strip();
        } catch (IOException ex){
            consoleOut.println("Unknown error. Program terminated");
            System.exit(-1);
        }
        return null;
    }

    public static void printlnIfUsingConsole(String s){
        if (defaultIn == consoleIn) consoleOut.println(s);
    }

    /**
     * reads SpaceMarine from defaultIn stream
     * @return SpaceMarine object
     */
    public static SpaceMarine readMarine(){
        return readMarine(defaultIn);
    }

    /**
     * reads SpaceMarine from stream
     * @param reader stream to readFrom
     * @return SpaceMarine object
     */
    public static SpaceMarine readMarine(InputStreamReader reader) {
        boolean needGreet = reader == consoleIn;
        String buff;

        String name; //Поле не может быть null, Строка не может быть пустой
        Coordinates coordinates; //Поле не может быть null
        Double health; //Поле не может быть null, Значение поля должно быть больше 0
        AstartesCategory category; //Поле может быть null
        Weapon weaponType; //Поле не может быть null
        MeleeWeapon meleeWeapon; //Поле может быть null
        Chapter chapter; //Поле может быть null

        while (true) {
            if (needGreet) consoleOut.print(">>> Введите имя: ");
            buff = getLine(reader);
            if (needGreet && (buff == null || buff.isEmpty()))
                continue;
            name = buff;
            break;
        }

        coordinates = readCoordinates(reader, needGreet);

        while (true) {
            try {
                if (needGreet) consoleOut.print(">>> Введите здоровье(> 0): ");
                buff = getLine(reader);
                Double tmp = Double.parseDouble(buff);
                if (needGreet && tmp <= 0)
                    continue;
                health = tmp;
                break;
            } catch (NumberFormatException ex){
                if (!needGreet) throw new FileReadingException("Ошибка чтения файла");
            }
        }

        while (true) {
            try {
                if (needGreet) consoleOut.print(">>> Введите категорию(SCOUT, AGGRESSOR, SUPPRESSOR, TACTICAL, TERMINATOR): ");
                buff = getLine(reader).toUpperCase();
                category = AstartesCategory.valueOf(buff);
                break;
            } catch (IllegalArgumentException ex){
                if (!needGreet) throw new FileReadingException("Ошибка чтения файла");
            }
        }
        while (true) {
            try {
                if (needGreet) consoleOut.print(">>> Введите тип оружия(MELTAGUN, GRAV_GUN, GRENADE_LAUNCHER): ");
                buff = getLine(reader).toUpperCase();
                weaponType = Weapon.valueOf(buff);
                break;
            } catch (IllegalArgumentException ex){
                if (!needGreet) throw new FileReadingException("Ошибка чтения файла");
            }
        }
        while (true) {
            try {
                if (needGreet) consoleOut.print(">>> Введите оружие ближнего боя(CHAIN_SWORD, POWER_SWORD, CHAIN_AXE, MANREAPER, POWER_BLADE): ");
                buff = getLine(reader).toUpperCase();
                meleeWeapon = MeleeWeapon.valueOf(buff);
                break;
            } catch (IllegalArgumentException ex){
                if (!needGreet) throw new FileReadingException("Ошибка чтения файла");
            }
        }

        chapter = readChapter(reader, needGreet);

        return new SpaceMarine(name, coordinates, health, category, weaponType, meleeWeapon, chapter);
    }

    /**
     * reads Coordinates from stream
     * @param reader stream to readFrom
     * @param needGreet indicates if we are reading from console
     * @return Coordinates object
     */
    public static Coordinates readCoordinates(InputStreamReader reader, boolean needGreet){
        String buff;
        Long x;
        long y;
        while (true){
            try {
                if (needGreet) consoleOut.print(">>> Введите x(> -201): ");
                buff = getLine(reader);
                Long tmp = Long.parseLong(buff);
                if (tmp <= -201)
                    continue;

                x = tmp;
                break;
            } catch (NumberFormatException ex){
                if (!needGreet) throw new FileReadingException("Ошибка чтения файла");
            }
        }

        while (true){
            try {
                if (needGreet) consoleOut.print(">>> Введите y: ");
                buff = getLine(reader);
                y = Long.parseLong(buff);
                break;
            } catch (NumberFormatException ex){
                if (!needGreet) throw new FileReadingException("Ошибка чтения файла");
            }
        }
        return new Coordinates(x, y);
    }

    /**
     * reads Chapter from stream
     * @param reader stream to readFrom
     * @param needGreet indicates if we are reading from console
     * @return Chapter object
     */
    public static Chapter readChapter(InputStreamReader reader, boolean needGreet){
        String name, parentLegion;
        Integer marinesCount;

        String buff;

        while (true) {
            if (needGreet) consoleOut.print(">>> Введите имя главы: ");
            buff = getLine(reader);
            if (needGreet && (buff == null || buff.isEmpty()))
                continue;
            name = buff;
            break;
        }

        if (needGreet) consoleOut.print(">>> Введите легион: ");
        parentLegion = getLine(reader);

        while (true){
            try {
                if (needGreet) consoleOut.print(">>> Введите количество морпехов: ");
                buff = getLine(reader);
                Integer tmp = Integer.parseInt(buff);
                if (needGreet && (tmp < 0 || tmp > 1000))
                    continue;

                marinesCount = tmp;
                break;
            } catch (NumberFormatException ex){
                if (!needGreet) throw new FileReadingException("Ошибка чтения файла");
            }
        }

        return new Chapter(name, parentLegion, marinesCount);
    }

}
