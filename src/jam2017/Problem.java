package jam2017;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class Problem {

    static final String INPUT_PATERN = "-(small|large).*[.]in";
    static final String INPUTFILE_EXT = ".in";
    static final String OUTPUTFILE_EXT = ".out";

    static final String INPUT_FOLDER = "input/";
    static final String OUTPUT_FOLDER = "output/";

    public static final String IMPOSSIBLE = "IMPOSSIBLE";

    protected static final boolean DEBUG = false;
    static final int POOL_SIZE = 6;
    static final int MILLI_WAIT = 10;

    protected String mAlpha;
    protected String mTitle;
    protected String mSubFolderName;

    protected abstract TestCase createTestCase(int testCount, InputReader in, StringBuffer result);
    protected abstract String getSubfolderName();

    public final void solve() {
        Locale.setDefault(Locale.US);
        try {
            final String regex = mAlpha + INPUT_PATERN;
            File directory = new File(INPUT_FOLDER + getSubfolderName());
            File[] inputFiles = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(regex);
                }
            });

            if (inputFiles.length == 0) {
                System.out.println("No input file for " + mTitle);
                return;
            }

            for (File inputFile : inputFiles) {
                processFile(inputFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final void processFile(File inputFile) {

        String inputFileName = inputFile.getName();
        String outputFileName = OUTPUT_FOLDER + getSubfolderName() + inputFileName.replace(INPUTFILE_EXT, OUTPUTFILE_EXT);
        System.out.println("outputFileName:" + outputFileName);

        InputStream inputStream;
        OutputStream outputStream;

        try {
            inputStream = new FileInputStream(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            outputStream = new FileOutputStream(outputFileName);
        } catch (Exception e) {
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return;
        }

        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);

        try {
            int testCount = Integer.parseInt(in.next());


            StringBuffer results [] = new StringBuffer[testCount+1];

            for (int i=1;i<=testCount;i++) {
                results[i] = new StringBuffer();
            }

            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(POOL_SIZE);

            long start = System.currentTimeMillis();

            for (int i=1;i<=testCount;i++) {
                TestCase tc = createTestCase(i, in, results[i]);
                executor.execute(tc);
            }

            executor.shutdown();

            while (executor.getActiveCount() > 0) {
                Thread.sleep(MILLI_WAIT);
            }

            System.out.println("Done:" + (System.currentTimeMillis() - start) + "ms");

            for (int i=1;i<=testCount;i++) {
                out.printf(results[i].toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static abstract class TestCase implements Runnable {

        protected StringBuffer result;
        protected int testNumber;

        protected TestCase(int testNumber, StringBuffer result) {
            this.testNumber = testNumber;
            this.result = result;
        }

        @Override
        public void run() {
            result.append(solve());
        }

        protected abstract String solve();
    }

    public static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public char nextChar() {
            return next().charAt(0);
        }

        public void close() throws IOException {
            if (reader != null) {
                reader.close();
            }
        }

    }
}
