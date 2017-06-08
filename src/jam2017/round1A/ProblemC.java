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
        ArrayList<Integer> debuffMap;
        DCache dcache = new DCache();

        Game(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            HD = in.nextInt(); AD = in.nextInt(); HK = in.nextInt(); AK = in.nextInt();
            BUFF = in.nextInt(); DEBUFF = in.nextInt();
            debuffMap = new ArrayList<>();
        }

        @Override
        protected String solve() {

            int AB = calcAB(AD, HK, BUFF);
            int Dmax = DEBUFF>0?ceil(AK, DEBUFF):0;

            for (int D=0;D<=Dmax;) {
                debuffMap.add(D);
                int cureInterval = floor(HD-1, AK-D*DEBUFF);
                int targetAttack = floor(HD-1, cureInterval+1);
                int nextD = ceil(AK - targetAttack, DEBUFF);

                //System.out.printf("debuffMap %d %d %d HD:%d AK:%d DEBUFF:%d\n", I, targetAttack, newD, HD, AK, DEBUFF);
                if (AK-D*DEBUFF <= 0 || nextD <= D) {
                    break;
                }

                D = nextD;
            }

            long Tmin = Long.MAX_VALUE;

            long T = 1;
            int d = 0, Hd = HD, Ak = AK;
            for (int D=0;D>=0;) {

                //System.out.printf("T %d d %d Hd %d Ak:%d D:%d\n", T, d, Hd, Ak, D);

                long total = simulate(T, d, Hd, Ak, AB, D);
                if (total>0) {
                    if (total<Tmin) {
                        Tmin = total;
                    }
                }
                D = getNextD(D);

                State saved = dcache.getLargest();
                if (saved != null) {
                    T = saved.T;
                    Ak = saved.Ak;
                    Hd = saved.Hd;
                    d = dcache.getLargestD();
                    //System.out.printf("3 Hd %d Ak %d D %d T %d\n", Hd, Ak, d, T);
                } else {
                    T = 1;
                    Ak = AK;
                    Hd = HD;
                    d = 0;
                }
            }

            String result;
            if (Tmin > 0 && Tmin < Long.MAX_VALUE) {
                result = String.valueOf(Tmin);
            } else {
                result = IMPOSSIBLE;
            }

            return String.format("Case #%d: %s\n", testNumber, result);
        }

        private long simulate(long T, int D, int Hd, int Ak, int AB, int Dmax) {

            //int C=0;

            while (AB>0) {
                if (AB <= 1) {
                    //System.out.printf("C %d D %d CD %d\n", C, Dmax, C+Dmax);
                    return T;
                }

                if (Hd - Ak + (D==Dmax?0:DEBUFF) <= 0) {
                    Hd = HD - Ak;

                    int cureInterval = floor(Hd-1, Ak);

                    if (cureInterval < 1) {
                        return TURN_IMPOSSIBLE;
                    }

                    //C++;


                    if (D == Dmax) {
                        int c = floor2(AB-1, cureInterval);
                        //System.out.printf("C %d AB %d HD %d Ak:%d \n", C, AB, HD, Ak);
                        //C += c;
                        //System.out.printf("C %d D %d CD %d\n", C, Dmax, C+Dmax);
                        return T+AB+c;
                    } else {
                        T++;
                        int Dnext = getNextD(D) - 1;
                        if (Dnext > D) {

                            Dnext = Math.min(Dmax-1, Dnext);

                            int delta = Dnext - D;

                            delta = (delta - delta%cureInterval);

                            if (delta > 0) {

                                int c = floor(delta, cureInterval) ;

                                //System.out.printf("Dnext %d Dmax %d D %d Delta %d C %d DEBUFF %d cureInterval %d", Dnext, Dmax, D, delta, c, DEBUFF, cureInterval);
                                //System.out.printf(" AK %d -> AK %d\n", Ak, Ak - delta * DEBUFF);


                                int Ak_d = Ak - delta * DEBUFF;
                                Ak = Ak_d<0?0:Ak_d;
                                Hd = HD - Ak;
                                D += delta;
                                T += (delta + c);
                                //C += c;
                                dcache.put(D, T, Ak, Hd);

                                //System.out.printf("1 Hd %d Ak %d D %d T %d\n", Hd, Ak, D, T);
                            }
                        }
                    }
                } else {

                    if (D < Dmax) {
                        int d = calcPossibleDebuff(Hd, Ak, Dmax-D);

                        Hd = Hd - (d*Ak - ((d+1) * d * DEBUFF)/2);
                        int Ak_d = Ak - d * DEBUFF;
                        Ak = Ak_d<0?0:Ak_d;
                        D += d;
                        T += d;

                        dcache.put(D, T, Ak, Hd);

                    } else {
                        int ab = floor(Hd-1, Ak);
                        ab = Math.min(AB-1, ab);
                        Hd = Hd - ab*Ak;

                        AB -= ab;
                        T += ab;
                    }

                    if (Hd <= 0) {
                        return TURN_IMPOSSIBLE;
                    }
                }
            }

            return TURN_IMPOSSIBLE;
        }

        private int getNextD(int D) {
            for (int i=0;i<debuffMap.size();i++) {
                int d = debuffMap.get(i);
                if (d > D) {
                    return d;
                }
            }

            return -1;
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


        static class DCache {
            HashMap<Integer, State> saved = new HashMap<>();
            int Dmax = -1;

            void put(int D, long T, int Ak, int Hd) {
                if (saved.containsKey(D)) {
                    return;
                }
                //System.out.printf("4 Hd %d Ak %d D %d T %d\n", Hd, Ak, D, T);

                if (T <1 || Ak < 0 || Hd < 0) {
                    System.out.println("!!!!!!!!");
                    return;
                }

                saved.put(D, new State(T, Ak, Hd));
                if (D > Dmax) {
                    Dmax = D;
                }
            }

            State get(int D) {
                return saved.get(D);
            }

            State getLargest() {
                return saved.get(Dmax);
            }

            int getLargestD() {
                return Dmax;
            }
        }

        static class State {
            long T;
            int Ak, Hd;

            State(long T, int Ak, int Hd) {
                this.T = T;
                this.Ak = Ak;
                this.Hd = Hd;
            }
        }

    }
}
