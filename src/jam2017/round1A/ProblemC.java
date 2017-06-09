package jam2017.round1A;

import java.util.ArrayList;
import java.util.HashMap;
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
        ArrayList<Integer> debuffThreshold;

        Game(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            HD = in.nextInt(); AD = in.nextInt(); HK = in.nextInt(); AK = in.nextInt();
            BUFF = in.nextInt(); DEBUFF = in.nextInt();
            debuffThreshold = new ArrayList<>();
        }

        @Override
        protected String solve() {

            int AB = calcAB();
            calcDebuffThreshold();

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

        private void calcDebuffThreshold() {
            int Dmax = DEBUFF>0?ceil(AK, DEBUFF):0;

            for (int D=0;D<=Dmax;) {
                debuffThreshold.add(D);
                int cureInterval = floor(HD-1, AK-D*DEBUFF);
                int targetAttack = floor(HD-1, cureInterval+1);
                int nextD = ceil(AK - targetAttack, DEBUFF);

                if (AK-D*DEBUFF <= 0 || nextD <= D) {
                    break;
                }

                D = nextD;
            }
        }

        int calcTurnAB(int Hd, int Ak, int AB) {

            // When Ak == 0 ab is Integer.MAX
            int ab = floor(Hd-1, Ak); // turn left before cure;

            if (AB-1 <= ab || Ak == 0) { // final cure can be -> attack.(+1)
                return AB;
            }

            // One Cure(+1) here! Dragon is cured and let heal(Hd -> HD) and attacked by knight(-Ak).
            // Hd should be greater than 0. so -1 to ensure Hd > 0

            int cureInterval = floor(HD-Ak-1, Ak);

            if (cureInterval < 1) {
                return TURN_IMPOSSIBLE;
            }

            int c = floor2(AB-ab-1, cureInterval); // total cure is c + 1

            return AB + c + 1;
        }

        int simulate(int Hd, int Ak, int AB) {
            int D = 0;
            int Dtarget = 0;
            int total = 0;
            int Tmin = Integer.MAX_VALUE;

            boolean cured = false;

            /*
             * TODO:
             * Using math to calculate D + C turns. At this time, loop method.
             */

            while (Hd > 0) {

                int d = Dtarget - D;

                if (d > 0) {
                    int t = calcPossibleDebuff(Hd, Ak, d); // turn left before cure;

                    if (t > 0) {
                        t = Math.min(t, d);
                        Hd = Hd - (t*Ak - ((t+1) * t * DEBUFF)/2);
                        Ak = Ak - t * DEBUFF;
                        Ak = Ak<0?0:Ak;
                        D += t;
                        total += t;
                        cured = false;
                    } else {
                        if (cured ) {
                            return Tmin;
                        }
                    }
                }

                if (D == Dtarget) {
                    int ab = calcTurnAB(Hd, Ak, AB);
                    if (ab>0 && total + ab < Tmin) {
                        Tmin = total + ab;
                    }

                    int nextD = getNextD(D);
                    if (nextD < 0) {
                        break;
                    }
                    Dtarget = nextD;
                }

                if (Hd - Ak + DEBUFF <= 0) {
                    total++;
                    Hd = HD - Ak;
                    cured = true;
                }
            }

            return Tmin;
        }

        private int getNextD(int D) {
            for (int i=0;i<debuffThreshold.size();i++) {
                int d = debuffThreshold.get(i);
                if (d > D) {
                    return d;
                }
            }

            return -1;
        }

        private int calcPossibleDebuff(int Hd, int Ak, int Dmax) {

            if (Ak-DEBUFF <= 0) {
                return 1;
            }

            int start = floor(Hd-1, Ak-DEBUFF);
            int end = Dmax;

            int result = start>Dmax?Dmax:start;

            for (int i=start+1;i<=end;i++) {
                if (Hd - (i*Ak - ((i+1) * i * DEBUFF)/2) > 0) {
                    result = i;
                } else {
                    break;
                }
            }

            return result;
        }

        private int ceil(int x, int y) {
            if (y<=0) {
                return Integer.MAX_VALUE;
            }

            return x/y + (x%y>0?1:0);
        }

        private int floor(int x, int y) {
            if (y<=0) {
                return Integer.MAX_VALUE;
            }

            return x/y;
        }

        private int floor2(int x, int y) {
            if (y<=0) {
                return Integer.MAX_VALUE;
            }

            return x/y - ((x%y==0)?1:0);
        }
    }
}
