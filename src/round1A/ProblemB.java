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
        recipe.print();
        String result = recipe.solve();
        System.out.println("Case #:" + testNumber + " result:" + result);
        out.printf("Case #%d: %s\n", testNumber, result);
    }

    static class Recipe {

        int mIngr;
        int mPack;

        int mRecipe[];
        Packages mPacks[];

        public Recipe(int ingredients, int packages, InputReader in) {
            mIngr = ingredients;
            mPack = packages;

            mRecipe = new int[ingredients];
            mPacks = new Packages[ingredients];

            for (int i=0;i<ingredients;i++) {
                mRecipe[i] = in.nextInt();
                mPacks[i] = new Packages(i);
            }

            for (int i=0;i<ingredients;i++) {
                for (int j=0;j<packages;j++) {
                    int gram = in.nextInt();
                    Pack pack = new Pack(i, gram, mRecipe[i], ingredients);
                    if (!pack.isInvalid) {
                        mPacks[i].add(pack);
                    }
                }
            }
        }

        public String solve() {

            int valid = 0;

            for (int i=0;i<mIngr;i++) {
                for (int t=0;t<mIngr;t++) {
                    if (i != t) {
                        mPacks[i].checkCandidate(mPacks[t]);
                    }
                }
            }

            for (int i=0;i<mIngr;i++) {
                mPacks[i].sortByReference();
                mPacks[i].printCandidate();
            }

            valid = makePackage();

            return String.valueOf(valid);
        }

        private int makePackage() {

            int count = 0;

            for (int i=0;i<mIngr;i++) {
                 Iterator<Pack> iter = mPacks[i].iterator();
                 while (iter.hasNext()) {
                     Pack pack = iter.next();
                     if (pack.canMakePack()) {
                         if (pack.makePack()) {
                             count++;
                         }
                     }
                 }
            }

            return count;
        }

        public void print() {
            StringBuffer result = new StringBuffer();
            result.append("#ingredients : " + mIngr);
            result.append(" #packages : " + mPack + "\n");

            for (int i=0;i<mIngr;i++) {
                result.append(mRecipe[i] + " ");
            }
            result.append("\n");
            for (int i=0;i<mIngr;i++) {
                for (int j=0;j<mPack;j++) {
                    //result.append(mGram[i][j] + " ");
                }
                result.append("\n");
            }
            System.out.print(result.toString());
        }

        public class Pack {
            int index;
            int weight;
            int minServ;
            int maxServ;
            boolean isInvalid;
            int ref;

            Packages[] mCandidate;

            Pack(int index, int gram, int recipe, int total) {
                this.index = index;

                mCandidate = new Packages[total];
                weight = gram;

                double max = (gram * 10.0) / (recipe * 9.0);
                double min = (gram * 10.0) / (recipe * 11.0);

                minServ = (int) Math.ceil(min);
                maxServ = (int) Math.floor(max);

                isInvalid = maxServ < minServ;

                System.out.println("gram:" + gram + " recipe:" + recipe);
                System.out.println("minServ:" + minServ + " maxServ:" + maxServ);
            }

            public boolean makePack() {

                int used = 0;
                for (int i=0;i<mIngr;i++) {
                    if (i != index) {
                        for (int j=0;j<mCandidate[i].size();j++) {
                            Pack target = mCandidate[i].get(j);
                            if (!target.isInvalid) {
                                used++;
                                break;
                            }
                        }
                    }
                }

                used++;

                if (used != mIngr) {
                    return false;
                }

                for (int i=0;i<mIngr;i++) {
                    if (i != index) {
                        for (int j=0;j<mCandidate[i].size();j++) {
                            Pack target = mCandidate[i].get(j);
                            if (!target.isInvalid) {
                                target.open();
                                break;
                            }
                        }
                    }
                }

                isInvalid = true;
                return true;
            }

            private void open() {
                // TODO Auto-generated method stub
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

            public void addCandidate(int index, Pack target) {
                if (mCandidate[index] == null) {
                    mCandidate[index] = new Packages(index);
                }
                mCandidate[index].add(target);
                target.onAdded();
            }

            private void onAdded() {
                ref++;
            }

            public Packages[] getCandidate() {
                return mCandidate;
            }

            public boolean canMakePack() {
                if (isInvalid) {
                    return false;
                }

                for (int i=0;i<mIngr;i++) {
                    if (i != index) {
                        if (mCandidate[i] == null || mCandidate[i].size() == 0) {
                            return false;
                        }
                    }
                }

                return true;
            }

            public void pruneInvalid() {
                if (!canMakePack()) {
                    isInvalid = true;
                }
            }

            public int getMinSize() {

                int min = Integer.MAX_VALUE;

                if (mIngr == 1) {
                    return 1;
                }

                for (int i=0;i<mIngr;i++) {
                    if (i != index) {
                        if (mCandidate[i] == null) {
                            return 0;
                        } else {
                            int size = mCandidate[i].size();

                            if (size < min) {
                                min = size;
                            }
                        }
                    }
                }

                return min;
            }

        }

        class Packages extends ArrayList<Pack> {
            int index;

            public Packages(int i) {
                index = i;
            }

            public void checkCandidate(Packages list) {
                Iterator<Pack> iter = iterator();
                while (iter.hasNext()) {
                    Pack item = iter.next();

                    if (item.isInvalid) {
                        continue;
                    }

                    Iterator<Pack> iter2 = list.iterator();
                    while (iter2.hasNext()) {
                        Pack target = iter2.next();
                        if (item.hasSameRange(target)) {
                            item.addCandidate(target.index, target);
                        }
                    }
                }
            }

            public void printCandidate() {
                StringBuffer result = new StringBuffer();
                result.append("printCandidate : ");

                Iterator<Pack> iter = iterator();
                while (iter.hasNext()) {
                    Pack item = iter.next();
                    result.append(item.weight);
                    result.append("[" + item.ref + "]");
                    if (item.canMakePack()) {
                        result.append("[" + item.getMinSize() + "] ");
                    } else {
                        result.append("X ");
                    }
                    /*
                    Packages[] candidate = item.getCandidate();

                    for (int i=0;i<candidate.length;i++) {
                        if (candidate[i] != null) {
                            result.append(candidate[i].size() + " ");
                        }
                    }
                    */
                }

                System.out.println(result.toString());
            }

            void sortByReference() {
                Collections.sort(this, new Comparator<Pack>() {

                    @Override
                    public int compare(Pack o1, Pack o2) {
                        return o1.ref - o2.ref;
                    }
                });
            }
        }
    }
}
