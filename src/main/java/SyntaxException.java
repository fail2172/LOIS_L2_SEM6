public class SyntaxException extends Exception {

    private final String errorPlace;

    public SyntaxException(String message) {
        super(message);
        this.errorPlace = "";
    }

    public SyntaxException(String message, String errorPlace) {
        super(message);
        this.errorPlace = errorPlace;
    }

    public String getErrorPlace() {
        return errorPlace;
    }
}
