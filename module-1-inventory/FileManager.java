import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class FileManager {
    private static final String PRODUCT_FILE = "products.dat";
    private static final String CATEGORY_FILE = "categories.dat";
    private FileManager() {}

    public static void saveProducts(List<Product> products) {
        save(products, PRODUCT_FILE);
    }

    public static List<Product> loadProducts() {
        return load(PRODUCT_FILE);
    }

    public static void saveCategories(List<Category> categories) {
        save(categories, CATEGORY_FILE);
    }

    public static List<Category> loadCategories() {
        return load(CATEGORY_FILE);
    }

    private static void save(Object value, String path) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(value);
        } catch (IOException e) {
            System.err.println("Could not save " + path + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> load(String path) {
        File file = new File(path);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) in.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("Could not load " + path + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
