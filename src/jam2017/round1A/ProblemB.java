package jam2017.round1A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ProblemB extends Round1A {

    public ProblemB() {
        mAlpha = "B";
        mTitle = "Ratatouille";
    }

    @Override
    protected List<TestCase> createTestCase(int testCount, InputReader in, StringBuffer [] results) {

        List<TestCase> tcs = new ArrayList<>();

        for (int i=1;i<=testCount;i++) {
            int ingredients = in.nextInt();
            int packages = in.nextInt();

            Recipe recipe = new Recipe(ingredients, packages, in, i, results[i]);
            tcs.add(recipe);
        }

        return tcs;
    }

    static class Recipe extends TestCase {
        int mIngr;
        int mPack;

        int mRecipe[];
        ArrayList<Pack> mPacks;

        public Recipe(int ingredients, int packages, InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            mIngr = ingredients;
            mPack = packages;

            mRecipe = new int[ingredients];
            mPacks = new ArrayList<>();

            for (int i=0;i<ingredients;i++) {
                mRecipe[i] = in.nextInt();
            }

            for (int i=0;i<ingredients;i++) {
                for (int j=0;j<packages;j++) {
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
}
