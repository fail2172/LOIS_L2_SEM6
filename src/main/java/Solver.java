import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {

    private static final String NEGATION = "!";
    private static final String ERROR_SEPARATOR = " _ERROR_ ";
    private static final String OPEN_BRACKET_STR = "(";
    private static final String CLOSE_BRACKET_STR = ")";
    private static final char OPEN_BRACKET_CH = '(';
    private static final char CLOSE_BRACKET_CH = ')';

    public boolean solve(String formula) {
        final List<Character> variableList = variableList(formula);
        final TruthTable table = new TruthTable(variableList.size());
        boolean[] mass = {true, true, false};
        return solveExpression(replaceVariable(formula, variableList, mass));
    }

    private String replaceVariable(String formula, List<Character> variableList, boolean[] values) {
        for (int i = 0; i < variableList.size(); i++) {
            char oldChar = variableList.get(i);
            char newChar = values[i] ? '1' : '0';
            formula = formula.replace(oldChar, newChar);
        }
        System.out.println(formula);
        return formula;
    }

    private List<Character> variableList(String formula) {
        final List<Character> variables = new ArrayList<>();
        for (char ch : formula.toCharArray()) {
            if (Config.SYMBOLS.contains(String.valueOf(ch))) {
                variables.add(ch);
            }
        }
        Set<Character> set = new HashSet<>(variables);
        variables.clear();
        variables.addAll(set);
        return variables;
    }

    public boolean solveExpression(String formula) {
        formula = withoutBrackets(formula);
        if (signPosition(formula) == -1) {
            return formula.startsWith(NEGATION) != formula.contains("1");
        }
        final String leftSide = leftSide(formula);
        final String rightSide = rightSide(formula);
        return switch (getSign(formula, signPosition(formula))) {
            case "->" -> !solveExpression(leftSide) || solveExpression(rightSide);
            case "~" -> solveExpression(leftSide) == solveExpression(rightSide);
            case "/\\" -> solveExpression(leftSide) && solveExpression(rightSide);
            case "\\/" -> solveExpression(leftSide) || solveExpression(rightSide);
            default -> false;
        };
    }

    private String leftSide(String formula) {
        return formula.substring(0, signPosition(formula));
    }

    private String rightSide(String formula) {
        final String sign = getSign(formula, signPosition(formula));
        return sign.length() == 2
                ? formula.substring(signPosition(formula) + 2)
                : formula.substring(signPosition(formula) + 1);
    }

    private String withoutBrackets(String formula) {
        while (signPosition(formula) == -1) {
            if (formula.startsWith(OPEN_BRACKET_STR) && formula.endsWith(CLOSE_BRACKET_STR)) {
                formula = formula.substring(1, formula.length() - 1);
            } else {
                return formula;
            }
        }
        return formula;
    }

    private int signPosition(String formula) {
        int bracket = 0;
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == OPEN_BRACKET_CH) {
                bracket++;
            } else if (formula.charAt(i) == CLOSE_BRACKET_CH) {
                bracket--;
            } else if (bracket == 0 && i != 0 && i != formula.length() - 1) {
                return i;
            }
        }
        return -1;
    }

    private String getSign(String formula, int signPosition) {
        final String sub = formula.substring(signPosition);
        for (String sign : Config.BINARY_SIGN) {
            if (sub.startsWith(sign)) {
                return sign;
            }
        }
        return "";
    }
}
