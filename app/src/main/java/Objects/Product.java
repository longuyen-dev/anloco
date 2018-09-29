package Objects;

import java.io.Serializable;

public class Product implements Serializable {

    public String nameProduct, idProduct, groupProduct, price1, price2, price3, price4,present;
    public Product() {
    }

    public Product(String nameProduct, String idProduct, String groupProduct, String price1, String price2, String price3, String price4, String present) {
        this.nameProduct = nameProduct;
        this.idProduct = idProduct;
        this.groupProduct = groupProduct;
        this.price1 = price1;
        this.price2 = price2;
        this.price3 = price3;
        this.price4 = price4;
        this.present = present;
    }
}
