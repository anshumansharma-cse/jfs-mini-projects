package config;

public class ProcessorConfig {
    private final int threadCount;
    private final String inputFolder;
    private final String outputFile;
    private final boolean skipHeader;
    private final char delimiter;

    private ProcessorConfig(Builder b) {
        threadCount = b.threadCount; inputFolder = b.inputFolder;
        outputFile = b.outputFile; skipHeader = b.skipHeader; delimiter = b.delimiter;
    }

    public int getThreadCount() { return threadCount; }
    public String getInputFolder() { return inputFolder; }
    public String getOutputFile() { return outputFile; }
    public boolean isSkipHeader() { return skipHeader; }
    public char getDelimiter() { return delimiter; }

    public static class Builder {
        private int threadCount = 3;
        private String inputFolder = "data";
        private String outputFile = "report.txt";
        private boolean skipHeader = true;
        private char delimiter = ',';

        public Builder threadCount(int n) { if (n < 1) throw new IllegalArgumentException("Thread count must be >= 1"); threadCount = n; return this; }
        public Builder inputFolder(String s) { inputFolder = s; return this; }
        public Builder outputFile(String s) { outputFile = s; return this; }
        public Builder skipHeader(boolean b) { skipHeader = b; return this; }
        public Builder delimiter(char c) { delimiter = c; return this; }
        public ProcessorConfig build() { return new ProcessorConfig(this); }
    }
}
