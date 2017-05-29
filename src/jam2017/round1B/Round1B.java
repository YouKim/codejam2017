package jam2017.round1B;

import jam2017.Problem;

public abstract class Round1B extends Problem{

    @Override
    protected String getSubfolderName() {
        return "round1B/";
    }

    public static void main(String[] args) {
        Problem [] problems = {
                //new ProblemA(),
                new ProblemB(),
                //new ProblemC(),
        };

        for (Problem prob : problems) {
            prob.solve();
        }
    }
}
