package jam2017.round1C;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import jam2017.Problem;
import jam2017.Problem.InputReader;
import jam2017.Problem.TestCase;

public class ProblemC extends Round1C {

    public ProblemC() {
        mAlphabet = "C";
        mTitle = "Core Training";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new CoreTraining(testNumber, in, result);
    }

    static class CoreTraining extends TestCase {

        int N, K;
        double U;
        Cores P;

        protected CoreTraining(int testNumber, InputReader in, StringBuffer result) {
            super(testNumber, result);

            N = in.nextInt();
            K = in.nextInt();

            U = in.nextDouble();

            P = new Cores();
            for (int i=0;i<N;i++) {
                P.add(in.nextDouble());
            }

            P.prepare(N-K);
        }

        @Override
        protected String solve() {

            double result = 0;

            result = P.pour(U);

            System.out.println("=========== " + result);
            return String.format("Case #%d: %f\n", testNumber, result);
        }
    }

    static class Cores extends ArrayList<Double> {

        double [] mDeltas;
        double [] mAreas;

        void prepare(int start) {

            Collections.sort(this);

            int size = size();

            mAreas = new double[size];
            mDeltas = new double[size];

            mAreas[0] = get(0);

            for (int i=1;i<size;i++) {
                mAreas[i] = mAreas[i-1] + get(i);
            }

            for (int i=(start);i<size-1;i++) {
                mDeltas[i] = (i<1?0:mDeltas[i-1]) + (get(i+1) - get(i)) * (i+1);
            }

            mDeltas[size-1] = size - mAreas[size-1];

            System.out.println("===========");
            for (int i=0;i<size;i++) {
                System.out.println(String.format("%d: %f", i, mAreas[i]));
            }
            System.out.println("===========");

            for (int i=0;i<size;i++) {
                System.out.println(String.format("%d: %f", i, mDeltas[i]));
            }
            System.out.println("===========");
        }

        double pour(double units) {

            int size = size();
            int i=0;

            for (;i<size;i++) {

                System.out.println("=========== mDeltas[i]:" + mDeltas[i] +  ", " + units);

                if (mDeltas[i] >= units) {
                    break;
                }
            }

            System.out.println("=========== i:" + i);
            double result;

            result = (units + mAreas[i]) / (i+1);
            System.out.println("=========== result:" + result);

            for (int j=0;j<=i;j++) {
                set(j, result);
            }

            double poss = 1;

            for (int k=0;k<size;k++) {
                poss *= get(k);
            }

            return poss;
        }
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
                "2 2\n" +
                "0.0000\n" +
                "0.9000 0.8000\n" +
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
                "Case #3: 0.720000\n" +
                "Case #4: 0.980000\n" +
                "Case #5: 0.760000";
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
