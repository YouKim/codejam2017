package jam2017.qualificationRound;

import static org.junit.Assert.assertTrue;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.Test;

import jam2017.Problem;

public class ProblemD extends Qulification {

    public ProblemD() {
        mAlpha = "D";
        mTitle = "Fashion Show";
    }

    @Override
    protected TestCase createTestCase(int testNumber, InputReader in,  StringBuffer result) {
        return new FashionMap(in, testNumber, result);
    }

    static class FashionMap extends TestCase {
        static final char O = 'o';
        static final char X = 'x';
        static final char P = '+';
        static final char E = '.';

        private int mSize;
        private HashMap<RowCol, Character> mModels;
        private HashMap<RowCol, Character> mUpdates;
        boolean rowBlocked[];
        boolean colBlocked[];
        boolean diagSumBlocked[];
        boolean diagDifBlocked[];

        private int mScore;

        public FashionMap(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            mSize = in.nextInt();
            mModels = new HashMap<>();
            mUpdates = new HashMap<>();
            // index (r/c - 1)
            rowBlocked = new boolean[mSize+1];
            colBlocked = new boolean[mSize+1];

            // index (r+c)
            diagSumBlocked = new boolean[2*(mSize+1)];

            // index (size+r-c)
            diagDifBlocked = new boolean[2*(mSize+1)];

            int pre = in.nextInt();

            for (int i = 0; i < pre; i++) {
                char model = in.nextChar();
                int row = in.nextInt();
                int col = in.nextInt();

                put(row, col, model);
            }
        }

        public char get(int row, int col) {
            RowCol rowcol = new RowCol(row, col);
            Character model = mModels.get(rowcol);
            return (model == null)?E:model;
        }

        // pre-placed model.
        public boolean put(int row, int col, char model) {
            if (model == E) {
                return false;
            }

            RowCol rowcol = new RowCol(row, col);
            mModels.put(rowcol, Character.valueOf(model));

            if (model == O || model == P) {
                diagSumBlocked[row+col] = true;
                diagDifBlocked[row-col+mSize] = true;
            }

            if (model == O || model == X) {
                rowBlocked[row] = true;
                colBlocked[col] = true;
            }

            mScore += (model==O)?2:1;

            return true;
        }

        public boolean update(int row, int col, char model) {

            if (model == E) {
                return false;
            }

            RowCol rowcol = new RowCol(row, col);

            char old = E;
            Character item = mUpdates.get(rowcol);

            if (item == null) {
                item = mModels.get(rowcol);
            }

            if (item != null) {
                old = item;
            }

            if (old != E) {
                model = O;

                if (old == model) {
                    return false;
                }

                mScore -= (old==O)?2:((old==E)?0:1);
            }
            mScore += (model==O)?2:1;
            mUpdates.put(rowcol, Character.valueOf(model));
            return true;
        }

        @Override
        protected String solve() {
            Biparted rowcolGraph = new Biparted();
            Biparted diagGraph = new Biparted();

            for (int r=1;r<=mSize;r++) {
                for (int c=1;c<=mSize;c++) {
                    char model = get(r, c);

                    if (!rowBlocked[r] && !colBlocked[c]) {
                        if (model == E || model == P) {
                            rowcolGraph.addEdge(r, c);
                        }
                    }

                    if (!diagSumBlocked[r+c] && !diagDifBlocked[r-c+mSize]) {
                        if (model == E || model == X) {
                            diagGraph.addEdge(r+c, r-c+mSize);
                        }
                    }
                }
            }
            ArrayList<Entry<Integer, Integer>> resultX = rowcolGraph.resolve();

            for (Entry<Integer, Integer> result : resultX) {
                update(result.getKey(), result.getValue(), X);
            }

            ArrayList<Entry<Integer, Integer>> resultP = diagGraph.resolve();

            for (Entry<Integer, Integer> result : resultP) {
                int sum = result.getKey();
                int diff = result.getValue();
                update((sum+diff-mSize)/2, (sum-diff+mSize)/2, P);
            }

            return String.format("Case #%d: %s", testNumber, getResult());
        }

