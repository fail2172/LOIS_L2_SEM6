/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М.
// Класс предназначен для проверки формулы и для проверки знаний пользователя

import java.util.*;

public class SolverThreading {

    private static final Validator validator = Validator.getInstance();
    private static final String VALIDATION_COMPLETE_MESSAGE = "validation complete: %s s%n";
    private static final String TABLE_CREATION_COMPLETE_MESSAGE = "table creation complete: %s s%n";
    private static final int NUM_TEN = 10;
    private static final int POWER = -9;
    private static final int THREAD_AMOUNT = 8;

    private SolverThreading() {

    }

    public static SolverThreading getInstance() {
        return Holder.INSTANCE;
    }

    public TruthTable solve(String formula) throws SyntaxException, InterruptedException {
        long startTime = System.nanoTime();
        validate(formula, startTime);
        final TruthTable table = createTable(formula, startTime);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_AMOUNT; i++) {
            Thread thread = new Thread(new Solver(i, THREAD_AMOUNT, formula, table));
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        long solutionTime = System.nanoTime();
        System.out.printf("solution complete: %s s%n", nanoToSeconds(solutionTime - startTime));
        return table;
    }

    private void validate(String formula, long startTime) throws SyntaxException {
        validator.validate(formula);
        long validateTime = System.nanoTime();
        System.out.printf(VALIDATION_COMPLETE_MESSAGE, nanoToSeconds(validateTime - startTime));
    }

    private TruthTable createTable(String formula, long startTime) {
        final TruthTable table = new TruthTable(formula);
        long tableCreateTime = System.nanoTime();
        System.out.printf(TABLE_CREATION_COMPLETE_MESSAGE, nanoToSeconds(tableCreateTime - startTime));
        return table;
    }

    private double nanoToSeconds(long nanoSeconds) {
        return nanoSeconds * Math.pow(NUM_TEN, POWER);
    }

    private static class Holder {
        private static final SolverThreading INSTANCE = new SolverThreading();
    }
}
