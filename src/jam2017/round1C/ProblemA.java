package jam2017.round1C;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProblemA extends Round1C {

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

        class Cake {
            long r;
            long h;
            long side;
            long top;

            Cake(int R, int H) {
                r = R;
                h = H;
                side = 2 * r * h;
                top = r * r;
            }
        }

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

        double solve() {
            Collections.sort(cakes, new Comparator<Cake>() {
                @Override
                public int compare(Cake o1, Cake o2) {
                    return (o2.r > o1.r)?1:(o2.r < o1.r)?-1:0;
                }
            });

            Collections.sort(sides, new Comparator<Cake>() {
                @Override
                public int compare(Cake o1, Cake o2) {
                    return (o2.side > o1.side)?1:(o2.side < o1.side)?-1:0;
                }
            });


            long maximumArea = 0;

            while (cakes.size() >= K) {

                Cake bottom = cakes.remove(0);
                sides.remove(bottom);

                long area = bottom.top + bottom.side;

                for (int i=0;i+1<K;i++) {
                    Cake stack = sides.get(i);
                    area += stack.side;
                }

                if (maximumArea < area) {
                    maximumArea = area;
                }
            }

            return maximumArea * Math.PI;
        }

        /* another solution. keep K-1 stack of cakes on top of base pancake.
        double solve2() {
            Collections.sort(cakes, new Comparator<Cake>() {
                @Override
                public int compare(Cake o1, Cake o2) {
                    return (o2.r > o1.r)?1:(o2.r < o1.r)?-1:0;
                }
            });

            Collections.sort(sides, new Comparator<Cake>() {
                @Override
                public int compare(Cake o1, Cake o2) {
                    return (o2.side > o1.side)?1:(o2.side < o1.side)?-1:0;
                }
            });

            ArrayList<Cake> stacks = new ArrayList<>();
            long stackedSide = 0;

            for (int i=0;i+1<K;i++) {
                Cake stack = sides.remove(0);
                stacks.add(stack);
                stackedSide += stack.side;
            }

            long maximumArea = 0;

            while (cakes.size() >= K) {

                Cake bottom = cakes.remove(0);
                sides.remove(bottom);

                boolean removed = stacks.remove(bottom);
                if (removed) {
                    stackedSide -= bottom.side;
                    Cake newCake = sides.remove(0);

                    stacks.add(newCake);
                    stackedSide += newCake.side;
                }

                long area = bottom.top + bottom.side + stackedSide;

                if (maximumArea < area) {
                    maximumArea = area;
                }
            }

            return maximumArea * Math.PI;
        }
        */
    }
}