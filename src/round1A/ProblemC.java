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
        game.print();
    }

    static class Game {
        int hp;
        int att;
        int knightHP;
        int knightATT;
        int buff;
        int debuff;

        Game(InputReader in) {
            hp = in.nextInt();
            att = in.nextInt();
            knightHP = in.nextInt();
            knightATT = in.nextInt();
            buff = in.nextInt();
            debuff = in.nextInt();
        }

        void print() {
            System.out.println("H:" + hp + " A:" + att + " KH:" + knightHP + " KA:" + knightATT + " B:" + buff + " DB:" + debuff);
        }
    }
}
