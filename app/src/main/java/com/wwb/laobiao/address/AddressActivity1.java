package com.wwb.laobiao.address;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.MyObservable.Teacher;
import com.wwb.laobiao.address.bean.AdressAdapter;
import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.address.bean.AdressUser;
import com.yangna.lbdsp.R;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity1 extends AppCompatActivity {

    private AdressUser adressUser;
    private RecyclerView recyclerView;
    private Button buttonadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
//        activitytransfer();
//        adressUser=new AdressUser();
//        initrecy();
//        buttonadd=findViewById(R.id.buttonadd_iv);
//        buttonadd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void initrecy() {
        recyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layout);
        List<AdressModel> mlist0=new ArrayList<>();
        AdressAdapter adapter = new AdressAdapter(mlist0);
        adapter.setinterface(new AdressAdapter.AdressAdapterinterface() {
            @Override
            public void edit(String toString) {
                int pos=-1;
                try {
                    pos=Integer.valueOf(toString);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                onedit(pos);
            }
            @Override
            public void delt(String toString) {
                int pos=-1;
                try {
                    pos=Integer.valueOf(toString);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                if(pos>=0)
                {
                    ondelt(pos);
                }
            }

            @Override
            public void onsel(String toString) {
                int pos=-1;
                try {
                    pos=Integer.valueOf(toString);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                if(pos>=0)
                {
                    onmrsel(pos);
                }
            }

        });
        recyclerView.setAdapter(adapter);
    }

    private void onmrsel(int pos) {

    }
    private void onedit(int pos) {
    }
    private void ondelt(int pos) {
        adressUser.mlist.remove(pos);
        initrecy();
    }
    private void activitytransfer() {
//        if(addressUser==null)
//        {
//            addressUser=new AddressUser();
//        }
//        Intent intent = getIntent();
//        Bundle bundleExtra = intent.getBundleExtra("bundle");
//        if(bundleExtra==null)
//        {
//            return;
//        }
//        addressUser= (AddressUser) bundleExtra.getSerializable(AddressUser.SER_KEY);
//        if(addressUser==null)
//        {
//            addressUser=new AddressUser();
//        }
        AdressUser addressUser;
    }

    @Override
    public void onBackPressed() {
        Teacher.getInstance().postMessage(Teacher.ADRESSOK);
        super.onBackPressed();
    }
}