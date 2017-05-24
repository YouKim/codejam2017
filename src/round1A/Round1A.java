package round1A;

public class Round1A {
    public static void main(String[] args) {
        Problem [] problems = {
                //new ProblemA(),
                //new ProblemB(),
                new ProblemC(),
        };

        for (Problem prob : problems) {
            prob.solve();
        }
    }
}
