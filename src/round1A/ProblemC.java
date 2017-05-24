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

        int minimumTurn = TURN_IMPOSSIBLE;

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

            System.out.println(" T:" + T);
            if (T<=TURN_MAX) {
                return String.valueOf(T);
            } else {
                return "IMPOSSIBLE";
            }
        }

        public int simulate(int hp, int att, int knightHP, int knightATT, String commands) {

            if (commands.length() > minimumTurn) {
                return TURN_IMPOSSIBLE;
            }

            if (hp <= 0) {
                return TURN_IMPOSSIBLE;
            }

            //System.out.println("simulate:" + hp + "," + att + "," + knightHP + "," + knightATT + "," + turn);

            State state = new State(hp, att, knightHP, knightATT);

            if (map.containsKey(state)) {
                return map.get(state);
            }

            if (knightHP - att <=0) {
                map.put(state, 1);
                System.out.println(commands + 'A' + ":1." + " ~ " + commands.length());

                if (commands.length() + 1 < minimumTurn) {
                    minimumTurn = commands.length() + 1;
                }

                return 1;
            }

            map.put(state, TURN_IMPOSSIBLE);

            int minimum = TURN_IMPOSSIBLE;
            int visited;

            String history = commands;


            if (hp <= knightATT) {
                visited = simulate(mHPMax - knightATT, att, knightHP, knightATT, commands + 'C') + 1;
                if (visited < minimum) {
                    minimum = visited;
                    history = commands + 'C';
                }
            } else {

                //attack
                visited = simulate(hp - knightATT, att, knightHP - att, knightATT, commands + 'A') + 1;
                if (visited < minimum) {
                    minimum = visited;
                    history = commands + 'A';
                }

                //buff
                if (mBuff > 0) {
                    visited = simulate(hp - knightATT, att+mBuff, knightHP, knightATT, commands + 'B') + 1;
                    if (visited < minimum) {
                        minimum = visited;
                        history = commands + 'B';
                    }
                }


                //debuff
                if (mDebuff > 0 && knightATT > 0) {
                    int debuffed = knightATT - mDebuff;
                    if (debuffed < 0) {
                        debuffed = 0;
                    }

                    visited = simulate(hp - debuffed, att, knightHP, debuffed, commands + 'C') + 1;
                    if (visited < minimum) {
                        minimum = visited;
                        history = commands + 'D';
                    }
                }
            }

            if (minimum <= TURN_MAX) {
                System.out.println(history + ":" + minimum);
            }

            map.put(state, minimum);
            return minimum;
        }

        class State {
            public int [] states;

            State(int... values) {
                states = new int[values.length];

                int i=0;
                for(int val:values){
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
