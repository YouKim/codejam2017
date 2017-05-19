package qualificationRound;

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


public abstract class Problem {

    static final String INPUT_PATERN = "-(small|large).*[.]in";
    static final String INPUTFILE_EXT = ".in";
    static final String OUTPUTFILE_EXT = ".out";

    static final boolean DEBUG = false;

    protected String mAlpha;
    protected String mTitle;

    public final void solve() {
        Locale.setDefault(Locale.US);
        try {
            final String regex = mAlpha + INPUT_PATERN;
            File directory = new File(".");
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
        String outputFileName = inputFileName.replace(INPUTFILE_EXT, OUTPUTFILE_EXT);
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
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
            return;
        }

        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);

        try {
            int testCount = Integer.parseInt(in.next());
            for (int testNumber = 1; testNumber <= testCount; testNumber++) {
                solveTest(testNumber, in, out);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    abstract void solveTest(int testNumber, InputReader in, PrintWriter out);

    static class InputReader {
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

        public void close() throws IOException {
            if (reader != null) {
                reader.close();
            }
        }

    }
}
