package qualificationRound;

import java.io.PrintWriter;

public class ProblemA extends Problem {

    public ProblemA() {
        mAlpha = "A";
        mTitle = "Oversized Pancake Flipper";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {
        String cakes = in.next();
        int n = in.nextInt();
        String count;
        if (DEBUG)
            System.out.println("cakes:" + cakes + ", " + n);

        Pancake cake = new Pancake(cakes, n);
        int result = cake.solve();

        if (result < 0) {
            count = "IMPOSSIBLE";
        } else {
            count = "" + result;
        }
        System.out.printf("Cakes #%d: %s\n", testNumber, count);
        out.printf("Case #%d: %s\n", testNumber, count);
    }

static class Pancake {

        static final char P = '+';
        static final char M = '-';

        private char[] cakes;
        private int mSize;

        Pancake(String m, int flipSize) {
            cakes = m.toCharArray();
            mSize = flipSize;
        }

        boolean flip(int index) {

            final int end = index + mSize;

            if (cakes.length < end) {
                return false;
            }

            for (int i=index;i<end;i++) {
                cakes[i] = (cakes[i] != P?P:M);
            }
            return true;
        }

        int solve() {
            int count = 0;
            for (int i=0;i<cakes.length;i++) {
                if (cakes[i] == M) {
                    if (!flip(i)) {
                        return -1;
                    } else {
                        count++;
                    }
                }
            }

            return count;
        }
    }
}
