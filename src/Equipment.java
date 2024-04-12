import java.util.List;
class Equipment {
    private String name;
    private int quantity;

    public Equipment(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    public String getName() {
    	return name;
    }
    public int getQuantity() {
    	return quantity;
    }
    public void setQuantity(int quant) {
    	quantity = quant;
    }

}