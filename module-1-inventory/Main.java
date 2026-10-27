import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final InventoryManager manager = new InventoryManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\\n=== INVENTORY MANAGEMENT ===");
            System.out.println("1 Products  2 Categories  3 Stock  4 Low-stock report  0 Exit");
            switch (readInt("Choice: ")) {
                case 1 -> productMenu();
                case 2 -> categoryMenu();
                case 3 -> stockMenu();
                case 4 -> printProducts(manager.lowStock(5));
                case 0 -> { System.out.println("Bye!"); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void productMenu() {
        while (true) {
            System.out.println("\\n--- Products --- 1 Add 2 View 3 Search 4 Update 5 Delete 0 Back");
            int choice = readInt("Choice: ");
            switch (choice) {
                case 1 -> {
                    String name = readString("Name: ");
                    String cat = readString("Category: ");
                    double price = readDouble("Price: ");
                    int stock = readInt("Initial stock: ");
                    System.out.println(manager.addProduct(new Product(manager.nextProductId(), name, cat, price, stock)) ? "Added." : "Could not add.");
                }
                case 2 -> printProducts(manager.getProducts());
                case 3 -> printProducts(manager.searchProducts(readString("Search name: ")));
                case 4 -> {
                    int id = readInt("Product ID: ");
                    System.out.println(manager.updateProduct(id, readString("New name: "), readString("New category: "), readDouble("New price: ")) ? "Updated." : "Not found/invalid.");
                }
                case 5 -> System.out.println(manager.deleteProduct(readInt("Product ID: ")) ? "Deleted." : "Not found.");
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void categoryMenu() {
        while (true) {
            System.out.println("\\n--- Categories --- 1 Add 2 View 3 Update 4 Delete 0 Back");
            int choice = readInt("Choice: ");
            switch (choice) {
                case 1 -> System.out.println(manager.addCategory(new Category(manager.nextCategoryId(), readString("Name: "))) ? "Added." : "Could not add.");
                case 2 -> manager.getCategories().forEach(System.out::println);
                case 3 -> System.out.println(manager.updateCategory(readInt("Category ID: "), readString("New name: ")) ? "Updated." : "Not found/invalid.");
                case 4 -> System.out.println(manager.deleteCategory(readInt("Category ID: ")) ? "Deleted." : "Not found or used by products.");
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void stockMenu() {
        int id = readInt("Product ID: ");
        System.out.println("1 Add stock  2 Remove stock");
        int choice = readInt("Choice: ");
        int quantity = readInt("Quantity: ");
        boolean ok = choice == 1 ? manager.addStock(id, quantity) : choice == 2 && manager.removeStock(id, quantity);
        System.out.println(ok ? "Stock updated." : "Invalid product/quantity or insufficient stock.");
    }

    private static void printProducts(List<Product> products) {
        if (products.isEmpty()) System.out.println("No products found.");
        else products.forEach(System.out::println);
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try { return Integer.parseInt(readString(prompt)); }
            catch (NumberFormatException e) { System.out.println("Enter a valid integer."); }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try { return Double.parseDouble(readString(prompt)); }
            catch (NumberFormatException e) { System.out.println("Enter a valid number."); }
        }
    }
}
