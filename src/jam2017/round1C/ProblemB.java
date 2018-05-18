package jam2017.round1C;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.junit.Test;

import jam2017.Problem;

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
        int ADc, ADj;
        int FTc, FTj;
        TimeTable TT;

        protected Partnering(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            Ac = in.nextInt(); Aj = in.nextInt();

            TT = new TimeTable();

            for (int i=0;i<Ac;i++) {
                int start = in.nextInt();
                int end = in.nextInt();
                ADc += TT.addActivity(start, end, true);
            }

            for (int i=0;i<Aj;i++) {
                int start = in.nextInt();
                int end = in.nextInt();
                ADj += TT.addActivity(start, end, false);
            }

            FTc = 720 - ADj;
            FTj = 720 - ADc;
            // The sum of Activity durations.
            System.out.println("ADc:" + ADc + " ADj" + ADj);
        }

        @Override
        protected String solve() {
            TT.sort();
            ArrayList<Freetime> freetimes = TT.getFreeTime();

            System.out.println("====================");

            Iterator<Freetime> itor = freetimes.iterator();

            int changes = 0;

            while (itor.hasNext()) {
                Freetime item = itor.next();

                if (item.startIsCameron != item.endIsCameron) {
                    changes++;
                } else {
                    if (item.startIsCameron) {
                        if (item.duration <= FTj) {
                            FTj -= item.duration;
                        } else {
                            changes+=2;
                        }
                    } else {
                        if (item.duration <= FTc) {
                            FTc -= item.duration;
                        } else {
                            changes+=2;
                        }
                    }
                }
            }

            changes += TT.countActivitySwitch();

            for (Freetime freetime:freetimes) {
                freetime.print();
            }

            return String.format("Case #%d: %d\n", testNumber, changes);
        }

        class TimeTable {
            ArrayList<Activity> activities = new ArrayList<>();

            int addActivity(int start, int end, boolean isCameron) {
                activities.add(new Activity(start, end, isCameron));
                return  end - start;
            }

            void sort() {
                Collections.sort(activities, new Comparator<Activity>() {
                    @Override
                    public int compare(Activity o1, Activity o2) {
                        return o1.start - o2.start;
                    }
                });
            }

            int countActivitySwitch() {

                int size = activities.size();

                if (size < 2) {
                    return 0;
                }

                int count = 0;

                for (int i=0;i<size;i++) {
                    Activity act1 = activities.get(i);
                    Activity act2 = (i+1<size)?activities.get(i+1):activities.get(0);

                    if (act1.end%DAY == act2.start%DAY) {
                        if (act1.isCameron != act2.isCameron) {
                            count++;
                        }
                    }
                }

                return count;
            }

            ArrayList<Freetime> getFreeTime() {
                ArrayList<Freetime> result = new ArrayList<>();

                int size = activities.size();
                for (int i=0;i<size;i++) {
                    Activity act1 = activities.get(i);
                    Activity act2 = (i+1<size)?activities.get(i+1):activities.get(0);

                    int duration = (act2.start - act1.end + DAY)%DAY;
                    if (duration > 0) {
                        Freetime free = new Freetime(act1.end, act2.start, duration, act1.isCameron, act2.isCameron);
                        result.add(free);
                    }
                }

                Collections.sort(result, new Comparator<Freetime>() {
                    @Override
                    public int compare(Freetime f1, Freetime f2) {
                        return f1.duration - f2.duration;
                    }
                });

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
                System.out.printf("%4d %4d %4d %5s %5s\n", start, end, duration, startIsCameron, endIsCameron);
            }
        }
    }

    @Override
    protected String getSampleInput() {
        return "5\n" +
                "1 1\n" +
                "540 600\n" +
                "840 900\n" +
                "2 0\n" +
                "900 1260\n" +
                "180 540\n" +
                "1 1\n" +
                "1439 1440\n" +
                "0 1\n" +
                "2 2\n" +
                "0 1\n" +
                "1439 1440\n" +
                "1438 1439\n" +
                "1 2\n" +
                "3 4\n" +
                "0 10\n" +
                "1420 1440\n" +
                "90 100\n" +
                "550 600\n" +
                "900 950\n" +
                "100 150\n" +
                "1050 1400";
    }

    @Override
    protected String getSampleOutput() {
        return "Case #1: 2\n" +
                "Case #2: 4\n" +
                "Case #3: 2\n" +
                "Case #4: 4\n" +
                "Case #5: 6";
    }

    public static void main(String[] args) {
        Problem problem = new ProblemB();
        problem.solve();
    }

    @Test
    public void testSample() {
        Problem problem = new ProblemB();
        boolean result = problem.test();

        assertTrue(result);
    }
}
