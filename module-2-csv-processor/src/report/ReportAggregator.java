package report;

import model.SalesRecord;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class ReportAggregator {
    private final List<SalesRecord> records = new ArrayList<>();
    public void addRecords(List<SalesRecord> rows) { records.addAll(rows); }

    public void generateReport(String path) {
        int totalQty = 0;
        double revenue = 0;
        Map<String, Double> revenueByCategory = new TreeMap<>();
        Map<String, Integer> qtyByCategory = new TreeMap<>();
        Map<String, Integer> qtyByProduct = new TreeMap<>();

        for (SalesRecord r : records) {
            totalQty += r.getQuantity();
            revenue += r.getTotalRevenue();
            revenueByCategory.merge(r.getCategory(), r.getTotalRevenue(), Double::sum);
            qtyByCategory.merge(r.getCategory(), r.getQuantity(), Integer::sum);
            qtyByProduct.merge(r.getProduct(), r.getQuantity(), Integer::sum);
        }

        String topProduct = qtyByProduct.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A");
        String topCategory = revenueByCategory.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A");

        StringBuilder out = new StringBuilder("========== SALES AGGREGATED REPORT ==========")
                .append("\\nRecords processed: ").append(records.size())
                .append("\\nQuantity sold: ").append(totalQty)
                .append(String.format("\\nTotal revenue: $%,.2f", revenue))
                .append(String.format("\\nAverage revenue/record: $%,.2f", records.isEmpty() ? 0 : revenue / records.size()))
                .append("\\nTop-selling product: ").append(topProduct)
                .append("\\nTop revenue category: ").append(topCategory)
                .append("\\n\\nRevenue by category:\\n");
        revenueByCategory.forEach((k, v) -> out.append(String.format("  %-20s $%,.2f%n", k, v)));
        out.append("\\nQuantity by category:\\n");
        qtyByCategory.forEach((k, v) -> out.append(String.format("  %-20s %,d units%n", k, v)));

        System.out.println(out);
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            writer.print(out);
            System.out.println("Saved report to " + path);
        } catch (Exception e) {
            System.err.println("Could not write report: " + e.getMessage());
        }
    }
}
