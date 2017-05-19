package qualificationRound;

public class Main {
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
