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
        final int DH;
        final int DA;
        final int KH;
        final int KA;
        final int BF;
        final int DB;

        int mOptA;
        int mOptB;
        int mOptC;
        int mOptD;

        static final int TURN_MAX = 100;
        static final int TURN_IMPOSSIBLE = TURN_MAX + 5;

        Game(InputReader in) {
            DH = in.nextInt();
            DA = in.nextInt();
            KH = in.nextInt();
            KA = in.nextInt();
            BF = in.nextInt();
            DB = in.nextInt();

            findOptimalAB();
            findOptimalCD();
        }

        void findOptimalAB() {
            int minA = TURN_IMPOSSIBLE;
            int minB = TURN_IMPOSSIBLE;
            int minAB = TURN_IMPOSSIBLE;

            for (int B=0;B<minAB;B++) {
                int A = attackCount(KH, DA + BF * B);
                if (A + B < minAB) {
                    minAB = A + B;
                    minA = A;
                    minB = B;
                }

                if (BF <= 0) {
                    break;
                }
            }

            mOptA = minA;
            mOptB = minB;

            System.out.println("findOptimalAB  A:" + minA + " B:" + minB + " BF:" + BF);
        }

        void findOptimalCD() {

            int minCD = 100 - (mOptA + mOptB) + 1;
            int minC = TURN_IMPOSSIBLE;
            int minD = TURN_IMPOSSIBLE;

            int maxTurn = TURN_MAX;

            for (int debuff_limit=0;debuff_limit<minCD;debuff_limit++) {

                int A = 0, B = 0, C = 0, D = 0;

                int dh = DH;
                int da = DA;
                int kh = KH;
                int ka = KA;

                StringBuffer commands = new StringBuffer();

                for (int T=1;T<=maxTurn;T++) {

                    int debuffed_ka = ka;
                    if (D < debuff_limit) {
                        debuffed_ka = ka - DB;
                        if (debuffed_ka < 0) {
                            debuffed_ka = 0;
                        }
                    }

                    if (kh - da <= 0) {
                        A++;
                        kh -= da;
                        commands.append('A');
                    } else if (dh - debuffed_ka <= 0) {
                        //cure
                        C++;
                        dh = DH;
                        commands.append('C');
                    } else {
                        if (D < debuff_limit && ka > 0 && DB > 0) {
                            D++;
                            ka = debuffed_ka;
                            commands.append('D');
                        } else if (B < mOptB) {
                            B++;
                            da += BF;
                            commands.append('B');
                        } else {
                            A++;
                            kh -= da;
                            commands.append('A');
                        }
                    }

                    if (kh <= 0) {
                        System.out.println("[WIN] T:" + T + " A:" + A + " B:" + B + " C:" + C + " D:" + D + " cmds:" + commands);

                        if (C + D < minCD) {
                            minCD = C + D;
                            minC = C;
                            minD = D;
                        }
                        break;
                    } else {
                        // Knight's turn
                        dh -= ka;

                        if (dh <= 0) {
                            System.out.println("[LOST] T:" + T + " cmds:" + commands);
                            break;
                        }
                    }
                }

                if (DB <= 0) {
                    break;
                }
            }

            mOptC = minC;
            mOptD = minD;
            System.out.println("findOptimalCD  C:" + minC + " D:" +  minD);
        }

        int attackCount(int hp, int attack) {

            /*
            int i=0;
            for (i=0;hp>0;i++) {
                hp -= attack;
            }

            return i;
            */

            return hp/attack + (hp%attack>0?1:0);
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
