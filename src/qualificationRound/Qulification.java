package qualificationRound;

import jam2017.Problem;

public abstract class Qulification extends Problem {

    @Override
    protected String getSubfolderName() {
        return "qualification/";
    }

    public static void main(String[] args) {
        Problem [] problems = {
                new ProblemA(),
                new ProblemB(),
                new ProblemC(),
                new ProblemD(),
        };

        for (Problem prob : problems) {
            prob.solve();
        }
    }
}
