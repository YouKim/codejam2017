package round1A;

import java.io.PrintWriter;

public class ProblemA extends Problem {

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

        //cake.printCake();
        String result = cake.solve();
        //cake.printCake();
        out.printf("Case #%d:\n%s", testNumber, result);
    }

    static class Cake {

        static final char ASK ='?';

        int mRow;
        int mCol;

        char [][] mCake;
        boolean[] hasLetter;

        public Cake(int row, int col, char [][] cake) {
            mRow = row;
            mCol = col;
            mCake = cake;

            hasLetter = new boolean[row];
            checkLetter();
        }

        void checkLetter() {
            for (int r=0;r<mRow;r++) {
                for (int c=0;c<mCol;c++) {
                    if (mCake[r][c] != ASK) {
                        hasLetter[r] = true;
                        break;
                    }
                }
            }
        }

        public String solve() {

            for (int r=0;r<mRow;r++) {
                // left -> right by column
                for (int c=1;c<mCol;c++) {
                    if (mCake[r][c] == ASK) {
                        mCake[r][c] = mCake[r][c-1];
                    }
                }

                // right -> left by column
                for (int c=mCol-2;c>=0;c--) {
                    if (mCake[r][c] == ASK) {
                        mCake[r][c] = mCake[r][c+1];
                    }
                }
            }

            // top -> bottom by line
            for (int r=1;r<mRow;r++) {
                if (hasLetter[r] == false && hasLetter[r-1] == true) {
                    copyLine(r-1, r);
                    hasLetter[r] = true;
                }
            }

            // bottom -> top by line
            for (int r=mRow-1;r>0;r--) {
                if (hasLetter[r-1] == false && hasLetter[r] == true) {
                    copyLine(r, r-1);
                    hasLetter[r-1] = true;
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

        void copyLine(int source, int target) {
            for (int c=0;c<mCol;c++) {
                 mCake[target][c] = mCake[source][c];
            }
        }

        public void printCake() {
            System.out.println("==============");
            for (int i=0;i<mCake.length;i++) {
                System.out.println(mCake[i]);
            }
        }
    }
}
