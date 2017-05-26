package round1C;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProblemA extends Problem {

    public ProblemA() {
        mAlpha = "A";
        mTitle = "Ample Syrup";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {

        Pancakes cakes = new Pancakes(in);
        double result = cakes.solve();
        out.printf("Case #%d:%f\n", testNumber, result);
    }

    static class Pancakes {

        final int N;
        final int K;

        ArrayList<Cake> cakes;
        ArrayList<Cake> sides;
        ArrayList<Cake> totals;

        Pancakes(InputReader in) {
            N = in.nextInt();
            K = in.nextInt();

            cakes = new ArrayList<>();
            sides = new ArrayList<>();
            totals = new ArrayList<>();

            for (int i=0;i<N;i++) {
                int Ri = in.nextInt();
                int Hi = in.nextInt();

                Cake cake = new Cake(Ri, Hi);
                cakes.add(cake);
                sides.add(cake);
                totals.add(cake);
            }
        }

        double solve () {
            Collections.sort(cakes, new Comparator<Cake>() {
                @Override
                public int compare(Cake o1, Cake o2) {
                    return o1.r - o2.r;
                }
            });

            Collections.sort(sides, new Comparator<Cake>() {
                @Override
                public int compare(Cake o1, Cake o2) {
                    return o1.side - o2.side;
                }
            });

            Collections.sort(totals, new Comparator<Cake>() {
                @Override
                public int compare(Cake o1, Cake o2) {
                    return o1.total - o2.total;
                }
            });

            return 0;
        }

        class Cake {
            int r;
            int h;
            int side;
            int total;
            double topArea;
            double sideArea;

            Cake(int r, int h) {
                this.r = r;
                this.h = h;
                side = r * h;
                total = r * ( r + h * 2);
                topArea = getTopArea();
                sideArea = getSideArea();
            }

            boolean isSideisBigger() {
                return (2 * h) > r;
            }

            double getTotalArea() {
               return (r + 2 * h) * r * Math.PI;
            }

            double getSideArea() {
                return 2 * h * r * Math.PI;
            }

            double getTopArea() {
                return r * r * Math.PI;
            }
        }
    }
}
