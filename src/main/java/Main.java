public class Main {

    public static void main(String[] args) {
        try {
            SolverThreading solverThreading = SolverThreading.getInstance();
            TruthTable table = solverThreading.solve("(((((((((((((((((((((((((A/\\B)/\\C)/\\D)/\\E)/\\F)/\\G)/\\H)/\\I)/\\J)/\\K)/\\L)/\\M)/\\N)/\\O)/\\P)/\\Q)/\\R)/\\S)/\\T)/\\U)/\\V)/\\W)/\\X)/\\Y)/\\Z)");
            //table.printTable();
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
                System.out.println("Formula is impossible");
            } else {
                System.out.println("Formula is neutral");
            }
        } catch (SyntaxException e) {
            System.out.println(e.getMessage() + e.getErrorPlace());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
