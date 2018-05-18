package jam2017.round1A;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.junit.Test;

import jam2017.Problem;

public class ProblemB extends Round1A {

    public ProblemB() {
        mAlpha = "B";
        mTitle = "Ratatouille";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new Recipe(in, testNumber, result);
    }

    static class Recipe extends TestCase {
        int mIngr;
        int mPack;

        int mRecipe[];
        ArrayList<Pack> mPacks;

        public Recipe(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            mIngr = in.nextInt();
            mPack = in.nextInt();

            mRecipe = new int[mIngr];
            mPacks = new ArrayList<>();

            for (int i=0;i<mIngr;i++) {
                mRecipe[i] = in.nextInt();
            }

            for (int i=0;i<mIngr;i++) {
                for (int j=0;j<mPack;j++) {
                    int gram = in.nextInt();
                    Pack pack = new Pack(i, gram, mRecipe[i]);
                    if (!pack.isInvalid) {
                        mPacks.add(pack);
                    }
                }
            }
        }

        @Override
        protected String solve() {

            Collections.sort(mPacks, new Comparator<Pack>() {
                @Override
                public int compare(Pack o1, Pack o2) {
                    return o1.maxServ - o2.maxServ;
                }
            });

            int made = 0;
            while (mPacks.size()>= mIngr) {
                Pack pack = mPacks.remove(0);
                if (pack.makeKit(mPacks)) {
                    made++;
                }
            }

            return String.format("Case #%d: %s\n", testNumber, made);
        }

        public class Pack {
            int index;
            int minServ;
            int maxServ;
            boolean isInvalid;

            Pack(int index, int weight, int recipe) {
                this.index = index;

                double max = (weight * 10.0) / (recipe * 9.0);
                double min = (weight * 10.0) / (recipe * 11.0);

                minServ = (int) Math.ceil(min);
                maxServ = (int) Math.floor(max);

                isInvalid = maxServ < minServ || (maxServ == 0);
            }

            public boolean makeKit(ArrayList<Pack> list) {
                int required = mIngr;
                Pack [] templet = new Pack[mIngr];

                templet[index] = this;
                required--;

                if (required > 0) {
                    Iterator<Pack> iter = list.iterator();
                    while (iter.hasNext() && required > 0) {
                        Pack pack = iter.next();
                        if (templet[pack.index] == null) {
                            if (hasSameRange(pack)) {
                                templet[pack.index] = pack;
                                required--;
                            }
                        }
                    }
                }

                if (required == 0) {
                    for (Pack pack:templet) {
                        list.remove(pack);
                    }
                    return true;
                }

                return false;
            }

            public boolean hasSameRange(Pack target) {
                int min = (minServ > target.minServ)?minServ:target.minServ;
                int max = (maxServ < target.maxServ)?maxServ:target.maxServ;

                return (max - min + 1 > 0);
            }
        }
    }

    @Override
    protected String getSampleInput() {
        return "6\n" +
                "2 1\n" +
                "500 300\n" +
                "900\n" +
                "660\n" +
                "2 1\n" +
                "500 300\n" +
                "1500\n" +
                "809\n" +
                "2 2\n" +
                "50 100\n" +
                "450 449\n" +
                "1100 1101\n" +
                "2 1\n" +
                "500 300\n" +
                "300\n" +
                "500\n" +
                "1 8\n" +
                "10\n" +
                "11 13 17 11 16 14 12 18\n" +
                "3 3\n" +
                "70 80 90\n" +
                "1260 1500 700\n" +
                "800 1440 1600\n" +
                "1700 1620 900";
    }

    @Override
    protected String getSampleOutput() {
        return "Case #1: 1\n" +
                "Case #2: 0\n" +
                "Case #3: 1\n" +
                "Case #4: 0\n" +
                "Case #5: 3\n" +
                "Case #6: 3";
    }

    public static void main(String[] args) {
        Problem problem = new ProblemB();
        problem.solve();
    }

    @Test
    public void testSample() {
        Problem problem = new ProblemB();
        boolean result = problem.test();

        assertTrue(result);
    }
}
