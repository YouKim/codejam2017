package jam2017.qualificationRound;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jam2017.Problem;

public class ProblemB extends Qulification {

    public ProblemB() {
        mAlphabet = "B";
        mTitle = "Tidy Numbers";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new Number(in, testNumber, result);
    }

    static class Number extends TestCase {

        private char[] numbers;

        Number(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);
            numbers = in.next().toCharArray();
        }

        @Override
        protected String solve() {
            for (int i=1;i<numbers.length;i++) {
                if (numbers[i-1] > numbers[i]) {
                    numbers[i-1]--;

                    for (int j=i;j<numbers.length;j++) {
                        numbers[j] = '9';
                    }

                    i = 0;
                }
            }

            String result = String.valueOf(numbers);
            return String.format("Case #%d: %s\n", testNumber, result.replaceFirst("^0+(?!$)", ""));
        }
    }

    @Override
    protected String getSampleInput() {
        return "4\n" +
                "132\n" +
                "1000\n" +
                "7\n" +
                "111111111111111110";
    }

    @Override
    protected String getSampleOutput() {
        return "Case #1: 129\n" +
                "Case #2: 999\n" +
                "Case #3: 7\n" +
                "Case #4: 99999999999999999\n";
    }

    public static void main(String[] args) {
        Problem problem = new ProblemB();
        problem.solve();
    }

    @Test
    public void testSample() {
        Problem problem = new ProblemB();
        boolean result = problem.test();

        assertTrue(result);
    }
}
