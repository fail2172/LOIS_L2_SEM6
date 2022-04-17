public class TruthTable {

    private final int variableNum;
    private final int rows;
    private final int cols;
    private final boolean[][] table;

    public TruthTable(int variableNum) {
        this.variableNum = variableNum;
        rows = (int) Math.pow(2, variableNum);
        cols = variableNum + 1;
        table = new boolean[rows][cols];
        fillVariableCol();
        fillResultCol();
    }

    private void fillVariableCol() {
        for (int i = 0, period = (int) Math.pow(2, variableNum - 1); i < cols - 1; i++, period /= 2) {
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

    public int height() {
        return rows;
    }
    public int weight() {
        return cols;
    }

    public boolean cell(int i, int j) {
        return table[i][j];
    }

    public boolean[] getRow(int i) {
        return table[i];
    }
}
