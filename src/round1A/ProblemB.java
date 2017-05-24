package round1A;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class ProblemB extends Problem {

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
        Packages mPacks;

        public Recipe(int ingredients, int packages, InputReader in) {
            mIngr = ingredients;
            mPack = packages;

            mRecipe = new int[ingredients];
            mPacks = new Packages();

            for (int i=0;i<ingredients;i++) {
                mRecipe[i] = in.nextInt();
            }

            for (int i=0;i<ingredients;i++) {
                for (int j=0;j<packages;j++) {
                    int gram = in.nextInt();
                    Pack pack = new Pack(i, gram, mRecipe[i], ingredients);
                    if (!pack.isInvalid) {
                        mPacks.add(pack);
                    }
                }
            }
        }

        public String solve() {
            int made = 0;
            mPacks.sortByMaxServ();

            Iterator<Pack> iter = mPacks.iterator();
            while (iter.hasNext()) {
                Pack pack = iter.next();
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

            Pack(int index, int gram, int recipe, int total) {
                this.index = index;

                double max = (gram * 10.0) / (recipe * 9.0);
                double min = (gram * 10.0) / (recipe * 11.0);

                minServ = (int) Math.ceil(min);
                maxServ = (int) Math.floor(max);

                isInvalid = maxServ < minServ || (maxServ == 0);
            }

            public boolean makeKit(Packages list) {
                if (isInvalid) {
                    return false;
                }

                int needed = 0;
                Pack [] templet = new Pack[mIngr];

                templet[index] = this;
                needed++;

                if (needed < mIngr) {
                    Iterator<Pack> iter = list.iterator();

                    while (iter.hasNext() && needed < mIngr) {

                        Pack pack = iter.next();

                        if (pack.index != this.index && !pack.isInvalid) {
                            if (templet[pack.index] == null) {
                                if (hasSameRange(pack)) {
                                    templet[pack.index] = pack;
                                    needed++;
                                }
                            }
                        }
                    }
                }

                if (needed == mIngr) {
                    for (Pack pack:templet) {
                        pack.open();
                    }
                    return true;
                }

                return false;
            }

            private void open() {
                isInvalid = true;
            }

            public boolean hasSameRange(Pack target) {
                if (target.isInvalid) {
                    return false;
                }

                int min = (minServ > target.minServ)?minServ:target.minServ;
                int max = (maxServ < target.maxServ)?maxServ:target.maxServ;

                return (max - min + 1 > 0);
            }
        }

        class Packages extends ArrayList<Pack> {

            void sortByMaxServ() {
                Collections.sort(this, new Comparator<Pack>() {

                    @Override
                    public int compare(Pack o1, Pack o2) {
                        return o1.maxServ - o2.maxServ;
                    }
                });
            }
        }
    }
}
