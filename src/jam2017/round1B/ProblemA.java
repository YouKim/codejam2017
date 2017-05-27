package jam2017.round1B;

import java.io.PrintWriter;

public class ProblemA extends Round1B {

    public ProblemA() {
        mAlpha = "A";
        mTitle = "Steed 2: Cruise Control";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {
        CruiseControl cc = new CruiseControl(in);
        double result = cc.solve(in);
        out.printf("Case #%d: %f\n", testNumber, result);
    }

    static class CruiseControl {

        int D;
        int N;

        public CruiseControl(InputReader in) {
            D = in.nextInt();
            N = in.nextInt();
        }

        double solve(InputReader in) {

            double slowest = 0;

            for (int i=0;i<N;i++) {
                int P = in.nextInt();
                int speed = in.nextInt();
                double distance = D - P;
                double time = distance / speed;

                if (time > slowest) {
                    slowest = time;
                }
            }

            double velocity = D / slowest;
            return velocity;
        }
    }

}
