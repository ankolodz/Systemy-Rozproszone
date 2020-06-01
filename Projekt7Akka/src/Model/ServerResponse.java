package Model;

public class ServerResponse {
    private String productName;
    private double price;
    private State state;
    private int counter = 0;

    public ServerResponse(String productName, double price, int counter, State state) {
        this.productName = productName;
        this.price = price;
        this.state = state;
        this.counter = counter;
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

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }
}
