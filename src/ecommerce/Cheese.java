package ecommerce;

public class Cheese extends Product implements Expirable, Shippable {
    private boolean expired;
    private double weight;

    public Cheese(String name, double price, int quantity, boolean expired, double weight) {
        super(name, price, quantity);
        this.expired = expired;
        this.weight = weight;
    }

    public boolean isExpired() { return expired; }

    public double getWeight() { return weight; }
    public String getName() { return super.getName(); }
}
