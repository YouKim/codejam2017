package jam2017.round1A;

import java.io.PrintWriter;
import java.util.ArrayList;

public class ProblemC extends Round1A {

    public ProblemC() {
        mAlpha = "C";
        mTitle = "Play the Dragon";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {
        Game game = new Game(testNumber, in);
        String result = game.solve();

        System.out.printf("Case #%d: %s\n", testNumber, result);
        out.printf("Case #%d: %s\n", testNumber, result);
    }

    static class Game {
        static final String IMPOSSIBLE = "IMPOSSIBLE";
        static final int TURN_IMPOSSIBLE = -1;

        final int TN, HD, AD, HK, AK, BUFF, DEBUFF;

        Game(int testCNumber, InputReader in) {
            TN = testCNumber;
            HD = in.nextInt(); AD = in.nextInt(); HK = in.nextInt(); AK = in.nextInt();
            BUFF = in.nextInt(); DEBUFF = in.nextInt();
        }

        private String solve() {

            int AB = calcAB(AD, HK, BUFF);
            int Dmax = DEBUFF>0?ceil(AK, DEBUFF):0;

            ArrayList<Integer> debuffMap = new ArrayList<>();
            for (int D=0;D<=Dmax;) {
                debuffMap.add(D);
                int cureInterval = floor(HD-1, AK-D*DEBUFF);
                int targetAttack = floor(HD-1, cureInterval+1);
                int nextD = ceil(AK - targetAttack, DEBUFF);

                //System.out.printf("debuffMap %d %d %d HD:%d AK:%d DEBUFF:%d\n", I, targetAttack, newD, HD, AK, DEBUFF);
                if (nextD <= D) {
                    break;
                }

                D = nextD;
            }

            int Tmin = Integer.MAX_VALUE;

            for (int i=0;i<debuffMap.size();i++) {
                int D = debuffMap.get(i);
                int T = simulate(HD, AK, AB, D);
                if (T>0 && T<Tmin) {
                    Tmin = T;
                }
            }

            if (Tmin > 0 && Tmin < Integer.MAX_VALUE) {
                return String.valueOf(Tmin);
            } else {
                return IMPOSSIBLE;
            }
        }

        private int simulate(int Hd, int Ak, int AB, int D) {

            for (int T=1;AB>0;) {
                if (AB <= 1) {
                    return T;
                }

                if (Hd - Ak + (D>0?DEBUFF:0) <= 0) {
                    Hd = HD - Ak;

                    int cureInterval = floor(HD-1, Ak);

                    if (cureInterval < 2) {
                        return TURN_IMPOSSIBLE;
                    }
                    T++;
                } else {

                    if (D > 0) {
                        int left = calcPossibleDebuff(Hd, Ak);
                        left = Math.min(D, left);
                        D-=left;
                        //Hd = Hd - left*Ak + DEBUFF;
                        Hd = Hd - (left*Ak - ((left+1) * left * DEBUFF)/2);
                        Ak -= (left * DEBUFF);
                        T+=left;
                    } else {
                        int left = floor(Hd-1, Ak);
                        left = Math.min(AB-1, left);
                        AB -= left;
                        Hd = Hd - left*Ak;
                        T+=left;
                    }

                    if (Hd <= 0) {
                        return TURN_IMPOSSIBLE;
                    }
                }
            }

            return TURN_IMPOSSIBLE;
        }

        private int calcAB(int Ad, final int Hk, final int Buff) {
            if (Buff <= 0) {
                return ceil(Hk, Ad);
            } else {
                double result = (Math.sqrt(Hk) * Math.sqrt(Buff) - Ad ) / Buff;
                int start = (result < 0)?0:((int) result);

                int minAB = Integer.MAX_VALUE;
                for (int B=start;B<Hk;B++) {
                    int AB = ceil(Hk, Ad + B*Buff) + B;
                    if (AB < minAB) {
                        minAB = AB;
                    } else if (AB > minAB) {
                        break;
                    }
                }

                return minAB;
            }
        }


        private int calcPossibleDebuff(int Hd, int Ak) {
            if (Ak-DEBUFF <= 0) {
                return 1;
            }

            int start = floor(Hd-1, Ak-DEBUFF);
            int end = ceil(AK, DEBUFF);

            int result = start;

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

    }
}
