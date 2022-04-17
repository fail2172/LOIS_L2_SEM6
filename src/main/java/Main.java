public class Main {
    public static void main(String[] args) {
        try {
           Validator.getInstance().validate("!A");
        } catch (SyntaxException e) {
            System.out.println(e);
        }
    }
}
