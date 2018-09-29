package Contains;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Reference {
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public Reference() {

    }
    public DatabaseReference getUsersRef(){
        DatabaseReference usersRef = database.getReference("users");
        return usersRef;
    }
    public DatabaseReference getCustomerRef(){
        DatabaseReference ref = database.getReference("customers");
        return ref;
    }
    public DatabaseReference getGroupCustomerRef(){
        DatabaseReference ref = database.getReference("groupCus");
        return ref;
    }
    public DatabaseReference getGroupProductRef(){
        DatabaseReference ref = database.getReference("groupPrd");
        return ref;
    }
    public DatabaseReference getProductRef(){
        DatabaseReference ref = database.getReference("products");
        return ref;
    }
    public DatabaseReference getInvoiceRef(){
        DatabaseReference ref = database.getReference("invoices");
        return ref;
    }
}
