package ecommerce;

import java.util.*;

public class Cart {
    private Map<Product, Integer> items = new HashMap<>();

    public void add(Product product, int quantity) {
        if (product.getQuantity() < quantity) {
            System.out.println("Not enough stock for " + product.getName());
            return;
        }
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public Map<Product, Integer> getItems() { return items; }
    public boolean isEmpty() { return items.isEmpty(); }
    public void clear() { items.clear(); }
}
