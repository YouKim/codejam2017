package jam2017.round1B;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jam2017.Problem;

public class ProblemC extends Round1B {

    public ProblemC() {
        mAlphabet = "C";
        mTitle = "Pony Express";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new PonyExpress(in, testNumber, result);
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

    @Override
    protected String getSampleInput() {
        return "3\n" +
                "3 1\n" +
                "2 3\n" +
                "2 4\n" +
                "4 4\n" +
                "-1 1 -1\n" +
                "-1 -1 1\n" +
                "-1 -1 -1\n" +
                "1 3\n" +
                "4 1\n" +
                "13 10\n" +
                "1 1000\n" +
                "10 8\n" +
                "5 5\n" +
                "-1 1 -1 -1\n" +
                "-1 -1 1 -1\n" +
                "-1 -1 -1 10\n" +
                "-1 -1 -1 -1\n" +
                "1 4\n" +
                "4 3\n" +
                "30 60\n" +
                "10 1000\n" +
                "12 5\n" +
                "20 1\n" +
                "-1 10 -1 31\n" +
                "10 -1 10 -1\n" +
                "-1 -1 -1 10\n" +
                "15 6 -1 -1\n" +
                "2 4\n" +
                "3 1\n" +
                "3 2";
    }

    @Override
    protected String getSampleOutput() {
        return "Case #1: 0.583333333\n" +
                "Case #2: 1.2\n" +
                "Case #3: 0.51 8.01 8.0";
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
