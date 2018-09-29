package Objects;

import java.io.Serializable;

public class ProductToCart implements Serializable {
    public String idProduct, nameProduct, price, quantum, total;

    public ProductToCart(String idProduct,String nameProduct, String price, String quantum, String total) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.price = price;
        this.quantum = quantum;
        this.total = total;
    }

    public ProductToCart() {
    }
}