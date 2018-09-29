package Objects;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Invoice implements Serializable {
    public String idInvoice,dateInvoice,customer,total,pay,own;
    public Map<String, ProductToCart> detail;

    public Invoice(String idInvoice, String dateInvoice, String customer, String total, String pay, String own) {
        this.idInvoice = idInvoice;
        this.dateInvoice = dateInvoice;
        this.customer = customer;
        this.total = total;
        this.pay = pay;
        this.own = own;
//        this.detail = "";
    }

    public Invoice() {
    }
}
