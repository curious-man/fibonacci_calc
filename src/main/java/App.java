
import java.math.BigInteger;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static java.math.BigInteger.ZERO;

public class App {

    private static Map<BigInteger, BigInteger> cache = new HashMap<>();

    static {
        cache.put(ZERO, ZERO);
        cache.put(ONE, ONE);
    }


    public static void main(String[] args) {
        System.out.println("Input positive number for calc F(n) and press Enter");
        BigInteger n = readNumberFromConsole();
        App app = new App();
        app.classicFibCalc(n);
        app.recursionCalcFib(n);
        app.calcWithMemoization(n);
    }

    static BigInteger readNumberFromConsole() {
        try {
            BigInteger n = new Scanner(System.in).nextBigInteger();
            if (n.compareTo(ZERO) < 0) {
                System.out.println(" Negative value is not correct, try again");
                return readNumberFromConsole();
            }
            return n;
        } catch (InputMismatchException e) {
            System.out.println("You was input incorrect data, try again");
            return readNumberFromConsole();
        }
    }

    // I decide to use BigInteger type. Long allows calc max fibonacci for n = 91
    private void classicFibCalc(BigInteger n) {
        Metric metric = new Metric("Classic algorithm");
        BigInteger prev = BigInteger.ZERO, next = ONE;
        BigInteger counter = BigInteger.ZERO;
        while (counter.compareTo(n) < 0) {
            BigInteger temp = next;
            next = prev.add(temp);
            prev = temp;
            counter = counter.add(ONE);
        }
        print(n, prev);
        metric.printResultTime();
    }

    // implemented tail recursion for avoiding StackOverFlowException
    void recursionCalcFib(BigInteger n) {
        Metric metric = new Metric("Recursion algorithm");
        print(n, recursFib(n, BigInteger.ZERO, ONE));
        metric.printResultTime();
    }

    // method with recursion more fast, but for big numbers you can get StackOverflowException
    // because java don't support tail recursion
    // also you can edit options your JVM for avoid it, but it no helps for big N
    // this implementation will be process in linear time
    BigInteger recursFib(BigInteger n, BigInteger prev, BigInteger next) {
        try {
            if (n.compareTo(BigInteger.ZERO) == 0)
                return prev;
            if (n.compareTo(ONE) == 0)
                return next;
            return recursFib(n.subtract(ONE), next, prev.add(next));
        } catch (StackOverflowError e) {
            System.out.printf("Recursion is not correct method for calc F(%d)" +
                    ". You Can increase Thread Stack Size (-Xss) in your JVM configs params", n);
            System.exit(1);
            return null;
        }
    }


    // I tried to improve algorithm with memoization it allows to calc every fibonacci number and store it in cache
    // but in benchmark this algorithm more slowly because memoization need memory and supporting cache demand more time
    void calcWithMemoization(BigInteger n) {
        // check if value already calculated
        Metric metric = new Metric("Recursion with memoization");
        print(n, fib(n));
        metric.printResultTime();
    }

    BigInteger fib(BigInteger n) {
        if (n.compareTo(ZERO) == 0 || n.compareTo(ONE) == 0) {
            return n;
        }
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        BigInteger result = fib(n.subtract(TWO)).add(fib(n.subtract(ONE)));
        cache.put(n, result);

        return result;
    }


    private void print(Number n, Number value) {
        System.out.printf("F(%s) = |%s| ", n, value);
    }
}