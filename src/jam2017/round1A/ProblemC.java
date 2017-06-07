package jam2017.round1A;

import java.io.PrintWriter;

public class ProblemC extends Round1A {

    public ProblemC() {
        mAlpha = "C";
        mTitle = "Play the Dragon";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {
        Game game = new Game(in);
        String result = game.solve();

        System.out.printf("Case #%d: %s\n", testNumber, result);
        out.printf("Case #%d: %s\n", testNumber, result);
    }

    static class Game {
        static final String IMPOSSIBLE = "IMPOSSIBLE";
        static final int TURN_IMPOSSIBLE = Integer.MAX_VALUE;

        InputReader in;

        Game(InputReader in) {
            this.in = in;
        }

        String solve() {
            final int Hd = in.nextInt(), Ad = in.nextInt(), Hk = in.nextInt(), Ak = in.nextInt();
            final int Buff = in.nextInt(), Debuff = in.nextInt();

            int AB = calcAB(Ad, Hk, Buff);

            return IMPOSSIBLE;
        }


        int calcAB(int Ad, final int Hk, final int Buff) {
            if (Buff <= 0) {
                return ceil(Hk, Ad);
            } else {
                double result = (Math.sqrt(Hk) * Math.sqrt(Buff) - Ad ) / Buff;
                //System.out.println("findMinAB by derivative:" + result);

                int start = (result < 0)?0:((int) result);

                int minAB = TURN_IMPOSSIBLE;
                for (int B=start;B<Hk;B++) {
                    int AB = ceil(Hk, Ad + B*Buff) + B;
                    //System.out.println("calcAB B:" + B + " A+B:" + AB);

                    if (AB < minAB) {
                        minAB = AB;
                    } else if (AB > minAB) {
                        break;
                    }
                }

                System.out.println("calcAB :" + minAB);
                return minAB;
            }
        }

        int ceil(int X, int Y) {
            if (Y<=0) {
                return Integer.MAX_VALUE;
            }

            return X/Y + (X%Y>0?1:0);
        }

        int floor(int X, int Y) {
            if (Y<=0) {
                return Integer.MAX_VALUE;
            }

            return X/Y;
        }

        /*
        public String solve() {
            // TODO Auto-generated method stub

            int minT = Integer.MAX_VALUE;


            if (mDmax == 0) {
                minT = simulateD(0);//sumulateNoDebuff();
            } else {
                int D;
                for (D = 0 ;D <= mDmax;D++) {
                    int T = simulateD(D);

                    if (T < minT) {
                        minT = T;
                    }
                }
            }


            if (minT<Integer.MAX_VALUE) {
                return String.valueOf(minT);
            } else {
                return IMPOSSIBLE;
            }
        }


        static int Ceil(int A, int B) {
            if (B<=0) {
                return 0;
            }

            return A/B + (A%B>0?1:0);
        }

        static int Floor(int A, int B) {
            if (B<=0) {
                return 0;
            }

            return A/B - (A%B>0?0:1);
        }

        int sumulateNoDebuff() {
            int Cint = Floor(mHd, mAk);

            int C;

            if (Cint == 0) {
                System.out.println("C == 0");
                C = 0;
            } else if (Cint == 1 && mAB > 2) {
                System.out.println("Cint == 1");
                return TURN_IMPOSSIBLE;
            } else {
                C = Floor(mAB, Cint+1);
            }

            System.out.println("mAB:" + mAB + " C:" + C + " Cint:" + Cint);
            int total = mAB + C;
            return total;
        }

        int simulateD(int Dcnt) {

            int AB = mAB;
            int D = 0;
            int Ak = mAk;
            int Hd = mHd;
            //int C = 0;
            boolean cure = false;

            for (int T = 1; true ; T++) {
                // Final Blow
                if (AB <= 1) {
                    System.out.println("[WIN] T:" + T);
                    return T;
                }


                int Ak_d = Ak;
                if (D < Dcnt) {
                    Ak_d = Ak - mD;
                    if (Ak_d < 0) {
                        Ak_d = 0;
                    }
                }

                if (Hd - Ak_d <= 0) {
                    // cure
                    //C++;
                    Hd = mHd;
                    if (cure) {
                        return TURN_IMPOSSIBLE;
                    }

                    cure = true;


                    if (D >= Dcnt) {
                        int Cint = Floor(mHd, Ak);

                        int C;

                        if (Cint == 0) {
                            C = 0;
                        } else if (Cint == 1) {
                            return TURN_IMPOSSIBLE;
                        } else {
                            C = Floor(AB, Cint-1);
                        }
                        int total = T + AB + C;
                        return total;
                    }

                } else {
                    cure = false;
                    if (D < Dcnt) {
                        D++;
                        Ak = Ak_d;
                    } else {
                        AB--;
                    }
                }

                // Knight's turn.
                Hd -= Ak;
                if (Hd <= 0) {
                    System.out.println("[LOST] T:" + T);
                    break;
                }
            }

            return TURN_IMPOSSIBLE;
        }

        */
        /*

        // Maximum value will be 100 in small or 10^9 in large
        int findMinAB() {
            if (mB <= 0) {
                return Ceil(mHk, mAd);
            } else {
                long minAB = TURN_IMPOSSIBLE;
                int B;

                for (B=0;B<mHk;B++) {
                    long A = Ceil(mHk, mAd + mB * B);
                    if (A + B < minAB) {
                        minAB = A + B;
                    } else {
                        break;
                    }
                }
                System.out.println("B:"+B);
                return minAB;
            }
        }

        long attackCount(long hp, long attack) {
            return hp/attack + (hp%attack>0?1:0);
        }

        long findDebuffLimit() {
            if (mD <= 0) {
                return 0;
            } else {
                long result = mAk/mD + (mAk%mD>0?1:0);
                return result;
            }
        }

        long cureInterval(long hp, long attack) {
            if (attack <= 0) {
                return Integer.MAX_VALUE;
            }
            return hp/attack - (hp%attack>0?0:1);
        }

        long cureCount(long cureInterval, long total) {

            return total/cureInterval - (total%cureInterval>0?0:1);
        }

        int cureTurn(int turn, int interval) {
            return turn/interval - (turn%interval>0?0:1);
        }

        // Check if 1st turn is cure;
        boolean isImpossible() {
            return ((mHk - mAd > 0) && (mHd - (mAk - mD)) <= 0);
        }

        /*
        int simulate(int BuffLimit, int DebuffLimit) {

            int Hd = mHd, Ad = mAd, Hk = mHk, Ak = mAk;
            int A = 0, B = 0, C = 0, D = 0;

            StringBuffer commands = new StringBuffer();
            System.out.println("T:"+0+" Hd:"+Hd+" Ad:"+Ad+" Hk:"+Hk+" Ak:"+Ak+" B:"+mB+" D:"+mD+" BL:"+BuffLimit+" DL"+DebuffLimit);

            for (int T = 1; T < mTurnMax; T++) {
                // Final Blow
                if (Hk - Ad <= 0) {
                    A++;
                    //Hk -= Ad;
                    commands.append('A');
                    System.out.println("[WIN] T:"+T+" A:"+A+" B:"+B+" C:"+C+" D:"+D+" cmds:"+commands);
                    return T;
                }

                int Ak_d = Ak;
                if (D < DebuffLimit) {
                    Ak_d = Ak - mD;
                    if (Ak_d < 0) {
                        Ak_d = 0;
                    }
                }

                if (Hd - Ak_d <= 0) {
                    // cure
                    C++;
                    Hd = mHd;
                    commands.append('C');
                } else {
                    if (D < DebuffLimit && Ak > 0 && mD > 0) {
                        D++;
                        Ak = Ak_d;
                        commands.append('D');
                    } else if (B < BuffLimit) {
                        B++;
                        Ad += mB;
                        commands.append('B');
                    } else {
                        A++;
                        Hk -= Ad;
                        commands.append('A');
                    }
                }

                // Knight's turn.
                Hd -= Ak;
                if (Hd <= 0) {
                    System.out.println("[LOST] T:" + T + " cmds:" + commands);
                    break;
                }

                if (commands.toString().endsWith("CC")) {
                    System.out.println("[IMPOSSIBLE] CC");
                    break;
                }

                // Log Pumping!!
                //System.out.println("T:"+T+" Hd:"+Hd+" Ad:"+Ad+" Hk:"+Hk+" Ak:"+Ak+" cmds:"+ commands);
            }

            return TURN_IMPOSSIBLE;
        }




        int simulate(int D, int AB) {
            return 0;
        }

        String solve() {
            int Tmin = TURN_IMPOSSIBLE;
            if (!isImpossible()) {
                long AB = findMinAB();

                System.out.println("AB"+AB);

                if (mD > 0) {
                    long limit = mAk/mD + (mAk%mD>0?1:0);

                    long minCure = Integer.MAX_VALUE;
                    System.out.println("limit:"+limit);
                    for (long D=limit;D>=0;) {
                        long I = cureInterval(mHd, mAk-D*mD);

                        if (I == 0) {
                            D--;
                            continue;
                        }

                        long T = cureCount(I, D+AB);
                        System.out.println("I:"+I + " D:" + D);
                        System.out.println("T:"+(T+D+AB));

                        //System.out.println("I:"+I + " D:" + D);
                        //System.out.println("mHd:"+mHd + " mAk:" + mAk + " mD:" + mD);
                        if (I<=minCure && I > 1) {

                            minCure = I;

                            long targetAttack = mHd/(I-1) + (mHd%(I-1)>0?1:0);



                            long newD = (mAk - targetAttack)/mD + (mAk%targetAttack>0?1:0);

                            if (newD < D) {
                                D = newD;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    /*
                    for (int D=0;D<=limit;D++) {

                        int I = cureInterval(mHd, mAk-D*mD);
                        System.out.println("I:"+I + " D:" + D);
                        if (I>minCure) {

                            minCure = I;
                            int T = simulate(mOptB, D);

                            if (T < Tmin) {
                                Tmin = T;
                            }


                            D = (mAk - mHd / (I+1)) * mD;
                        }
                    }
                    */
        /*
                } else {
                    Tmin = simulate(mOptB, 0);
                }
            }

            if (Tmin<TURN_IMPOSSIBLE) {
                return String.valueOf(Tmin);
            } else {
                return IMPOSSIBLE;
            }
        }
        */
    }
}
