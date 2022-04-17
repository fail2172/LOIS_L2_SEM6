public class Main {
    public static void main(String[] args) {
        Solver solver = new Solver();
        try {
            TruthTable table = solver.solve("((A/\\(!B))~((!A)\\/B))");
            table.printTable();
        } catch (SyntaxException e) {
            System.out.println(e);
        }
    }
}
