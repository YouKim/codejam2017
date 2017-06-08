package jam2017.qualificationRound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ProblemC extends Qulification {

    public ProblemC() {
        mAlpha = "C";
        mTitle = "Bathroom Stalls";
    }

    @Override
    protected List<TestCase> createTestCase(int testCount, InputReader in, StringBuffer [] results) {

        List<TestCase> tcs = new ArrayList<>();

        for (int i=1;i<=testCount;i++) {
            long stalls = in.nextLong();
            long people = in.nextLong();

            Stall stall = new Stall(stalls, people, i, results[i]);
            tcs.add(stall);
        }

        return tcs;
    }

    static class Stall extends TestCase {

        private long mPeople;
        private TreeMap<Long, Long> tiedStalls;

        Stall(long stalls, long people, int testNumber, StringBuffer result) {
            super(testNumber, result);
            mPeople = people;
            tiedStalls = new TreeMap<>();
            add(stalls, 1);
        }

        void add(long size, long number) {
            if (size < 1) {
                return;
            }

            Long old = tiedStalls.get(size);

            if (old != null) {
                number = number + old;
            }

            tiedStalls.put(size, number);
            if (DEBUG) System.out.println("Stall add:" + size + " : " + number);
        }

        @Override
        protected String solve() {
            long located = 0;
            long min = 0;
            long max = 0;
            while (located < mPeople) {
                Entry<Long, Long> sections = tiedStalls.pollLastEntry();

                long size = sections.getKey();
                long number = sections.getValue();

                if (size > 1) {
                    if (size % 2 == 1) {
                        long divided = (size - 1) / 2;
                        add(divided, 2 * number);
                        min = max = divided;
                    } else {
                        long divided = (size) / 2;
                        add(divided, number);
                        max = divided;
                        add(divided-1, number);
                        min = divided-1;
                    }
                } else if (size == 1) {
                    min = max = 0;
                } else {
                    System.out.println("Size is 0!!!");
                    return null;
                }
                located += number;
            }

            return String.format("Case #%d: %d %d\n", testNumber, max, min);
        }

    }
}
