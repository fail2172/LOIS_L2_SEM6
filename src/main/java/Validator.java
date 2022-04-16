public class Validator {

    private Validator() {
    }

    public static Validator getInstance() {
        return Holder.INSTANCE;
    }

    public void validate1(String formula) throws SyntaxException {
        try {
            validate(formula);
        } catch (Exception e) {
            throw new SyntaxException("Invalid sign syntax");
        }
    }

    public void validate(String formula) throws SyntaxException {
        if (formula.length() > 2) {
            final String leftSide = leftSide(withoutBrackets(formula));
            final String rightSide = rightSide(withoutBrackets(formula));
            validate(withoutBrackets(leftSide));
            validate(withoutBrackets(rightSide));
        } else if (formula.startsWith("!")) {
            validate(formula.substring(1));
        } else if (!checkSymbol(formula)) {
            throw new SyntaxException("Invalid expression syntax");
        }
    }

    public String leftSide(String formula) throws SyntaxException {
        final int singPosition = signPosition(formula);
        final String leftSide = formula.substring(0, singPosition);
        if (leftSide.startsWith("!")) {
            throw new SyntaxException("Invalid sign syntax");
        }
        return leftSide;
    }

    public String rightSide(String formula) throws SyntaxException {
        final int singPosition = signPosition(formula);
        final String sign = getSign(formula, singPosition);
        final String rightSide = sign.length() == 2
                ? formula.substring(singPosition + 2)
                : formula.substring(singPosition + 1);
        if (rightSide.startsWith("!")) {
            throw new SyntaxException("Invalid sign syntax");
        }
        return rightSide;
    }

    public String withoutBrackets(String formula) throws SyntaxException {
        while (signPosition(formula) == -1) {
            if (formula.startsWith("(") && formula.endsWith(")")) {
                formula = formula.substring(1, formula.length() - 1);
            } else {
                return formula;
            }
        }
        return formula;
    }

    private int signPosition(String formula) throws SyntaxException {
        int bracket = 0;
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == '(') {
                bracket++;
            } else if (formula.charAt(i) == ')') {
                bracket--;
            } else if (bracket == 0 && i != 0 && i != formula.length() - 1) {
                if (checkSign(formula, i)) {
                    return i;
                }
                throw new SyntaxException("Invalid sign syntax");
            }
        }
        return -1;
    }

    private String getSign(String formula, int signPosition) throws SyntaxException {
        final String sub = formula.substring(signPosition);
        for (String sign : Config.BINARY_SIGN) {
            if (sub.startsWith(sign)) {
                return sign;
            }
        }
        throw new SyntaxException("Invalid sign syntax");
    }

    public boolean checkSign(String formula, int signPosition) {
        final String sub = formula.substring(signPosition);
        return Config.BINARY_SIGN.stream()
                .anyMatch(sub::startsWith);
    }

    private boolean checkSymbol(String formula) {
        return Config.SYMBOLS.stream()
                .anyMatch(formula::equals);
    }

    private static class Holder {
        private static final Validator INSTANCE = new Validator();
    }
}
