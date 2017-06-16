package jam2017.round1B;

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

}
