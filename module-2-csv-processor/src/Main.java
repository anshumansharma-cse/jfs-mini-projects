import config.ProcessorConfig;
import model.SalesRecord;
import processor.CsvFileProcessor;
import report.ReportAggregator;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ProcessorConfig config = new ProcessorConfig.Builder()
                .threadCount(3).inputFolder("data").outputFile("report.txt")
                .skipHeader(true).delimiter(',').build();

        File folder = new File(config.getInputFolder());
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (files == null || files.length == 0) {
            System.err.println("No CSV files found in " + config.getInputFolder());
            return;
        }

        ExecutorService pool = Executors.newFixedThreadPool(config.getThreadCount());
        List<Future<List<SalesRecord>>> futures = new ArrayList<>();
        for (File file : files) futures.add(pool.submit(new CsvFileProcessor(file.getPath(), config)));

        ReportAggregator aggregator = new ReportAggregator();
        try {
            for (Future<List<SalesRecord>> future : futures) aggregator.addRecords(future.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Processing interrupted.");
        } catch (ExecutionException e) {
            System.err.println("Task failed: " + e.getCause().getMessage());
        } finally {
            pool.shutdown();
        }
        aggregator.generateReport(config.getOutputFile());
    }
}
