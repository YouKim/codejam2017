package jam2017.round1B;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jam2017.Problem;

public class ProblemA extends Round1B {

    public ProblemA() {
        mAlpha = "A";
        mTitle = "Steed 2: Cruise Control";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new CruiseControl(in, testNumber, result);
    }

    static class CruiseControl extends TestCase {

        int D;
        int N;
        double slowest = 0;

        public CruiseControl(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            D = in.nextInt();
            N = in.nextInt();

            for (int i=0;i<N;i++) {
                int P = in.nextInt();
                int speed = in.nextInt();
                double distance = D - P;
                double time = distance / speed;

                if (time > slowest) {
                    slowest = time;
                }
            }
        }

        @Override
        protected String solve() {
            double velocity = D / slowest;
            return String.format("Case #%d: %f\n", testNumber, velocity);
        }
    }

    @Override
    protected String getSampleInput() {
        return "3\n" +
                "2525 1\n" +
                "2400 5\n" +
                "300 2\n" +
                "120 60\n" +
                "60 90\n" +
                "100 2\n" +
                "80 100\n" +
                "70 10";
    }

    @Override
    protected String getSampleOutput() {
        return "Case #1: 101.000000\n" +
                "Case #2: 100.000000\n" +
                "Case #3: 33.333333";
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
