package jam2017.round1B;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.Test;

import jam2017.Problem;

public class ProblemB extends Round1B {

    public ProblemB() {
        mAlpha = "B";
        mTitle = "Stable Neigh-bors";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new Neighbors(in, testNumber, result);
    }

    static class Neighbors extends TestCase {
        static final char R='R', O='O', Y='Y', G='G', B='B', V='V';

        static final char [] COLOR = {R, O, Y, G, B, V};
        static final int COLORS = 6; //COLOR.length;

        class Unicorns {
            char color;
            int heads;

            Unicorns(char color, int heads) {
                this.color = color;
                this.heads = heads;
            }
        }

        int N;
        int [] H;

        Neighbors(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            N = in.nextInt();
            H = new int[COLORS];

            for (int i=0;i<COLORS;i++) {
                H[i] = in.nextInt();
            }
        }

        @Override
        protected String solve() {
            return String.format("Case #%d: %s\n", testNumber, solveInner());
        }

        private String solveInner() {
            boolean mixed = (H[1]+H[3]+H[5]>0);

            if (mixed) {
                for (int i=1;i<COLORS;i+=2) {
                    if (H[i] > 0) {
                        if (H[i] > H[(i+3)%COLORS]) {
                            return IMPOSSIBLE;
                        } else if (H[i] == H[(i+3)%COLORS]) {
                            if (H[(i+1)%COLORS] + H[(i+2)%COLORS]
                                    + H[(i+4)%COLORS] + H[(i+5)%COLORS] > 0) {
                                return IMPOSSIBLE;
                            }
                        }
                    }
                }

                for (int i=0;i<COLORS;i+=2) {
                    H[i] = H[i] - H[(i+3)%COLORS];
                }
            }

            // Check if it is possible. R>Y+B, Y>R+B B>R+Y
            for (int i=0;i<COLORS;i+=2) {
                if (H[i] > H[(i+2)%COLORS] + H[(i+4)%COLORS]) {
                    return IMPOSSIBLE;
                }
            }

            ArrayList<Unicorns> unicorns = new ArrayList<>();
            for (int i=0;i<COLORS;i++) {
                if (H[i]>0) {
                    unicorns.add(new Unicorns(COLOR[i], H[i]));
                }
            }

            Collections.sort(unicorns, new Comparator<Unicorns>() {
                @Override
                public int compare(Unicorns u1, Unicorns u2) {
                    return (u2.heads > u1.heads)?1:(u2.heads < u1.heads)?-1:0;
                }
            });

            int [] h = new int[3];
            char [] c = new char[3];

            for (int i=0, j=0;i<unicorns.size();i++) {
                Unicorns unicorn = unicorns.get(i);
                char C = unicorn.color;
                if (C == R||C == Y||C == B) {
                    h[j] = unicorn.heads;
                    c[j] = unicorn.color;
                    j++;
                }
            }

            int total = h[0]+h[1]+h[2];

            char [] stalls = new char[total];

            for (int b=0;b<2;b++) {
                for (int i=b;i<total;i+=2) {
                    for (int j=0;j<3;j++) {
                        if (h[j] > 0) {
                            stalls[i] = c[j];
                            h[j]--;
                            break;
                        }
                    }
                }
            }

            String result = String.valueOf(stalls);

            if (mixed) {
                String oneMixed[] = {null, "BO", null, "RG", null, "YV"};
                String replaceSource[] = {null, "B", null, "R", null, "Y"};
                String replaceTarget[] = {null, "BOB", null, "RGR", null, "YVY",};

                for (int i=1;i<COLORS;i+=2) {
                    for (int j=0;j<H[i];j++) {
                       if (result.isEmpty()) {
                           result = oneMixed[i];
                       } else {
                           result = result.replaceFirst(replaceSource[i], replaceTarget[i]);
                       }
                    }
                }
            }

            return result;
        }
    }

    @Override
    protected String getSampleInput() {
        return "4\n" +
                "6 2 0 2 0 2 0\n" +
                "3 1 0 2 0 0 0\n" +
                "6 2 0 1 1 2 0\n" +
                "4 0 0 2 0 0 2";
    }

    @Override
    protected String getSampleOutput() {
        return "Case #1: RYBRBY\n" +
                "Case #2: IMPOSSIBLE\n" +
                "Case #3: YBRGRB\n" +
                "Case #4: YVYV";
    }

    public static void main(String[] args) {
        Problem problem = new ProblemB();
        problem.solve();
    }

    @Test
    public void testSample() {
        Problem problem = new ProblemB();
        boolean result = problem.test();

        assertTrue(result);
    }
}