        String getResult() {
            StringBuffer result = new StringBuffer();

            result.append(String.format("%d %d\n", mScore, mUpdates.size()));

            Iterator<RowCol> iter = mUpdates.keySet().iterator();

            while (iter.hasNext()) {
                RowCol key = iter.next();

                char ch = mUpdates.get(key);

                int row = key.mRow;
                int col = key.mCol;

                result.append(String.format("%c %d %d\n", ch, row, col));
            }

            return result.toString();
        }
    }

    static class RowCol {

        public int mRow;
        public int mCol;

        RowCol(int row, int col) {
            mRow = row;
            mCol = col;
        }

        @Override
        public boolean equals(Object arg0) {

            if (arg0 instanceof RowCol) {
                RowCol that = (RowCol) arg0;
                return mRow == that.mRow && mCol == that.mCol;
            }

            return false;
        }

        @Override
        public int hashCode() {
            //1 ≤ N ≤ 100
            return mRow * 1000 + mCol;
        }
    }

    static class Biparted {

        private HashMap<Integer, LNode> leftNodes;
        private HashMap<Integer, Node> rightNodes;
        private ArrayList<LNode> sortedLeftNodes;

        private ArrayList<Entry<Integer, Integer>> results;

        Biparted() {
            leftNodes = new HashMap<>();
            rightNodes = new HashMap<>();
            sortedLeftNodes = new ArrayList<>();
            results = new ArrayList<>();
        }

        void addEdge(int l, int r) {
            LNode left = leftNodes.get(l);
            Node right = rightNodes.get(r);

            if (left == null) {
                left = new LNode(l);
                leftNodes.put(l, left);
                sortedLeftNodes.add(left);
            }

            if (right == null) {
                right = new Node(r);
                rightNodes.put(r, right);
            }

            left.add(right);
        }

        void sortByCandidateNums() {
            Collections.sort(sortedLeftNodes, new Comparator<LNode>() {

                @Override
                public int compare(LNode o1, LNode o2) {
                    return o1.getCandidateSize() - o2.getCandidateSize();
                }
            });
        }

        ArrayList<Entry<Integer, Integer>> resolve() {
            sortByCandidateNums();

            for (LNode left :sortedLeftNodes) {
                if (!left.isConnected() && left.getCandidateSize() > 0) {
                    Iterator<Node> iter = left.iterator();
                    while (iter.hasNext()) {
                        Node right = iter.next();

                        if (!right.isConnected()) {
                            left.connect(right);
                            //System.out.println("Connected : " + left.mIndex + " " + right.mIndex);
                            results.add(new AbstractMap.SimpleEntry<>(left.mIndex, right.mIndex));
                            break;
                        }
                    }
                }
            }

            return results;
        }
    }

    // Normal node.
    static class Node {
        public boolean connected;
        public int mIndex;
        public int connectedIndex;

        Node(int index) {
            mIndex = index;
        }

        boolean isConnected() {
            return connected;
        }

        void connect(Node target) {
            target.connected = this.connected = true;
            target.connectedIndex = this.mIndex;
            this.connectedIndex = target.mIndex;
        }
    }

    // Node has candidate list.
    static class LNode extends Node {

        ArrayList<Node> mCandidate;

        LNode(int index) {
            super(index);
        }

        void add(Node candidate) {
            if (mCandidate == null) {
                mCandidate = new ArrayList<>();
            }
            mCandidate.add(candidate);
        }

        int getCandidateSize() {
            if (mCandidate == null) {
                return 0;
            }

            return mCandidate.size();
        }

        Iterator<Node> iterator() {
            return mCandidate.iterator();
        }
    }

    @Override
    protected String getSampleInput() {
        return "3\n" +
                "2 0\n" +
                "1 1\n" +
                "o 1 1\n" +
                "3 4\n" +
                "+ 2 3\n" +
                "+ 2 1\n" +
                "x 3 1\n" +
                "+ 2 2";
    }

    @Override
    protected String getSampleOutput() {
        return "Case #1: 4 3\n" +
                "o 2 2\n" +
                "+ 2 1\n" +
                "x 1 1\n" +
                "Case #2: 2 0\n" +
                "Case #3: 6 2\n" +
                "o 2 3\n" +
                "x 1 2\n";
    }

    public static void main(String[] args) {
        Problem problem = new ProblemD();
        problem.solve();
    }

    @Test
    public void testSample() {
        Problem problem = new ProblemD();
        boolean result = problem.test();

        assertTrue(result);
    }
}
