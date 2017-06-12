package jam2017.round1B;

import java.util.ArrayList;
import java.util.List;

public class ProblemC extends Round1B {

    public ProblemC() {
        mAlpha = "C";
        mTitle = "Pony Express";
    }

    @Override
    protected List<TestCase> createTestCase(int testCount, InputReader in,  StringBuffer [] results) {
        List<TestCase> tcs = new ArrayList<>();

        for (int i=1;i<=testCount;i++) {
            PonyExpress test = new PonyExpress(in, i, results[i]);
            tcs.add(test);
        }

        return tcs;
    }

    static class PonyExpress extends TestCase {

        private final int N, Q;

        private final int [] E, S, U, V;

        private final long [][] dist;
        private final double [][] time;

        protected PonyExpress(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            N = in.nextInt();
            Q = in.nextInt();

            E = new int[N+1];
            S = new int[N+1];

            U = new int[Q];
            V = new int[Q];

            dist = new long[N+1][N+1];
            time = new double[N+1][N+1];

            for (int i=1;i<=N;i++) {
                E[i] = in.nextInt();
                S[i] = in.nextInt();
            }

            for (int i=1;i<=N;i++) {
                for (int j=1;j<=N;j++) {
                    dist[i][j] = in.nextInt();
                }
            }

            for (int i=0;i<Q;i++) {
                U[i] = in.nextInt();
                V[i] = in.nextInt();
            }
        }

        @Override
        protected String solve() {

            for (int k=1;k<=N;k++) {
                for (int i=1;i<=N;i++) {
                    for (int j=1;j<=N;j++) {
                        if (i != j && i != k && j != k) {
                            if (dist[i][k] > 0 && dist[k][j] > 0) {
                                if (dist[i][j] < 0 || dist[i][j] > dist[i][k] + dist[k][j]) {
                                    dist[i][j] = dist[i][k] + dist[k][j];
                                }
                            }
                        }
                    }
                }
            }

            for (int i=1;i<=N;i++) {
                for (int j=1;j<=N;j++) {
                    if (dist[i][j] > 0 && dist[i][j] <= E[i]) {
                        time[i][j] = (double) dist[i][j] /  (double) S[i];
                    } else {
                        time[i][j] = Double.MAX_VALUE;
                    }
                }
            }

            for (int k=1;k<=N;k++) {
                for (int i=1;i<=N;i++) {
                    for (int j=1;j<=N;j++) {
                        if (i != j && i != k && j != k) {
                            if (time[i][j] > time[i][k] + time[k][j]) {
                                time[i][j] = time[i][k] + time[k][j];
                            }
                        }
                    }
                }
            }

            StringBuffer result = new StringBuffer();

            for (int i=0;i<Q;i++) {
                result.append(time[U[i]][V[i]]).append(' ');
             }

            return String.format("Case #%d: %s\n", testNumber, result.toString().trim());
        }
    }
}
