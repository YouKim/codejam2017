package jam2017.round1A;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class ProblemB extends Round1A {

    public ProblemB() {
        mAlpha = "B";
        mTitle = "Ratatouille";
    }

    @Override
    protected void solveTest(int testNumber, InputReader in, PrintWriter out) {
        int ingredients = in.nextInt();
        int packages = in.nextInt();

        Recipe recipe = new Recipe(ingredients, packages, in);
        String result = recipe.solve();

        out.printf("Case #%d: %s\n", testNumber, result);
    }

    static class Recipe {
        int mIngr;
        int mPack;

        int mRecipe[];
        ArrayList<Pack> mPacks;

        public Recipe(int ingredients, int packages, InputReader in) {
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

        public String solve() {

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

            return String.valueOf(made);
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
