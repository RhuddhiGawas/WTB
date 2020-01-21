package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.Helping_Class.HomePage_Adapter;
import com.example.myapplication.Helping_Class.Product;
import com.example.myapplication.R;
import com.example.myapplication.Sign_In;
import com.example.myapplication.SingleProduct;
import com.example.myapplication.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    EditText edLocation,edNotification;

    AutoCompleteTextView edSearch;
    boolean search1=false;

    Button btnCat;
    ListView list_item ;
    String search,text;
    private String auth_token;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    ArrayList<Product> products;
    ArrayList<Product> searchList;
    ArrayList<String> searchText;
    ArrayAdapter arrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        auth_token = Utils.getAuthToken(getActivity());
        edLocation = root.findViewById(R.id.edLocation);
        edNotification=root.findViewById(R.id.edNotification);
        btnCat=root.findViewById(R.id.btnCat);
        list_item=root.findViewById(R.id.list_item);
        edSearch=root.findViewById(R.id.edSearch);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        searchText = new ArrayList<>();

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(auth_token==null){
//                    startActivity(new Intent(getActivity(),Sign_In.class));
//                }


                    if(search1){
                        Intent intent = new Intent(getActivity(), SingleProduct.class);
                        intent.putExtra("product",searchList.get(position));
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getActivity(), SingleProduct.class);
                        intent.putExtra("product",products.get(position));
                        startActivity(intent);
                    }

                }

        });
        products = new ArrayList<>();
        searchList = new ArrayList<>();
        databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Rhuddhi ",dataSnapshot.getKey());
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    products.add(ds.getValue(Product.class));
                    searchText.add(ds.getValue(Product.class).getProname());
                    Log.d("Rhuddhi",ds.getValue(Product.class).getProname());
                }
                HomePage_Adapter homePage_adapter = new HomePage_Adapter(getActivity(), R.layout.homepg_list_layout, products);
                list_item.setAdapter(homePage_adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,searchText);
        edSearch.setAdapter(arrayAdapter);
        edSearch.setThreshold(1);


        edSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edSearch.getRight() - edSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        search = edSearch.getText().toString().toLowerCase();
                        Toast.makeText(getActivity(), search, Toast.LENGTH_SHORT).show();
                        for(int i=0;i<products.size();i++){
                            Log.d("RHUDDHI1", "Num" + i);
                            Log.d("Rhuddhi1",products.get(i).getProname());
                            if(products.get(i).getProname().toLowerCase().equals(search)){
                                Log.d("Rhuddhiasd","TRUE");
                                Log.d("Rhuddhiasd",products.get(i).getProname());
                                searchList.add(products.get(i));
                                search1=true;
                            }
                        }
                        HomePage_Adapter homePage_adapter = new HomePage_Adapter(getActivity(), R.layout.homepg_list_layout, searchList);
                        list_item.setAdapter(homePage_adapter);
                        return true;
                    }
                }
                return false;
            }
        });


        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}