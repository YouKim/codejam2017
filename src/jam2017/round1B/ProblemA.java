package jam2017.round1B;

import java.util.ArrayList;
import java.util.List;

public class ProblemA extends Round1B {

    public ProblemA() {
        mAlpha = "A";
        mTitle = "Steed 2: Cruise Control";
    }

    @Override
    protected List<TestCase> createTestCase(int testCount, InputReader in,  StringBuffer [] results) {

        List<TestCase> tcs = new ArrayList<>();

        for (int i=1;i<=testCount;i++) {
            CruiseControl cc = new CruiseControl(in, i, results[i]);
            tcs.add(cc);
        }

        return tcs;
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

}
