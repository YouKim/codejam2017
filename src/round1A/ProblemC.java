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
        static final int TURN_MAX = 100;
        static final int TURN_IMPOSSIBLE = TURN_MAX + 5;

        final int mHd, mAd, mHk, mAk, mB, mD;

        Game(InputReader in) {
            mHd = in.nextInt();
            mAd = in.nextInt();
            mHk = in.nextInt();
            mAk = in.nextInt();
            mB = in.nextInt();
            mD = in.nextInt();
        }

        int findOptimalBuff() {

            if (mB <= 0) {
                return 0;
            } else {
                int optB = TURN_IMPOSSIBLE;
                int minAB = TURN_IMPOSSIBLE;

                for (int B=0;B<minAB;B++) {
                    int A = attackCount(mHk, mAd + mB * B);
                    if (A + B < minAB) {
                        minAB = A + B;
                        optB = B;
                    }
                }

                return optB;
            }
        }

        int attackCount(int hp, int attack) {
            int result = hp/attack + (hp%attack>0?1:0);

            if (result > TURN_MAX) {
                return TURN_IMPOSSIBLE;
            }

            return result;
        }

        int findDebuffLimit() {
            if (mD <= 0) {
                return 0;
            } else {
                int result = mAk/mD + (mAk%mD>0?1:0);
                if (result > TURN_MAX) {
                    return TURN_MAX;
                }
                return result;
            }
        }

        int simulate(int BuffLimit, int DebuffLimit) {

            int Hd = mHd, Ad = mAd, Hk = mHk, Ak = mAk;
            int A = 0, B = 0, C = 0, D = 0;

            StringBuffer commands = new StringBuffer();
            System.out.println("T:"+0+" HP:"+Hd+" DA:"+Ad+" KH:"+Hk+" KA:"+Ak+" B:"+mB+" DB:"+ mD);

            for (int T = 1; T <= TURN_MAX; T++) {
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

                //System.out.println("T:"+T+" HP:"+Hd+" DA:"+Ad+" KH:"+Hk+" KA:"+Ak+" cmds:"+ commands);
            }

            return TURN_IMPOSSIBLE;
        }

        String solve() {
            int B = findOptimalBuff();
            int Tmin = TURN_IMPOSSIBLE;

            if (B < TURN_MAX) {
                int dLimit = findDebuffLimit();
                for (int D=0;D<=dLimit;D++) {
                    int T = simulate(B, D);
                    Tmin = Math.min(Tmin, T);
                }
            }

            if (Tmin<=TURN_MAX) {
                return String.valueOf(Tmin);
            } else {
                return "IMPOSSIBLE";
            }
        }
    }
}
