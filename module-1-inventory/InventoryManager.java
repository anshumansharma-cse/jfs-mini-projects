import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private final List<Product> products = FileManager.loadProducts();
    private final List<Category> categories = FileManager.loadCategories();

    public List<Product> getProducts() { return new ArrayList<>(products); }
    public List<Category> getCategories() { return new ArrayList<>(categories); }

    public Product findProductById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public Category findCategoryById(int id) {
        return categories.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public boolean addProduct(Product p) {
        if (p == null || findProductById(p.getId()) != null || p.getPrice() < 0 || p.getStock() < 0) return false;
        products.add(p);
        FileManager.saveProducts(products);
        return true;
    }

    public boolean updateProduct(int id, String name, String category, double price) {
        Product p = findProductById(id);
        if (p == null || name == null || name.isBlank() || category == null || category.isBlank() || price < 0) return false;
        p.setName(name.trim()); p.setCategory(category.trim()); p.setPrice(price);
        FileManager.saveProducts(products);
        return true;
    }

    public boolean deleteProduct(int id) {
        Product p = findProductById(id);
        if (p == null) return false;
        products.remove(p);
        FileManager.saveProducts(products);
        return true;
    }

    public boolean addStock(int id, int quantity) {
        Product p = findProductById(id);
        if (p == null || quantity <= 0) return false;
        p.setStock(p.getStock() + quantity);
        FileManager.saveProducts(products);
        return true;
    }

    public boolean removeStock(int id, int quantity) {
        Product p = findProductById(id);
        if (p == null || quantity <= 0 || p.getStock() < quantity) return false;
        p.setStock(p.getStock() - quantity);
        FileManager.saveProducts(products);
        return true;
    }

    public boolean addCategory(Category c) {
        if (c == null || findCategoryById(c.getId()) != null || c.getName() == null || c.getName().isBlank()) return false;
        categories.add(c);
        FileManager.saveCategories(categories);
        return true;
    }

    public boolean updateCategory(int id, String name) {
        Category c = findCategoryById(id);
        if (c == null || name == null || name.isBlank()) return false;
        String oldName = c.getName();
        c.setName(name.trim());
        for (Product p : products) if (p.getCategory().equalsIgnoreCase(oldName)) p.setCategory(c.getName());
        FileManager.saveCategories(categories);
        FileManager.saveProducts(products);
        return true;
    }

    public boolean deleteCategory(int id) {
        Category c = findCategoryById(id);
        if (c == null || products.stream().anyMatch(p -> p.getCategory().equalsIgnoreCase(c.getName()))) return false;
        categories.remove(c);
        FileManager.saveCategories(categories);
        return true;
    }

    public List<Product> searchProducts(String query) {
        String q = query == null ? "" : query.toLowerCase();
        return products.stream().filter(p -> p.getName().toLowerCase().contains(q)).toList();
    }

    public List<Product> lowStock(int threshold) {
        return products.stream().filter(p -> p.getStock() <= threshold).toList();
    }

    public int nextProductId() { return products.stream().mapToInt(Product::getId).max().orElse(0) + 1; }
    public int nextCategoryId() { return categories.stream().mapToInt(Category::getId).max().orElse(0) + 1; }
}
