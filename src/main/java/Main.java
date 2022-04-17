public class Main {
    public static void main(String[] args) {
        TruthTable table = new TruthTable(3);

        for(int i = 0; i < table.height(); i++) {
            for (int j = 0; j < table.weight(); j++) {
                System.out.print(table.cell(i, j));
                System.out.print("\t");
            }
            System.out.println("\n");
        }
    }
}
