package jam2017.round1A;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jam2017.Problem;

public class ProblemC extends Round1A {

    public ProblemC() {
        mAlphabet = "C";
        mTitle = "Play the Dragon";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new Game(in, testNumber, result);
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
                int B = 0;
                int Ad = AD;
                if ((HK - AD) / BUFF > 100) {
                    double result = (Math.sqrt(HK) * Math.sqrt(BUFF) - AD) / BUFF;
                    B = (result < 0) ? 0 : ((int) result);
                    Ad = AD + B * BUFF;
                }

                int ABmin = Integer.MAX_VALUE;
                for (;B<HK;B++, Ad += BUFF) {
                    int AB = ceil(HK, Ad) + B;
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
            if (Ak <= 0) {
                return AB;
            }

            int t = floor(Hd-1, Ak); // Turns left before cure;

            if (AB <= t + 1) { // Cure is not needed in final attack(+1).
                return AB;
            }

            // One cure here(+1)! Dragon is cured(Hd -> HD) and knight's turn(-Ak).
            // Calculate interval on HD-Ak-1. (-1) to ensure Hd > 0.

            int cureInterval = floor(HD-Ak-1, Ak);

            if (cureInterval < 1) {
                return TURN_IMPOSSIBLE;
            }

            int c = floor2(AB-t-1, cureInterval); // Cure is not needed in final attack(-1).

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

                if (d > 0 && Ak > 0) {
                    int t;
                    if (Ak-DEBUFF > 0) {
                        t = floor(Hd-1, Ak-DEBUFF);
                        t = (t>d)?d:t;
                    } else {
                        t = d;
                    }

                    if (t > 0) {
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
            return x/y;
        }

        private int floor2(int x, int y) { // y > 0 guaranteed
            return x/y - ((x%y==0)?1:0);
        }
    }

    @Override
    protected String getSampleInput() {
        return "4\n" +
                "11 5 16 5 0 0\n" +
                "3 1 3 2 2 0\n" +
                "3 1 3 2 1 0\n" +
                "2 1 5 1 1 1";
    }

    @Override
    protected String getSampleOutput() {
        return "Case #1: 5\n" +
                "Case #2: 2\n" +
                "Case #3: IMPOSSIBLE\n" +
                "Case #4: 5";
    }

    public static void main(String[] args) {
        Problem problem = new ProblemC();
        problem.solve();
    }

    @Test
    public void testSample() {
        Problem problem = new ProblemC();
        boolean result = problem.test();

        assertTrue(result);
    }
}
