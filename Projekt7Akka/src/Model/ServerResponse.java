package Model;

public class ServerResponse {
    private String productName;
    private double price;
    private State state;

    public ServerResponse(String productName, double price, State state) {
        this.productName = productName;
        this.price = price;
        this.state = state;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public State getState() {
        return state;
    }
}
