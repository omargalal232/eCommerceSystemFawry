package ecommerce;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Products
        Cheese cheese = new Cheese("Cheese", 100, 5, false, 0.4);
        Biscuits biscuits = new Biscuits("Biscuits", 150, 3, false, 0.7);
        TV tv = new TV("TV", 300, 4, 5.0);
        MobileCard card = new MobileCard("Scratch Card", 50, 10);

        // Customer
        Customer customer = new Customer("Omar", 800);

        // Cart
        Cart cart = new Cart();
        cart.add(cheese, 2);
        cart.add(biscuits, 1);
        cart.add(tv, 1); // Comment out to test shipping changes
        cart.add(card, 1);

        checkout(customer, cart);
    }

    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Error: Cart is empty.");
            return;
        }

        List<Shippable> toShip = new ArrayList<>();
        double subtotal = 0;

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();

            if (p instanceof Expirable && ((Expirable)p).isExpired()) {
                System.out.println("Error: Product " + p.getName() + " is expired.");
                return;
            }

            if (p.getQuantity() < qty) {
                System.out.println("Error: Product " + p.getName() + " is out of stock.");
                return;
            }

            subtotal += p.getPrice() * qty;
            p.reduceQuantity(qty);

            if (p instanceof Shippable) {
                for (int i = 0; i < qty; i++) toShip.add((Shippable)p);
            }
        }

        double shippingFee = toShip.isEmpty() ? 0 : 30;
        double total = subtotal + shippingFee;

        if (customer.getBalance() < total) {
            System.out.println("Error: Insufficient balance.");
            return;
        }

        customer.deduct(total);

        if (!toShip.isEmpty()) ShippingService.shipItems(toShip);

        // Print receipt
        System.out.println("** Checkout receipt **");
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.println(qty + "x " + p.getName() + " " + (p.getPrice() * qty));
        }
        System.out.println("----------------------");
        System.out.println("Subtotal " + subtotal);
        System.out.println("Shipping " + shippingFee);
        System.out.println("Amount " + total);
        System.out.println("Customer Balance: " + customer.getBalance());

        cart.clear();
    }
}
