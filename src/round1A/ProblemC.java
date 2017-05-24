package round1A;

import java.io.PrintWriter;
import java.util.HashMap;

public class ProblemC extends Problem {

    public ProblemC() {
        mAlpha = "C";
        mTitle = "Play the Dragon";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {
        Game game = new Game(in);
        //game.print();
        String result = game.solve();
        System.out.printf("Case #%d: %s\n", testNumber, result);
        out.printf("Case #%d: %s\n", testNumber, result);
    }

    static class Game {
        final int mHPMax;
        final int mAT;
        final int kHP;
        final int kAT;
        final int mBuff;
        final int mDebuff;
        HashMap<State, Integer> map;

        static final int TURN_MAX = 100;
        static final int TURN_IMPOSSIBLE = TURN_MAX + 5;

        Game(InputReader in) {
            mHPMax = in.nextInt();
            mAT = in.nextInt();
            kHP = in.nextInt();
            kAT = in.nextInt();
            mBuff = in.nextInt();
            mDebuff = in.nextInt();

            map = new HashMap<>();
        }

        String solve() {
            int T = simulate(mHPMax, mAT, kHP, kAT, "");

            if (T<=TURN_MAX) {
                return String.valueOf(T);
            } else {
                return "IMPOSSIBLE";
            }
        }

        public int simulate(int hp, int att, int knightHP, int knightATT, String commands) {

            //System.out.println("simulate:" + hp + "," + att + "," + knightHP + "," + knightATT + "," + commands);

            State state = new State(hp, att, knightHP, knightATT);

            Integer key = map.get(state);
            if (key != null) {
                int result = key;

                //System.out.println("contain:" + result);
                return result;
            }

            map.put(state, TURN_IMPOSSIBLE);

            if (hp <= 0) {
                return TURN_IMPOSSIBLE;
            }

            if (knightHP - att <=0) {
                map.put(state, 1);
                return 1;
            }


            int minimum = TURN_IMPOSSIBLE;
            int visited;

            int debuffed = knightATT - mDebuff;
            if (debuffed < 0) {
                debuffed = 0;
            }

            if (hp - debuffed <= 0) {
                //cure
                visited = simulate(mHPMax - knightATT, att, knightHP, knightATT, commands + 'C') + 1;
                if (visited < minimum) {
                    minimum = visited;
                }
            } else {

                // debuff
                if (mDebuff > 0 && knightATT > 0 && !commands.contains("B") && !commands.contains("A") ) {
                    visited = simulate(hp - debuffed, att, knightHP, debuffed, commands + 'D') + 1;
                    if (visited < minimum) {
                        minimum = visited;
                    }
                }

                //buff
                if (mBuff > 0 && !commands.contains("A")) {
                    visited = simulate(hp - knightATT, att+mBuff, knightHP, knightATT, commands + 'B') + 1;
                    if (visited < minimum) {
                        minimum = visited;
                    }
                }

                //attack
                visited = simulate(hp - knightATT, att, knightHP - att, knightATT, commands + 'A') + 1;
                if (visited < minimum) {
                    minimum = visited;
                }



            }

            map.put(state, minimum);
            return minimum;
        }

        class State {
            public int [] states;

            State(int... values) {
                states = new int[values.length];

                int i=0;
                for (int val:values){
                    states[i++] = val;
                }
            }

            @Override
            public boolean equals(Object object) {

                if (object instanceof State) {
                    State target = (State) object;
                    int length = states.length;

                    if (target.states.length == length) {
                        for (int i=0;i<length;i++) {
                            if (target.states[i] != states[i]) {
                                return false;
                            }
                        }

                        return true;
                    }
                }

                return false;
            }

            @Override
            public int hashCode() {
                int hash = 0;
                for (int i=0;i<states.length;i++) {
                    hash += states[i];
                }

                return hash;
            }


        }
    }
}
