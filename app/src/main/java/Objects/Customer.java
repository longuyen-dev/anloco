package Objects;

import java.io.Serializable;

public class Customer implements Serializable{
    public String nameCustomer,idCustomer, groupCustomer, addressCustomer, phoneCustomer, priceGroup;




    public Customer() {
    }


    public Customer(String nameCustomer, String idCustomer, String groupCustomer, String addressCustomer, String phoneCustomer, String priceGroup, String recentPrice) {
        this.nameCustomer = nameCustomer;
        this.idCustomer = idCustomer;
        this.groupCustomer = groupCustomer;
        this.addressCustomer = addressCustomer;
        this.phoneCustomer = phoneCustomer;
        this.priceGroup = priceGroup;


    }
}
