package food;

public class Foot {
    private String product;
    private int amount;

    public Foot() {
    }

    public Foot(String product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return product + "," + amount;
    }
}
