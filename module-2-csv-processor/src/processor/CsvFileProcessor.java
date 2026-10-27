package processor;

import config.ProcessorConfig;
import model.SalesRecord;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class CsvFileProcessor implements Callable<List<SalesRecord>> {
    private final String filePath;
    private final ProcessorConfig config;

    public CsvFileProcessor(String filePath, ProcessorConfig config) {
        this.filePath = filePath; this.config = config;
    }

    @Override
    public List<SalesRecord> call() throws Exception {
        List<SalesRecord> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                if (first && config.isSkipHeader()) { first = false; continue; }
                first = false;
                if (line.isBlank()) continue;
                String[] cols = line.split(String.valueOf(config.getDelimiter()), -1);
                if (cols.length < 4) {
                    System.err.println("Skipping malformed row: " + line);
                    continue;
                }
                try {
                    records.add(new SalesRecord(cols[0].trim(), cols[1].trim(),
                            Integer.parseInt(cols[2].trim()), Double.parseDouble(cols[3].trim())));
                } catch (NumberFormatException ex) {
                    System.err.println("Skipping row with invalid numeric values: " + line);
                }
            }
        }
        System.out.printf("[%s] Processed %s (%d records)%n", Thread.currentThread().getName(), filePath, records.size());
        return records;
    }
}
