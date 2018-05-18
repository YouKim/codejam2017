package jam2017.qualificationRound;

import java.util.Map.Entry;

import org.junit.Test;

import jam2017.Problem;

import static org.junit.Assert.assertTrue;

import java.util.TreeMap;

public class ProblemC extends Qulification {

    public ProblemC() {
        mAlpha = "C";
        mTitle = "Bathroom Stalls";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new Stall(in, testNumber, result);
    }

    static class Stall extends TestCase {

        private long mPeople;
        private TreeMap<Long, Long> tiedStalls;

        Stall(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);
            tiedStalls = new TreeMap<>();
            add(in.nextLong(), 1);
            mPeople = in.nextLong();
        }

        void add(long size, long number) {
            if (size < 1) {
                return;
            }

            Long old = tiedStalls.get(size);

            if (old != null) {
                number = number + old;
            }

            tiedStalls.put(size, number);
            if (DEBUG) System.out.println("Stall add:" + size + " : " + number);
        }

        @Override
        protected String solve() {
            long located = 0;
            long min = 0;
            long max = 0;
            while (located < mPeople) {
                Entry<Long, Long> sections = tiedStalls.pollLastEntry();

                long size = sections.getKey();
                long number = sections.getValue();

                if (size > 1) {
                    if (size % 2 == 1) {
                        long divided = (size - 1) / 2;
                        add(divided, 2 * number);
                        min = max = divided;
                    } else {
                        long divided = (size) / 2;
                        add(divided, number);
                        max = divided;
                        add(divided-1, number);
                        min = divided-1;
                    }
                } else if (size == 1) {
                    min = max = 0;
                } else {
                    System.out.println("Size is 0!!!");
                    return null;
                }
                located += number;
            }

            return String.format("Case #%d: %d %d\n", testNumber, max, min);
        }

    }

    @Override
    protected String getSampleInput() {
        return "5\n" +
                "4 2\n" +
                "5 2\n" +
                "6 2\n" +
                "1000 1000\n" +
                "1000 1";
    }

    @Override
    protected String getSampleOutput() {
        return "Case #1: 1 0\n" +
                "Case #2: 1 0\n" +
                "Case #3: 1 1\n" +
                "Case #4: 0 0\n" +
                "Case #5: 500 499";
    }

    public static void main(String[] args) {
        Problem problem = new ProblemC();
        problem.solve();
    }

    @Test
    public void testSample() {
        Problem problem = new ProblemC();
        boolean result = problem.test();

        assertTrue(result);
    }
}
