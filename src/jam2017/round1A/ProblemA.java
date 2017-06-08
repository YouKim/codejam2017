package jam2017.round1A;

import java.util.ArrayList;
import java.util.List;

public class ProblemA extends Round1A {

    public ProblemA() {
        mAlpha = "A";
        mTitle = "Alphabet Cake";
    }

    @Override
    protected List<TestCase> createTestCase(int testCount, InputReader in,  StringBuffer [] results) {

        List<TestCase> tcs = new ArrayList<>();

        for (int i=1;i<=testCount;i++) {
            int row = in.nextInt();
            int col = in.nextInt();

            char [][] letters = new char[row][];

            for (int j=0;j<row;j++) {
                String line = in.next();
                letters[j] = line.toCharArray();
            }

            Cake cake = new Cake(row, col, letters, i, results[i]);

            tcs.add(cake);
        }

        return tcs;
    }

    static class Cake extends TestCase {

        static final char ASK ='?';

        final int R;
        final int C;

        char [][] mCake;
        boolean[] hasLetter;

        public Cake(int row, int col, char [][] cake, int testNumber, StringBuffer result) {
            super(testNumber, result);

            R = row;
            C = col;
            mCake = cake;

            hasLetter = new boolean[row];
            checkLetter();
        }

        void checkLetter() {
            for (int r=0;r<R;r++) {
                for (int c=0;c<C;c++) {
                    if (mCake[r][c] != ASK) {
                        hasLetter[r] = true;
                        break;
                    }
                }
            }
        }

        @Override
        protected String solve() {

            for (int r=0;r<R;r++) {

                char[] row = mCake[r];

                for (int i=1;i<C;i++) {

                    if (row[i] == ASK) {
                        row[i] = row[i-1];
                    }

                    if (row[C-i-1] == ASK) {
                        row[C-i-1] = row[C-i];
                    }
                }
            }

            for (int i=1;i<R;i++) {
                // top -> bottom by line
                if (hasLetter[i] == false && hasLetter[i-1] == true) {
                    mCake[i] = mCake[i-1];
                    hasLetter[i] = true;
                }

                // bottom -> top by line
                if (hasLetter[R-i-1] == false && hasLetter[R-i] == true) {
                    mCake[R-i-1] = mCake[R-i];
                    hasLetter[R-i-1] = true;
                }
            }

            return String.format("Case #%d:\n%s", testNumber, getResult());
        }

        String getResult() {
            StringBuffer result = new StringBuffer();

            for (int i=0;i<mCake.length;i++) {
                result.append(mCake[i]).append("\n");
            }
            return result.toString();
        }
    }
}
