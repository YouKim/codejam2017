package qualificationRound;

import java.io.PrintWriter;

public class ProblemB extends Problem {

    public ProblemB() {
        mAlpha = "B";
        mTitle = "Tidy Numbers";
    }

    @Override
    void solveTest(int testNumber, InputReader in, PrintWriter out) {

        String numbers = in.next();

        Number number = new Number(numbers);
        String result = number.solve();

        System.out.println("tidyNumbers:" + numbers + " : " + result);
        out.printf("Case #%d: %s\n", testNumber, result);
    }


    static class Number {

        private char[] numbers;

        Number(String m) {
            numbers = m.toCharArray();
        }

        boolean isTidy() {
            for (int i = numbers.length-1;i>0;i--) {
                if (numbers[i-1] > numbers[i]) {
                    return false;
                }
            }

            return true;
        }

        String solve() {
            while(!isTidy()) {
                makeTidy();
            }

            String result = String.valueOf(numbers);
            return String.valueOf(result.replaceFirst("^0+(?!$)", ""));
        }

        void makeTidy() {
            for (int i=1;i<numbers.length;i++) {
                if (numbers[i-1] > numbers[i]) {
                    numbers[i-1]--;

                    for (int j=i;j<numbers.length;j++) {
                        numbers[j] = '9';
                    }
                }
            }
        }
    }
}
