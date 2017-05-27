package jam2017.round1B;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProblemA extends Round1B {

    public ProblemA() {
        mAlpha = "A";
        mTitle = "Steed 2: Cruise Control";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {
        CruiseControl cc = new CruiseControl(in);
        double result = cc.solve();
        out.printf("Case #%d: %f\n", testNumber, result);
    }

    static class CruiseControl {

        int D;
        int N;

        ArrayList<Horse> horses;

        public CruiseControl(InputReader in) {
            D = in.nextInt();
            N = in.nextInt();

            horses = new ArrayList<>();

            for (int i=0;i<N;i++) {
                int P = in.nextInt();
                int speed = in.nextInt();
                int distance = D - P;

                Horse horse = new Horse(distance, speed);
                horses.add(horse);
            }
        }

        double solve() {
            Collections.sort(horses, new Comparator<Horse>() {
                @Override
                public int compare(Horse o1, Horse o2) {
                    return (o2.time > o1.time)?1:(o2.time < o1.time)?-1:0;
                }
            });

            Horse slowest = horses.get(0);
            double time = slowest.time;
            double velocity = D / time;

            return velocity;
        }


        class Horse {
            double distance;
            double speed;
            double time;

            public Horse(int distance, int speed) {
                this.distance = distance;
                this.speed = speed;

                time = distance / speed;
            }
        }

    }

}
