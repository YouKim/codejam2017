package jam2017.round1C;

import jam2017.Problem;

public abstract class Round1C extends Problem {

    @Override
    protected String getSubfolderName() {
        return "round1C";
    }

    public static void main(String[] args) {
        Problem [] problems = {
                new ProblemA(),
                new ProblemB(),
                //new ProblemC(),
        };

        for (Problem prob : problems) {
            prob.solve();
        }
    }
}
