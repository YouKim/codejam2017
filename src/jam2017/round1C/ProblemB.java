package jam2017.round1C;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProblemB extends Round1C {

    public ProblemB() {
        mAlpha = "B";
        mTitle = "Parenting Partnering";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new Partnering(in, testNumber, result);
    }

    static class Partnering extends TestCase {

        static final int DAY = 24 * 60; //1440
        int Ac, Aj;
        TimeTable table;

        protected Partnering(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            Ac = in.nextInt(); Aj = in.nextInt();

            table = new TimeTable();

            for (int i=0;i<Ac;i++) {
                int start = in.nextInt();
                int end = in.nextInt();
                table.addActivity(start, end, true);
            }

            for (int i=0;i<Aj;i++) {
                int start = in.nextInt();
                int end = in.nextInt();
                table.addActivity(start, end, false);
            }
        }

        @Override
        protected String solve() {
            table.sort();
            ArrayList<Freetime> freetimes = table.getFreeTime();

            System.out.println("====================");
            for (Freetime freetime:freetimes) {
                freetime.print();
            }

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

            public void print() {
                // TODO Auto-generated method stub
                System.out.printf("%4d %4d %4d %5s %5s\n", start, end, duration, startIsCameron, endIsCameron);
            }
        }
    }
}
