package round1A;

import jam2017.Problem;

public abstract class Round1A extends Problem {

    @Override
    protected String getSubfolderName() {
        return "round1A/";
    }

    public static void main(String[] args) {
        Problem [] problems = {
                new ProblemA(),
                new ProblemB(),
                new ProblemC(),
        };

        for (Problem prob : problems) {
            prob.solve();
        }
    }
}
