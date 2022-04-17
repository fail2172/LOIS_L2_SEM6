public class Validator {

    private static final String NEGATION = "!";
    private static final String ERROR_SEPARATOR = " _ERROR_ ";
    private static final String OPEN_BRACKET_STR = "(";
    private static final String CLOSE_BRACKET_STR = ")";
    private static final char OPEN_BRACKET_CH = '(';
    private static final char CLOSE_BRACKET_CH = ')';

    private Validator() {
    }

    public static Validator getInstance() {
        return Holder.INSTANCE;
    }

    public void validate(String formula) throws SyntaxException {
        try {
            if (formula.startsWith(NEGATION)) {
                throw new SyntaxException("Invalid negation syntax: ", formula);
            }
            dfs(withoutBrackets(formula));
        } catch (SyntaxException e) {
            throw new SyntaxException(errorMessageConstructor(formula, e));
        } catch (StringIndexOutOfBoundsException e) {
            throw new SyntaxException("Invalid syntax");
        }
    }

    private String errorMessageConstructor(String formula, SyntaxException e) {
        int errorPosition = formula.indexOf(e.getErrorPlace());
        return e.getMessage()
                + formula.substring(0, errorPosition)
                + ERROR_SEPARATOR
                + e.getErrorPlace()
                + ERROR_SEPARATOR
                + formula.substring(errorPosition + e.getErrorPlace().length());
    }

    private void dfs(String formula) throws SyntaxException {
        if (signPosition(formula) != -1) {
            final String leftSide = leftSide(formula);
            final String rightSide = rightSide(formula);
            dfs(withoutBrackets(leftSide));
            dfs(withoutBrackets(rightSide));
        } else if (formula.startsWith(NEGATION)) {
            dfs(formula.substring(1));
        } else if (!checkSymbol(formula)) {
            throw new SyntaxException("There is no such variable in the alphabet: ", formula);
        }
    }

    private String leftSide(String formula) throws SyntaxException {
        final int singPosition = signPosition(formula);
        final String leftSide = formula.substring(0, singPosition);
        if (leftSide.startsWith(NEGATION)) {
            throw new SyntaxException("Invalid negation syntax: ", formula);
        }
        return leftSide;
    }

    private String rightSide(String formula) throws SyntaxException {
        final String sign = getSign(formula, signPosition(formula));
        final String rightSide = sign.length() == 2
                ? formula.substring(signPosition(formula) + 2)
                : formula.substring(signPosition(formula) + 1);
        if (rightSide.startsWith(NEGATION)) {
            throw new SyntaxException("Invalid negation syntax: ", formula);
        }
        return rightSide;
    }

    private String withoutBrackets(String formula) throws SyntaxException {
        while (signPosition(formula) == -1) {
            if (formula.startsWith(OPEN_BRACKET_STR) && formula.endsWith(CLOSE_BRACKET_STR)) {
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
            if (formula.charAt(i) == OPEN_BRACKET_CH) {
                bracket++;
            } else if (formula.charAt(i) == CLOSE_BRACKET_CH) {
                bracket--;
            } else if (bracket == 0 && i != 0 && i != formula.length() - 1) {
                if (checkSign(formula, i)) {
                    return i;
                }
                throw new SyntaxException("There is no such sign in the alphabet: ", formula);
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
        throw new SyntaxException("There is no such sign in the alphabet: ", formula);
    }

    private boolean checkSign(String formula, int signPosition) {
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
