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
        int mAT;
        int kHP;
        int kAT;
        final int mBuff;
        final int mDebuff;
        HashMap<State, Integer> map;

        int minimumTurn = TURN_IMPOSSILE;

        static final int TURN_MAX = 100;
        static final int TURN_IMPOSSILE = TURN_MAX + 1;

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
            int T = simulate(mHPMax, mAT, kHP, kAT, 0);

            System.out.println(" T:" + T);
            if (T<=TURN_MAX) {
                return String.valueOf(T);
            } else {
                return "IMPOSSIBLE";
            }
        }

        public int simulate(int hp, int att, int knightHP, int knightATT, int turn) {

            if (turn > minimumTurn) {
                return TURN_IMPOSSILE;
            }

            if (knightATT < 0) {
                knightATT = 0;
            }

            //System.out.println("simulate:" + hp + "," + att + "," + knightHP + "," + knightATT + "," + turn);

            State state = new State(hp, att, knightHP, knightATT);

            if (map.containsKey(state)) {
                return map.get(state);
            }

            if (knightHP - att <=0) {
                map.put(state, 1);

                if (1 + turn < minimumTurn) {
                    minimumTurn = 1 + turn;
                }
                return 1;
            }
            map.put(state, TURN_IMPOSSILE);

            int minimum = TURN_IMPOSSILE;
            int visited;

            if (hp - knightATT <= 0) {
                //cure
                hp = mHPMax;
                visited = simulate(hp - knightATT, att, knightHP, knightATT, turn+1);
                minimum = Math.min(minimum, visited+1);
            } else {
                //attack
                visited = simulate(hp - knightATT, att, knightHP - att, knightATT, turn+1);
                minimum = Math.min(minimum, visited+1);
                //buff
                if (mBuff > 0) {
                    visited = simulate(hp - knightATT, att+mBuff, knightHP, knightATT, turn+1);
                    minimum = Math.min(minimum, visited+1);
                }
                //debuff
                if (mDebuff > 0 && knightATT > 0) {
                    knightATT = knightATT - mDebuff;
                    if (knightATT < 0) {
                        knightATT = 0;
                    }

                    visited = simulate(hp - knightATT, att, knightHP, knightATT, turn+1);
                    minimum = Math.min(minimum, visited+1);
                }
            }
            if (minimum + turn < minimumTurn) {
                minimumTurn = minimum + turn;
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
                    if (i%2==0) {
                        hash += states[i];
                    } else {
                        hash -= states[i];
                    }
                }

                return hash;
            }


        }
    }
}
