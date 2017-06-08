package jam2017.qualificationRound;

import java.util.ArrayList;
import java.util.List;

public class ProblemB extends Qulification {

    public ProblemB() {
        mAlpha = "B";
        mTitle = "Tidy Numbers";
    }

    @Override
    protected List<TestCase> createTestCase(int testCount, InputReader in, StringBuffer[] results) {
        // TODO Auto-generated method stub
        List<TestCase> tcs = new ArrayList<>();

        for (int i=1;i<=testCount;i++) {
            String letters = in.next();
            Number number = new Number(letters, i, results[i]);
            tcs.add(number);
        }

        return tcs;
    }

    static class Number extends TestCase {

        private char[] numbers;

        Number(String m, int testNumber, StringBuffer result) {
            super(testNumber, result);
            numbers = m.toCharArray();
        }

        @Override
        protected String solve() {
            for (int i=1;i<numbers.length;i++) {
                if (numbers[i-1] > numbers[i]) {
                    numbers[i-1]--;

                    for (int j=i;j<numbers.length;j++) {
                        numbers[j] = '9';
                    }

                    i = 0;
                }
            }

            String result = String.valueOf(numbers);
            return String.format("Case #%d: %s\n", testNumber, result.replaceFirst("^0+(?!$)", ""));
        }
    }

}
