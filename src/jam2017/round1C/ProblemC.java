package jam2017.round1C;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jam2017.Problem;

public class ProblemC extends Round1C {

    public ProblemC() {
        mAlpha = "C";
        mTitle = "Core Training";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return null;
    }

    @Override
    protected String getSampleInput() {
        return "4\n" +
                "4 4\n" +
                "1.4000\n" +
                "0.5000 0.7000 0.8000 0.6000\n" +
                "2 2\n" +
                "1.0000\n" +
                "0.0000 0.0000\n" +
                "2 1\n" +
                "0.0000\n" +
                "0.9000 0.8000\n" +
                "2 1\n" +
                "0.1000\n" +
                "0.4000 0.5000";
    }

    @Override
    protected String getSampleOutput() {
        return "Case #1: 1.000000\n" +
                "Case #2: 0.250000\n" +
                "Case #3: 0.980000\n" +
                "Case #4: 0.760000";
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
