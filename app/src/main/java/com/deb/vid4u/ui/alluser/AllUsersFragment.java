package com.deb.vid4u.ui.alluser;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deb.vid4u.Adapter1;
import com.deb.vid4u.MainActivity;
import com.deb.vid4u.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllUsersFragment extends Fragment {
    Adapter1 mAdapter1;
    String mString;
    ArrayList<String> item;
    ArrayList<Integer> upld;
    ArrayList<Integer> dwnld;
    DatabaseReference root,local,store;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = FirebaseDatabase.getInstance().getReference();
        local = root.child("user");
        store = root.child("User");

        upld = new ArrayList<>();
        item = new ArrayList<>();
        dwnld = new ArrayList<>();


        View root = inflater.inflate(R.layout.fragment_allusers, container, false);
        final RecyclerView mRecyclerView = root.findViewById(R.id.recyclerView);
//        final  SearchView mSearchView = root.findViewById(R.id.search);
        MainActivity main = (MainActivity)getActivity();
//You can access all public variable and methods of MainActivity.
//simply call
        main.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C1C1E")));
        main.getWindow().setStatusBarColor(Color.parseColor("#1C1C1E"));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        upld.clear();

        local.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Log.d("Flag Value 0", "" + dataSnapshot1.getKey());
                    for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                    {
                            if ("Username".equals(dataSnapshot2.getKey())) {
                                mString = (String) dataSnapshot2.getValue();
                                Log.d("Flag Value 1", "Username" + dataSnapshot2.getValue());
                                item.add(mString);
                            }
                            if (("Video".equals(dataSnapshot2.getKey()))) {
                                    Log.d("Flag Value 2", "Video  " + dataSnapshot2.getChildrenCount());
                                    upld.add((int) dataSnapshot2.getChildrenCount());

                            }
                            if(("Downloaded").equals(dataSnapshot2.getKey())){
                                    dwnld.add((int)dataSnapshot2.getChildrenCount());
                            }
                        }
                    if(item.size() != upld.size())
                    {
                        Log.d("Flag Value 3", "");
                        upld.add(0);
                    }
                    if(item.size() != dwnld.size())
                    {
                        dwnld.add(0);
                    }
                  }
            mAdapter1 = new Adapter1(getLayoutInflater(),item,upld,dwnld);
                mRecyclerView.setAdapter(mAdapter1);
            };


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }
}
