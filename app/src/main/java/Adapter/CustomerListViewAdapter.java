package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mrlong.anloco.R;

import java.util.List;

import Objects.Customer;

public class CustomerListViewAdapter extends ArrayAdapter<Customer> {

    public CustomerListViewAdapter(@NonNull Context context, int resource, @NonNull List<Customer> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_row_customer,null);

        }
        Customer cus = getItem(position);

        if (cus != null){
            TextView idCus = (TextView) v.findViewById(R.id.idCustomerTextView);
            idCus.setText(cus.idCustomer);

//            TextView idCus = (TextView) v.findViewById(R.id.idCustomerTextView);
//            idCus.setText(cus.idCustomer);

            TextView nameCus = (TextView) v.findViewById(R.id.nameCustomerTextView);
            nameCus.setText(cus.nameCustomer);

            TextView groupCus = (TextView) v.findViewById(R.id.groupCustomerTextView);
            groupCus.setText(cus.groupCustomer);

            TextView addressCus = (TextView) v.findViewById(R.id.addressCustomerTextView);
            addressCus.setText(cus.addressCustomer);

            TextView phoneCus = (TextView) v.findViewById(R.id.phoneCustomerTextView);
            phoneCus.setText(cus.phoneCustomer);

            TextView priceCus = (TextView) v.findViewById(R.id.priceCustomerTextView);
            priceCus.setText(cus.priceGroup);


        }

        return v;
    }
}
