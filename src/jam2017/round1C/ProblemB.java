package jam2017.round1C;

import java.util.ArrayList;
import java.util.List;

public class ProblemB extends Round1C {

    public ProblemB() {
        mAlpha = "B";
        mTitle = "Parenting Partnering";
    }

    @Override
    protected List<TestCase> createTestCase(int testCount, InputReader in,  StringBuffer [] results) {
        List<TestCase> tcs = new ArrayList<>();

        for (int i=1;i<=testCount;i++) {
            Partnering partnering = new Partnering(in, i, results[i]);
            tcs.add(partnering);
        }

        return tcs;
    }

    static class Partnering extends TestCase {

        int Ac, Aj;
        int [] C, D, J, K;

        protected Partnering(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            Ac = in.nextInt(); Aj = in.nextInt();

            C = new int[Ac]; D = new int[Ac];
            J = new int[Aj]; K = new int[Aj];

            for (int i=0;i<Ac;i++) {
                C[i] = in.nextInt();
                D[i] = in.nextInt();
            }

            for (int i=0;i<Aj;i++) {
                J[i] = in.nextInt();
                K[i] = in.nextInt();
            }
        }

        @Override
        protected String solve() {
            return null;
        }

    }
}
