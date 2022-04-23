/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Шило М.Ю
// Класс предназначен для проверки формулы и для проверки знаний пользователя

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            SolverThreading solverThreading = SolverThreading.getInstance();
            Scanner sc = new Scanner(new File("src/main/resources/input.txt"));
            TruthTable table = solverThreading.solve(sc.nextLine());
            table.printTable();
            System.out.println();
            boolean generallyValid = true;
            boolean impossible = true;
            for (int i = 0; i < table.height(); i++) {
                if (table.getCell(i, table.width() - 1)){
                    impossible = false;
                } else {
                    generallyValid = false;
                }
            }
            if (generallyValid){
                System.out.println("Formula is generally valid");
            } else if (impossible) {
                System.out.println("Formula is unsatisfiable");
            } else {
                System.out.println("Formula is neutral");
            }
        } catch (SyntaxException e) {
            System.out.println(e.getMessage() + e.getErrorPlace());
        } catch (InterruptedException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
