package jam2017.qualificationRound;

import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ProblemC extends Qulification {

    public ProblemC() {
        mAlpha = "C";
        mTitle = "Bathroom Stalls";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {
        long stalls = in.nextLong();
        long people = in.nextLong();

        Stall stall = new Stall(stalls, people);
        String result = stall.solve();

        System.out.printf("Stalls: #%d: %s\n", testNumber, result);
        out.printf("Case #%d: %s\n", testNumber, result);
    }

    static class Stall {

        private long mPeople;
        private TreeMap<Long, Long> tiedStalls;

        Stall(long stalls, long people) {
            mPeople = people;
            tiedStalls = new TreeMap<Long, Long>();
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

        String solve() {
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

            return String.format("%d %d", max, min);
        }

    }
}
