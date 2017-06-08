package jam2017.qualificationRound;

import java.util.ArrayList;
import java.util.List;

public class ProblemA extends Qulification {

    public ProblemA() {
        mAlpha = "A";
        mTitle = "Oversized Pancake Flipper";
    }

    @Override
    protected List<TestCase> createTestCase(int testCount, InputReader in,  StringBuffer [] results) {

        List<TestCase> tcs = new ArrayList<>();

        for (int i=1;i<=testCount;i++) {
            String cakes = in.next();
            int n = in.nextInt();

            Pancake cake = new Pancake(cakes, n, i, results[i]);
            tcs.add(cake);
        }

        return tcs;
    }

    static class Pancake extends TestCase {

        private char[] cakes;
        private int mSize;

        Pancake(String m, int flipSize, int testNumber, StringBuffer result) {
            super(testNumber, result);
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

        @Override
        protected String solve() {
            int count = 0;
            for (int i=0;i<cakes.length;i++) {
                if (cakes[i] == '-') {
                    if (flip(i)) {
                        count++;
                    } else {
                        count = -1;
                    }
                }
            }
            String result = count<0?IMPOSSIBLE:String.valueOf(count);

            return String.format("Case #%d: %s\n", testNumber, result);
        }
    }
}
