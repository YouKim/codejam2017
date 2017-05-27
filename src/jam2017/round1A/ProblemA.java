package jam2017.round1A;

import java.io.PrintWriter;

public class ProblemA extends Round1A {

    public ProblemA() {
        mAlpha = "A";
        mTitle = "Alphabet Cake";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {
        int row = in.nextInt();
        int col = in.nextInt();

        char [][] letters = new char[row][];

        for (int i=0;i<row;i++) {
            String line = in.next();
            letters[i] = line.toCharArray();
        }

        Cake cake = new Cake(row, col, letters);

        String result = cake.solve();
        out.printf("Case #%d:\n%s", testNumber, result);
    }

    static class Cake {

        static final char ASK ='?';

        final int R;
        final int C;

        char [][] mCake;
        boolean[] hasLetter;

        public Cake(int row, int col, char [][] cake) {
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

        public String solve() {

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

            return getResult();
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
