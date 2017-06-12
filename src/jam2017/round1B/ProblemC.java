package jam2017.round1B;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class ProblemC extends Round1B {

    public ProblemC() {
        mAlpha = "C";
        mTitle = "Pony Express";
    }

    @Override
    protected List<TestCase> createTestCase(int testCount, InputReader in,  StringBuffer [] results) {
        List<TestCase> tcs = new ArrayList<>();

        for (int i=1;i<=testCount;i++) {
            PonyExpress test = new PonyExpress(in, i, results[i]);
            tcs.add(test);
        }

        return tcs;
    }



    static class PonyExpress extends TestCase {

        private final int N, Q;

        private final int [] U, V;

        HashMap<Integer, City> cities;

        protected PonyExpress(InputReader in, int testNumber, StringBuffer result) {
            super(testNumber, result);

            N = in.nextInt();
            Q = in.nextInt();

            cities = new HashMap<>();

            for (int i=1;i<=N;i++) {
                int E = in.nextInt();
                int S = in.nextInt();
                City city = new City(i, E, S);
                cities.put(i, city);
            }

            for (int i=1;i<=N;i++) {
                for (int j=1;j<=N;j++) {
                    int dist = in.nextInt();

                    if (dist > 0) {
                        cities.get(i).addEdge(j, dist);
                    }
                }
            }

            for (City city : cities.values()) {
                city.addHorse(city.endurance, city.speed);
            }

            U = new int[Q];
            V = new int[Q];

            for (int i=0;i<Q;i++) {
                U[i] = in.nextInt();
                V[i] = in.nextInt();
            }

            print();
        }

        private void print() {
            // TODO Auto-generated method stub

            System.out.printf("=============================\n");
            for (City city : cities.values()) {
                city.print();
            }
            System.out.printf("=============================\n");
        }

        @Override
        protected String solve() {
            // TODO Auto-generated method stub
            return null;
        }


        class City {
            int nodeNum;

            int endurance;
            int speed;

            ArrayList<Edge> edges;

            City(int nodeNum, int endurance, int speed) {
                this.nodeNum = nodeNum;
                this.endurance = endurance;
                this.speed = speed;

                edges = new ArrayList<>();
            }

            void addEdge(int dest, int dist) {
                edges.add(new Edge(dest, dist));
            }

            private void print() {
                System.out.printf("city:%d E:%d S:%d\n", nodeNum, endurance, speed);
                System.out.printf("=============================\n");
                for (Edge edge : edges) {
                    edge.print();
                }
                System.out.printf("=============================\n");
            }

            void addHorse(int endurance, int speed) {
                for (Edge edge : edges) {
                    if (endurance >= edge.distance) {
                        edge.addHorse(endurance, speed);

                        cities.get(edge.edst).addHorse((endurance-edge.distance), speed);
                    }
                }
            }
        }

        static class Edge {
            int edst, distance, maxSpeed;

            public Edge(int dest, int distance) {
                this.edst = dest;
                this.distance = distance;
            }

            public void addHorse(int endurance, int speed) {
                if (endurance >= distance) {
                    addSpeed(speed);
                }

            }

            private void print() {
                System.out.printf("edst:%d distance:%d speed:%d\n", edst, distance, maxSpeed);
            }

            public void addSpeed(int speed) {
                if (speed > maxSpeed) {
                    maxSpeed = speed;
                }
            }
        }
    }
}
