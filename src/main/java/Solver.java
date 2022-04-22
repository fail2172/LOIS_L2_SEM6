/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М.
// Класс предназначен для проверки формулы и для проверки знаний пользователя

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record Solver(int threadNum, int threadAmount, String formula, TruthTable table) implements Runnable {

    private static final String NEGATION = "!";
    private static final String OPEN_BRACKET_STR = "(";
    private static final String CLOSE_BRACKET_STR = ")";
    private static final String ONE_STR = "1";
    private static final char OPEN_BRACKET_CH = '(';
    private static final char CLOSE_BRACKET_CH = ')';
    private static final char ONE_CHAR = '1';
    private static final char ZERO_CHAR = '0';
    private static final String IMPLICATION = "->";
    private static final String EQUIVALENCE = "~";
    private static final String CONJUNCTION = "/\\";
    private static final String DISJUNCTION = "\\/";
    private static final String EMPTY = "";

    @Override
    public void run() {
        int answerPosition = table.width() - 1;
        int i = threadNum;
        while (i < table.height()) {
            final String expression = replaceVariable(formula, variableList(formula), table.getRow(i));
            boolean decision = solveExpression(expression);
            table.setCell(i, answerPosition, decision);
            i += threadAmount;
        }
    }

    private String replaceVariable(String formula, List<Character> variableList, boolean[] values) {
        for (int i = 0; i < variableList.size(); i++) {
            char oldChar = variableList.get(i);
            char newChar = values[i] ? ONE_CHAR : ZERO_CHAR;
            formula = formula.replace(oldChar, newChar);
        }
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
        variables.sort(Character::compareTo);
        return variables;
    }

    private boolean solveExpression(String formula) {
        formula = withoutBrackets(formula);
        if (formula.startsWith(NEGATION)) {
            return !solveExpression(formula.substring(1));
        }
        if (signPosition(formula) == -1) {
            return formula.contains(ONE_STR);
        }
        final String leftSide = leftSide(formula);
        final String rightSide = rightSide(formula);
        return switch (getSign(formula, signPosition(formula))) {
            case IMPLICATION -> !solveExpression(leftSide) || solveExpression(rightSide);
            case EQUIVALENCE -> solveExpression(leftSide) == solveExpression(rightSide);
            case CONJUNCTION -> solveExpression(leftSide) && solveExpression(rightSide);
            case DISJUNCTION -> solveExpression(leftSide) || solveExpression(rightSide);
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
        return EMPTY;
    }
}
