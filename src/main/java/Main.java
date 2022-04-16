public class Main {
    public static void main(String[] args) {
        try {
           Validator.getInstance().validate1("(((!A)/\\B)/\\(C\\/(!A)))");
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }
}
