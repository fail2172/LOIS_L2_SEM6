public class Main {
    public static void main(String[] args) {
        try {
           Validator.getInstance().validate("(A/\\B)");
        } catch (SyntaxException e) {
            System.out.println(e);
        }

        Solver solver = new Solver();
        System.out.println(solver.solve("((A/\\B)~C)"));
    }
}
