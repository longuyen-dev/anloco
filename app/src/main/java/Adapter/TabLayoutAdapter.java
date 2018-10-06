package Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mrlong.anloco.AccoutActivity;
import com.example.mrlong.anloco.CustomerActivity;
import com.example.mrlong.anloco.InvoiceActivity;
import com.example.mrlong.anloco.ManagerInvoice;
import com.example.mrlong.anloco.OweActivity;
import com.example.mrlong.anloco.ProductActivity;
import com.example.mrlong.anloco.SupplierActivity;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {
    private String listTab[] = {"Khách Hàng","Nhà cung cấp","Hàng hoá","Hoá đơn","Quản lý","Nợ","Tài khoản"};

    private CustomerActivity customerActivity;
    private SupplierActivity supplierActivity;
    private ProductActivity productActivity;
    private InvoiceActivity invoiceActivity;
    private OweActivity oweActivity;
    private ManagerInvoice managerInvoice;
    private AccoutActivity accoutActivity;

    public TabLayoutAdapter(FragmentManager fm) {
        super(fm);
        customerActivity = new CustomerActivity();
        supplierActivity = new SupplierActivity();
        productActivity = new ProductActivity();
        invoiceActivity = new InvoiceActivity();
        managerInvoice = new ManagerInvoice();
        oweActivity = new OweActivity();
        accoutActivity = new AccoutActivity();
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return customerActivity;
        }else if(position == 1){
            return supplierActivity;
        }else if(position == 2){
            return productActivity;
        }else if(position == 3){
            return invoiceActivity;
        }else if(position == 4){
            return managerInvoice;
        }else if(position == 5){
            return oweActivity;
        }else if(position ==6){
            return accoutActivity;
        }

        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }

}
