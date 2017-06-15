package jam2017.round1C;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jam2017.round1C.ProblemA.Pancakes.Cake;

public class ProblemB extends Round1C {

    public ProblemB() {
        mAlpha = "B";
        mTitle = "Parenting Partnering";
    }

    @Override
    protected List<TestCase> createTestCase(int testCount, InputReader in,  StringBuffer [] results) {
        List<TestCase> tcs = new ArrayList<>();

        for (int i=1;i<=testCount;i++) {
            Partnering partnering = new Partnering(in, i, results[i]);
            tcs.add(partnering);
        }

        return tcs;
    }

    static class Partnering extends TestCase {

        static final int DAY = 24 * 60; //1440

        int Ac, Aj;
        int [] C, D, J, K;

        protected Partnering(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            Ac = in.nextInt(); Aj = in.nextInt();

            C = new int[Ac]; D = new int[Ac];
            J = new int[Aj]; K = new int[Aj];

            for (int i=0;i<Ac;i++) {
                C[i] = in.nextInt();
                D[i] = in.nextInt();
            }

            for (int i=0;i<Aj;i++) {
                J[i] = in.nextInt();
                K[i] = in.nextInt();
            }
        }

        @Override
        protected String solve() {
            return null;
        }

        class TimeTable {
            ArrayList<Activity> table = new ArrayList<>();
            int Tc, Tj;
            void addActivity(int start, int end, boolean isCameron) {
                table.add(new Activity(start, end, isCameron));
                int duration = end - start;

                if (isCameron) {
                    Tc += duration;
                } else {
                    Tj += duration;
                }
            }

            void sort() {
                Collections.sort(table, new Comparator<Activity>() {
                    @Override
                    public int compare(Activity o1, Activity o2) {
                        return o2.start - o1.start;
                    }
                });
            }

            ArrayList<Freetime> getFreeTime() {
                ArrayList<Freetime> result = new ArrayList<>();

                int size = table.size();
                for (int i=0;i<size;i++) {
                    Activity act1 = table.get(i);
                    Activity act2 = (i+1<size)?table.get(i+1):table.get(0);

                    int duration = (act2.start - act1.end + DAY)%DAY;
                    if (duration > 0) {
                        Freetime free = new Freetime(act1.end, act2.start, duration, act1.isCameron, act2.isCameron);
                        result.add(free);
                    }
                }

                return result;
            }
        }

        class Activity {
            int start;
            int end;
            boolean isCameron;

            Activity(int start, int end, boolean isCameron) {
                this.start = start;
                this.end = end;
                this.isCameron = isCameron;
            }
        }

        class Freetime {
            int start;
            int end;
            int duration;
            boolean startIsCameron;
            boolean endIsCameron;

            Freetime(int start, int end, int duration, boolean startIsCameron, boolean endIsCameron) {
                this.start = start;
                this.end = end;
                this.duration = duration;
                this.startIsCameron = startIsCameron;
                this.endIsCameron = endIsCameron;
            }
        }
    }
}
