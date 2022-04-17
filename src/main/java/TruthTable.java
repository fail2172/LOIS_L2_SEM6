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
        variableList(formula);
        rows = (int) Math.pow(2, variableList.size());
        cols = variableList.size() + 1;
        table = new boolean[rows][cols];
        fillVariableCol();
        fillResultCol();
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
    }

    private void fillResultCol() {
        for (int i = 0; i < rows; i++) {
            table[i][cols - 1] = false;
        }
    }

    private void variableList(String formula) {
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

    public int weight() {
        return cols;
    }

    public void setCell(int i, int j, boolean value) {
        table[i][j] = value;
    }

    public boolean[] getRow(int i) {
        return table[i];
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
