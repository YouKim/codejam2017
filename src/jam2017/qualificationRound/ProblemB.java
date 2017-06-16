package jam2017.qualificationRound;

public class ProblemB extends Qulification {

    public ProblemB() {
        mAlpha = "B";
        mTitle = "Tidy Numbers";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new Number(in, testNumber, result);
    }

    static class Number extends TestCase {

        private char[] numbers;

        Number(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);
            numbers = in.next().toCharArray();
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
