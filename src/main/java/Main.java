public class Main {
    public static void main(String[] args) {
        try {
            Solver solver = Solver.getInstance();
            TruthTable table = solver.solve("(((!A)/\\B)~(A\\/(!B)))");
            table.printTable();
            System.out.println();
            boolean flag = true;
            for (int i = 0; i < table.height(); i++) {
                if (table.getCell(i, table.weight() - 1)){
                    flag = false;
                    break;
                }
            }
            if (flag) {
                System.out.println("The function is not executable");
            } else {
                System.out.println("The function is not invalid");
            }
        } catch (SyntaxException e) {
            System.out.println(e.getMessage());
        }
    }
}
