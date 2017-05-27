package qualificationRound;

import java.io.PrintWriter;

public class ProblemA extends Qulification {

    public ProblemA() {
        mAlpha = "A";
        mTitle = "Oversized Pancake Flipper";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {
        String cakes = in.next();
        int n = in.nextInt();

        if (DEBUG) System.out.println("cakes:" + cakes + ", " + n);

        Pancake cake = new Pancake(cakes, n);
        String result = cake.solve();

        System.out.printf("Cakes #%d: %s\n", testNumber, result);
        out.printf("Case #%d: %s\n", testNumber, result);
    }

    static class Pancake {

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
                cakes[i] = (cakes[i]=='+'?'-':'+');
            }
            return true;
        }

        String solve() {
            int count = 0;
            for (int i=0;i<cakes.length;i++) {
                if (cakes[i] == '-') {
                    if (flip(i)) {
                        count++;
                    } else {
                        return "IMPOSSIBLE";
                    }
                }
            }

            return String.valueOf(count);
        }
    }
}
