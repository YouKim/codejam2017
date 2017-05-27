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
        // Targeted to large 10^9
        static final int ACTION_MAX = 1000000000;
        static final int TURN_MAX = 4*ACTION_MAX;  //AB combo <= 10^9 + D <=10^9 + C can interleave all action.
        static final int TURN_IMPOSSIBLE = Integer.MAX_VALUE;

        final int mHd, mAd, mHk, mAk, mB, mD;
        int minimumAB = TURN_IMPOSSIBLE;
        int minimumWin = TURN_IMPOSSIBLE;

        Game(InputReader in) {
            mHd = in.nextInt();
            mAd = in.nextInt();
            mHk = in.nextInt();
            mAk = in.nextInt();
            mB = in.nextInt();
            mD = in.nextInt();
        }

        // Maximum value will be 100 in small or 10^9 in large
        int findOptimalBuff() {
            if (mB <= 0) {
                minimumAB = attackCount(mHk, mAd);
                System.out.println("Minimal Attack & Buff combo:" + minimumAB);
                return 0;
            } else {
                int optB = TURN_IMPOSSIBLE;
                int minAB = ACTION_MAX;

                for (int B=0;B<minAB;B++) {
                    int A = attackCount(mHk, mAd + mB * B);
                    if (A + B < minAB) {
                        minAB = A + B;
                        optB = B;
                    }
                }
                minimumAB = minAB;
                System.out.println("Minimal Attack & Buff combo:" + minimumAB);
                return optB;
            }
        }

        int attackCount(int hp, int attack) {
            return hp/attack + (hp%attack>0?1:0);
        }

        int findDebuffLimit() {
            if (mD <= 0) {
                return 0;
            } else {
                int result = mAk/mD + (mAk%mD>0?1:0);
                return result;
            }
        }

        // Check if 1st turn is cure;
        boolean isImpossible() {
            return ((mHk - mAd > 0) && (mHd - (mAk - mD)) < 0);
        }

        int simulate(int BuffLimit, int DebuffLimit) {

            int Hd = mHd, Ad = mAd, Hk = mHk, Ak = mAk;
            int A = 0, B = 0, C = 0, D = 0;

            StringBuffer commands = new StringBuffer();
            System.out.println("T:"+0+" Hd:"+Hd+" Ad:"+Ad+" Hk:"+Hk+" Ak:"+Ak+" B:"+mB+" D:"+mD+" BL:"+BuffLimit+" DL"+DebuffLimit);

            for (int T = 1; T < minimumWin; T++) {
                // Final Blow
                if (Hk - Ad <= 0) {
                    A++;
                    //Hk -= Ad;
                    commands.append('A');
                    System.out.println("[WIN] T:"+T+" A:"+A+" B:"+B+" C:"+C+" D:"+D+" cmds:"+commands);
                    minimumWin = T;
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

        String solve() {
            int Tmin = TURN_IMPOSSIBLE;
            if (!isImpossible()) {
                int B = findOptimalBuff();
                int dLimit = findDebuffLimit();
                for (int D=0;D<=dLimit;D++) {
                    int T = simulate(B, D);
                    Tmin = Math.min(Tmin, T);
                }
            }

            if (Tmin<TURN_IMPOSSIBLE) {
                return String.valueOf(Tmin);
            } else {
                return "IMPOSSIBLE";
            }
        }
    }
}
