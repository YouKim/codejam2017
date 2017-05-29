package jam2017.round1B;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProblemB extends Round1B {

    public ProblemB() {
        mAlpha = "B";
        mTitle = "Stable Neigh-bors";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {
        Neighbors neighbors = new Neighbors(in);
        String result = neighbors.solve(in);
        out.printf("Case #%d: %s\n", testNumber, result);
    }

    static class Neighbors {

        static final String IMPOSSIBLE = "IMPOSSIBLE";
        static char [] COLOR = {'R', 'O', 'Y', 'G', 'B', 'V'};

        class Unicorn {
            char hair;
            int heads;

            Unicorn(char hair, int heads) {
                this.hair = hair;
                this.heads = heads;
            }
        }

        int N;

        ArrayList<Unicorn> unicorns;
        ArrayList<Unicorn> mixeds;

        Neighbors(InputReader in) {
            N = in.nextInt();

            unicorns = new ArrayList<>();
            mixeds = new ArrayList<>();

            for (int i=0;i<3;i++) {
                Unicorn unicorn = new Unicorn(COLOR[i*2], in.nextInt());
                unicorns.add(unicorn);
                Unicorn mixed = new Unicorn(COLOR[i*2+1], in.nextInt());
                mixeds.add(mixed);
            }
        }

        public String solve(InputReader in) {

            Collections.sort(unicorns, new Comparator<Unicorn>() {
                @Override
                public int compare(Unicorn o1, Unicorn o2) {
                    return (o2.heads > o1.heads)?1:(o2.heads < o1.heads)?-1:0;
                }
            });

            String result = solve();

            System.out.println(result.toString());
            return result.toString();
        }

        private String solve() {

            int [] c = new int[3];
            char [] h = new char[3];

            for (int i=0;i<3;i++) {
                Unicorn unicorn = unicorns.get(i);
                c[i] = unicorn.heads;
                h[i] = unicorn.hair;
            }

            if ((c[0]>c[1]+c[2])
                    || (c[1]>c[1]+c[0])
                    || (c[2]>c[0]+c[1])) {
                return IMPOSSIBLE;
            }

            int total = c[0]+c[1]+c[2];

            char [] stalls = new char[total];

            for (int b=0;b<2;b++) {
                for (int i=b;i<stalls.length;i+=2) {
                    for (int j=0;j<3;j++) {
                        if (c[j] > 0) {
                            stalls[i] = h[j];
                            c[j]--;
                            break;
                        }
                    }
                }
            }

            String result = String.valueOf(stalls);
            return result;
        }

    }
}
