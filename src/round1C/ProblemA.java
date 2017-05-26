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
        out.printf("Case #%d: %f\n", testNumber, result);
    }

    static class Pancakes {

        final int N;
        final int K;

        ArrayList<Cake> cakes;
        ArrayList<Cake> sides;

        Pancakes(InputReader in) {
            N = in.nextInt();
            K = in.nextInt();

            cakes = new ArrayList<>();
            sides = new ArrayList<>();

            for (int i=0;i<N;i++) {
                int Ri = in.nextInt();
                int Hi = in.nextInt();

                Cake cake = new Cake(Ri, Hi);
                cakes.add(cake);
                sides.add(cake);
            }
        }

        double solve () {
            Collections.sort(cakes, new Comparator<Cake>() {
                @Override
                public int compare(Cake o1, Cake o2) {
                    return (int) (o2.r - o1.r);
                }
            });

            Collections.sort(sides, new Comparator<Cake>() {
                @Override
                public int compare(Cake o1, Cake o2) {
                    return (int) (o2.side - o1.side);
                }
            });


            long maximumArea = 0;

            while (cakes.size() >= K) {

                long area = 0;

                Cake bottom = cakes.remove(0);
                sides.remove(bottom);

                area += bottom.total;

                for (int i=0;i+1<K;i++) {
                    Cake stack = sides.get(i);
                    area += stack.side;
                }

                if (maximumArea < area) {
                    maximumArea = area;
                }
            }

            return maximumArea * 3.141592653589793238;
        }

        class Cake {
            long r;
            long h;
            long side;
            long total;

            Cake(long r, long h) {
                this.r = r;
                this.h = h;
                side = 2 * r * h;
                total = r * ( r + h * 2);
            }
        }
    }
}
