package jam2017.qualificationRound;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jam2017.Problem;

public class ProblemA extends Qulification {

    public ProblemA() {
        mAlpha = "A";
        mTitle = "Oversized Pancake Flipper";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new Pancake(in, testNumber, result);
    }

    static class Pancake extends TestCase {

        private char[] cakes;
        private int mSize;

        Pancake(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);
            cakes = in.next().toCharArray();
            mSize = in.nextInt();
        }

        boolean flip(int index) {
            final int end = index + mSize;

            if (cakes.length < end) {
                return false;
            }

            for (int i=index;i<end;i++) {
                cakes[i] = (cakes[i]=='+'?'-':'+');
            }
            return true;
        }

        @Override
        protected String solve() {
            int count = 0;
            for (int i=0;i<cakes.length;i++) {
                if (cakes[i] == '-') {
                    if (flip(i)) {
                        count++;
                    } else {
                        count = -1;
                    }
                }
            }
            String result = count<0?IMPOSSIBLE:String.valueOf(count);

            return String.format("Case #%d: %s\n", testNumber, result);
        }
    }

    @Override
    protected String getSampleInput() {
        return "3\n" +
               "---+-++- 3\n" +
               "+++++ 4\n" +
               "-+-+- 4\n";
    }

    @Override
    protected String getSampleOutput() {
        return "Case #1: 3\n" +
               "Case #2: 0\n" +
               "Case #3: IMPOSSIBLE\n";
    }

    public static void main(String[] args) {
        Problem problem = new ProblemA();
        problem.solve();
    }

    @Test
    public void testSample() {
        Problem problem = new ProblemA();
        boolean result = problem.test();

        assertTrue(result);
    }
}
