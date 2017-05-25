package round1A;

import java.io.PrintWriter;

public class ProblemC extends Problem {

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
        final long mHD, mAD, mHK, mAK, mB, mD;
        int mOptA, mOptB, mOptC, mOptD;

        static final int TURN_MAX = 100;
        static final int TURN_IMPOSSIBLE = TURN_MAX + 5;

        Game(InputReader in) {
            mHD = in.nextInt();
            mAD = in.nextInt();
            mHK = in.nextInt();
            mAK = in.nextInt();
            mB = in.nextInt();
            mD = in.nextInt();

            findOptimalAB();
            findOptimalCD();
        }

        void findOptimalAB() {

            if (mB <= 0) {
                mOptA = attackCount(mHK, mAD);
                mOptB = 0;

                System.out.println("findOptimalAB  A:" + mOptA + " " + mHK + "(HP) " + mAD + "X" + mOptA + "=" + (mAD*mOptA));
            } else {
                int minA = TURN_IMPOSSIBLE;
                int minB = TURN_IMPOSSIBLE;
                int minAB = TURN_MAX;

                for (int B=0;B<minAB;B++) {
                    int A = attackCount(mHK, mAD + mB * B);
                    if (A + B < minAB) {
                        minAB = A + B;
                        minA = A;
                        minB = B;
                    }
                }

                mOptA = minA;
                mOptB = minB;

                System.out.println("findOptimalAB  A:" + mOptA + " B:" + mOptB  + " " + mHK + "(HP) " + (mAD+mB*mOptB) + "X" + mOptA + "=" + ((mAD+mB*mOptB)*mOptA));
            }
        }

        void findOptimalCD() {

            int minCD = 100 - (mOptA + mOptB) + 1;
            int minC = TURN_IMPOSSIBLE;
            int minD = TURN_IMPOSSIBLE;

            int maxTurn = TURN_MAX;

            for (int debuff_limit=0;debuff_limit<minCD;debuff_limit++) {

                long hd = mHD, ad = mAD, hk = mHK, ak = mAK;
                int A = 0, B = 0, C = 0, D = 0;

                StringBuffer commands = new StringBuffer();
                System.out.println("T:" + 0 + " HP:" + hd + " DA:" + ad + " KH:" + hk + " KA:" + ak + " B:" + mB + " DB:" + mD);

                for (int T=1;T<=maxTurn;T++) {

                    long ak_debuffed = ak;
                    if (D < debuff_limit) {
                        ak_debuffed = ak - mD;
                        if (ak_debuffed < 0) {
                            ak_debuffed = 0;
                        }
                    }

                    if (hk - ad <= 0) {
                        A++;
                        hk -= ad;
                        commands.append('A');
                    } else if (hd - ak_debuffed <= 0) {
                        //cure
                        C++;
                        hd = mHD;
                        commands.append('C');
                    } else {
                        if (D < debuff_limit && ak > 0 && mD > 0) {
                            D++;
                            ak = ak_debuffed;
                            commands.append('D');
                        } else if (B < mOptB) {
                            B++;
                            ad += mB;
                            commands.append('B');
                        } else {
                            A++;
                            hk -= ad;
                            commands.append('A');
                        }
                    }

                    if (hk <= 0) {
                        System.out.println("[WIN] T:" + T + " A:" + A + " B:" + B + " C:" + C + " D:" + D + " cmds:" + commands);
                        if (C + D < minCD) {
                            minCD = C + D;
                            minC = C;
                            minD = D;
                        }
                        break;
                    } else {
                        // Knight's turn.
                        hd -= ak;

                        if (hd <= 0) {
                            System.out.println("[LOST] T:" + T + " cmds:" + commands);
                            break;
                        }

                        if (commands.toString().endsWith("CC")) {
                            System.out.println("[IMPOSIBLE] CC");
                            break;
                        }

                        if (mOptA+mOptB+C+D > TURN_MAX) {
                            System.out.println("[BUSTED] A+B+C+D > 100");
                            break;
                        }
                    }

                    System.out.println("T:" + T + " HP:" + hd + " DA:" + ad + " KH:" + hk + " KA:" + ak + " cmds:" + commands);
                }

                if (mD <= 0) {
                    System.out.println("DEBUFF is 0, no more simulation");
                    break;
                }
            }

            mOptC = minC;
            mOptD = minD;
            System.out.println("findOptimalCD  C:" + minC + " D:" +  minD);
        }

        int attackCount(long hp, long attack) {
            long result = hp/attack + (hp%attack>0?1:0);

            if (result > TURN_MAX) {
                return TURN_MAX;
            }

            return (int) result;
        }

        String solve() {
            int T = mOptA + mOptB + mOptC + mOptD;

            if (T<=TURN_MAX) {
                return String.valueOf(T);
            } else {
                return "IMPOSSIBLE";
            }
        }
    }
}
