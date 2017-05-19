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
        System.out.println("cakes:" + cakes + ", " + n );

        Pancake cake = new Pancake(cakes, n);
        int result = cake.solve();

        if (result < 0) {
            count = "IMPOSSIBLE";
        } else {
            count = "" + result;
        }

        out.printf("Case #%d: %s\n", testNumber, count);
    }

 static class Pancake {

        private char[] cakes;
        private int mSize;

        Pancake(String m, int flipSize) {
            cakes = m.toCharArray();
            mSize = flipSize;
        }

        boolean flip(int index) {
            if (cakes.length < index + mSize) {
                return false;
            }

            for (int i=0;i<mSize;i++) {
                char ch = cakes[i+index];

                if (ch == '+') {
                    cakes[i+index] = '-';
                } else {
                    cakes[i+index] = '+';
                }
            }
            return true;
        }

        int solve() {
            int count = 0;
            for (int i=0;i<cakes.length;i++) {
                if (cakes[i] == '-') {
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
