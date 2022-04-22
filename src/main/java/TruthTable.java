/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М.
// Класс предназначен для проверки формулы и для проверки знаний пользователя

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TruthTable {

    private static final String DECISION = "Decision";
    private static final String DOUBLE_INTRACT = "\t\t";
    private static final String INTRACT = "\t";

    private final List<Character> variableList = new ArrayList<>();
    private final int rows;
    private final int cols;
    private final boolean[][] table;

    public TruthTable(String formula) {
        receiveVariableList(formula);
        rows = (int) Math.pow(2, variableList.size());
        cols = variableList.size() + 1;
        table = new boolean[rows][cols];
        fillVariableCol();
    }

    private void fillVariableCol() {
        for (int i = 0, period = (int) Math.pow(2, variableList.size() - 1); i < cols - 1; i++, period /= 2) {
            boolean flag = true;
            for (int j = 0, currentPeriod = period; j < rows; j++, currentPeriod--) {
                if (currentPeriod == 0) {
                    flag = !flag;
                    currentPeriod = period;
                }
                table[j][i] = flag;
            }
        }
        for (int i = 0; i < variableList.size(); i++) {
            System.out.println(variableList.get(i));
            if (variableList.get(i).equals('1')) {
                for (int j = 0; j < rows; j++) {
                    table[j][i] = true;
                }
            } else if(variableList.get(i).equals('0')) {
                for (int j = 0; j < rows; j++) {
                    table[j][i] = false;
                }
            }
        }
    }

    private void receiveVariableList(String formula) {
        for (char ch : formula.toCharArray()) {
            if (Config.SYMBOLS.contains(String.valueOf(ch))) {
                variableList.add(ch);
            }
        }
        Set<Character> set = new HashSet<>(variableList);
        variableList.clear();
        variableList.addAll(set);
        variableList.sort(Character::compareTo);
    }

    public int height() {
        return rows;
    }

    public int width() {
        return cols;
    }

    public boolean getCell(int i, int j) {
        return table[i][j];
    }

    public boolean[] getRow(int i) {
        return table[i];
    }

    public void setCell(int i, int j, boolean value) {
        table[i][j] = value;
    }

    public void printTable() {
        for (char variable : variableList) {
            System.out.print(variable + DOUBLE_INTRACT);
        }
        System.out.println(DECISION);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(table[i][j] + INTRACT);
            }
            System.out.println();
        }
    }
}
