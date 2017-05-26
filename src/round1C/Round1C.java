package round1C;

public class Round1C {
    public static void main(String[] args) {
        Problem [] problems = {
                new ProblemA(),
                //new ProblemB(),
                //new ProblemC(),
        };

        for (Problem prob : problems) {
            prob.solve();
        }
    }
}
