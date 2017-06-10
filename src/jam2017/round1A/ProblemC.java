package jam2017.round1A;

import java.util.ArrayList;
import java.util.List;

public class ProblemC extends Round1A {

    public ProblemC() {
        mAlpha = "C";
        mTitle = "Play the Dragon";
    }

    @Override
    protected List<TestCase> createTestCase(int testCount, InputReader in,  StringBuffer [] results) {

        List<TestCase> tcs = new ArrayList<>();

        for (int i=1;i<=testCount;i++) {
            Game game = new Game(in, i, results[i]);
            tcs.add(game);
        }

        return tcs;
    }

    static class Game extends TestCase {

        static final int TURN_IMPOSSIBLE = -1;

        final int HD, AD, HK, AK, BUFF, DEBUFF;

        Game(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            HD = in.nextInt(); AD = in.nextInt(); HK = in.nextInt(); AK = in.nextInt();
            BUFF = in.nextInt(); DEBUFF = in.nextInt();
        }

        @Override
        protected String solve() {
            int AB = calcAB();
            int T = simulate(HD, AK, AB);
            String result = (T < Integer.MAX_VALUE)?String.valueOf(T):IMPOSSIBLE;

            System.out.printf("Case #%d: %s\n", testNumber, result);
            return String.format("Case #%d: %s\n", testNumber, result);
        }

        private int calcAB() {
            if (BUFF <= 0) {
                return ceil(HK, AD);
            } else {
                double result = (Math.sqrt(HK) * Math.sqrt(BUFF) - AD ) / BUFF;
                int start = (result < 0)?0:((int) result);

                int ABmin = Integer.MAX_VALUE;
                for (int B=start;B<HK;B++) {
                    int AB = ceil(HK, AD + B*BUFF) + B;
                    if (AB < ABmin) {
                        ABmin = AB;
                    } else if (AB > ABmin) {
                        break;
                    }
                }

                return ABmin;
            }
        }

        int calcTurnAB(int Hd, int Ak, int AB) {

            // When Ak == 0, ab is Integer.MAX. Do not use condition like (AB <= ab+1)
            int ab = floor(Hd-1, Ak); // Turns left before cure;

            if (AB-1 <= ab || Ak == 0) { // Cure is not needed in final attack(-1).
                return AB;
            }

            // One cure here(+1)! Dragon is cured(Hd -> HD) and knight's turn(-Ak).
            // Calculate interval on HD-Ak-1. (-1) to ensure Hd > 0.

            int cureInterval = floor(HD-Ak-1, Ak);

            if (cureInterval < 1) {
                return TURN_IMPOSSIBLE;
            }

            int c = floor2(AB-ab-1, cureInterval); // Cure is not needed in final attack(-1).

            return AB + c + 1; // Don't forget one cure over there.
        }

        int simulate(int Hd, int Ak, int AB) {
            int D = 0, Dtarget = 0;
            int total = 0;
            int Tmin = Integer.MAX_VALUE;

            while (Hd > 0) {
                if (Hd == HD - Ak && total > 1) {
                    int cureInterval = floor(Hd-1, Ak);

                    if (cureInterval < 1) {
                        break;
                    }

                    int t = Dtarget - D - 1;
                    t = (t - t%cureInterval);

                    if (t > 0) {
                        int c = floor(t, cureInterval) ;

                        Ak = debuff(Ak, t);
                        Hd = HD - Ak;

                        D += t;
                        total += (t+c);
                    }
                }

                final int d = Dtarget - D;

                if (d > 0) {
                    int t = floor(Hd-1, Ak-DEBUFF);

                    if (t > 0) {
                        t = (t>d)?d:t;

                        Hd = Hd - (t*Ak - ((t+1) * t * DEBUFF)/2);
                        Ak = debuff(Ak, t);

                        if (t < d && Hd - Ak + DEBUFF > 0) {
                            Hd = Hd - Ak + DEBUFF;
                            Ak = debuff(Ak, 1);
                            t++;
                        }

                        D += t;
                        total += t;
                    }
                }

                if (D == Dtarget) {
                    int ab = calcTurnAB(Hd, Ak, AB);
                    if (ab > 0 && total + ab < Tmin) {
                        Tmin = total + ab;
                    }

                    if (DEBUFF <= 0 || Ak <= 0) {
                        break;
                    }

                    int cureInterval = floor(HD-1, Ak);
                    int targetAttack = floor(HD-1, cureInterval+1);
                    Dtarget = ceil(AK - targetAttack, DEBUFF);
                }

                if (Hd - Ak + DEBUFF <= 0) {
                    total++;
                    Hd = HD - Ak;
                }
            }

            return Tmin;
        }

        private int debuff(int Ak, int n) {
            Ak -= (n * DEBUFF);
            return Ak<0?0:Ak;
        }

        private int ceil(int x, int y) { // y > 0 guaranteed
            return x/y + (x%y>0?1:0);
        }

        private int floor(int x, int y) {
            if (y<=0) {
                return Integer.MAX_VALUE;
            }

            return x/y;
        }

        private int floor2(int x, int y) { // y > 0 guaranteed
            return x/y - ((x%y==0)?1:0);
        }
    }
}
