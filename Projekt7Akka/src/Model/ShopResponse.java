package Model;

public class ShopResponse {
    private int id;
    private Double price;

    public ShopResponse(int id, Double price) {
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }
}
