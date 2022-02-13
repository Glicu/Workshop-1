package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class TaskManager {

    static String[][] tasks;

    public static void main(String[] args) {

        String[] str = {"ADD", "REMOVE", "LIST", "EXIT"};

        tasks = LoadingFile();


        Scanner scanner = new Scanner(System.in);

        while (true) {
            Menu(str);
            String komenda = scanner.nextLine();

            if (equalsIgnoreCase(komenda, str[0])) {
                add();

            } else if (equalsIgnoreCase(komenda, str[1])) {
                remove();

            } else if (equalsIgnoreCase(komenda, str[2])) {
                list();

            } else if (equalsIgnoreCase(komenda, str[3])) {
                exit();
                break;
            } else {
                System.out.println("Podano niepoprawne dane, spróbuj ponownie");
            }
        }
    }

    public static void Menu(String[] str) {

        System.out.println(ConsoleColors.BLUE_BOLD + "\n .:: PLEASE SELECT AN OPTION ::. " + "\n" + "---------------------------------\n");

        for (int i = 0; i < str.length; i++) {
            System.out.println(ConsoleColors.RESET + "-> " + str[i]);
        }
        System.out.println(ConsoleColors.BLUE_BOLD + "\n" + "---------------------------------");
        System.out.print(ConsoleColors.RESET + "\n");

    }

    public static String[][] LoadingFile() {

        File file = new File("tasks.csv");
        int row = 0;

        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String wiersz = scan.nextLine();
                if (!wiersz.isEmpty()) {
                    row += 1;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku");
        }
        tasks = new String[row][];
        int x = 0;

        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String wiersz = scan.nextLine();

                if (!wiersz.isEmpty()) {
                    String[] temp = wiersz.split(",");
                    tasks[x] = temp;
                }
                x++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku");
        }

        return tasks;
    }

    public static void add() {

        System.out.println("Please add task description");
        Scanner task = new Scanner(System.in);
        String newTask = task.nextLine();
        StringBuilder newDataLine = new StringBuilder();
        newDataLine.append(newTask + ", ");
        System.out.println("Please add task due date");
        Scanner dueDate = new Scanner(System.in);
        String newDueDate = dueDate.nextLine();
        newDataLine.append(newDueDate + ", ");
        System.out.println("Is your task important: true/false");
        Scanner importance = new Scanner(System.in);
        String newImportance = importance.nextLine();
        newDataLine.append(newImportance);
        String newRow = newDataLine.toString();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = newRow.split(",");

    }

    public static void remove() {

        System.out.println("Please select number of row to be deleted");
        Scanner scan = new Scanner(System.in);
        try {
            while (true) {
                int rowToDelete = scan.nextInt();
                if (rowToDelete >= 0 && rowToDelete <= tasks.length) {
                    System.out.println("Row number " + rowToDelete + " has been deleted");
                    tasks = ArrayUtils.remove(tasks, rowToDelete);
                    break;
                } else {
                    System.out.println("Incorrect argument passed. Please give number greater or equal 0");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There's no such row in database");
        }
    }

    public static void list() {

        for (int i = 0; i < tasks.length; i++) {
            System.out.print("\n" + i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
        }
        System.out.println("\n");
    }
    public static void exit(){

        String fileName = "tasks.csv";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName, false);
            for(int i=0; i<tasks.length; i++) {
                fileWriter.write(Arrays.toString(tasks[i]) + "\n");
//                for(int j=0; j<tasks[i].length; j++) {
//                    fileWriter.write(tasks[i][j]);
//                }
            }
            fileWriter.close();
        }catch (IOException ex) {
            System.out.println("Problez z dostępem do pliku");
        }
        System.out.println(ConsoleColors.RED_BRIGHT + "Bye, bye");


    }
}