package com.deb.vid4u.ui.newvideo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deb.vid4u.dialogbox;
import com.deb.vid4u.Adapter;
import com.deb.vid4u.R;
import com.deb.vid4u.dialogbox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewVideoFragment extends Fragment implements dialogbox.dialogboxlistener {
    RecyclerView mRecyclerView;
    Adapter mAdapter;

    DatabaseReference mReference;
    FirebaseUser mUser;
    ArrayList<String> item;
    ArrayList<String> file;
    String mButton,uid;
    Boolean Admin;
    Boolean flag = false;
    DatabaseReference mDatabaseReference;
    @Override
    public void onStart() {
        super.onStart();

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        item = new ArrayList<>();
        file = new ArrayList<>();
        mButton = "Download";
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mUser.getUid();

        if(mUser.getUid().equals("FW48CMDhbcayWFGt8WD4T1kJcRD3")){
            Admin = true;
        }
        else
            Admin = false;

//        item.add("Second Card View");
//        item.add("Third Card View");
//        item.add("Forth Card View");
//        item.add("Five Card View");
//        mButton = "Download";


        View root = inflater.inflate(R.layout.fragment_newvideos, container, false);
        final RecyclerView mRecyclerView = root.findViewById(R.id.newrecycle);
        final CardView mCard = root.findViewById(R.id.addbtn);
        final CardView upcard = root.findViewById(R.id.uploadedbtn);
        final  CardView downcard = root.findViewById(R.id.downloadebtn);
        final Button down = root.findViewById(R.id.button2);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mAdapter = new Adapter(getContext(),item,mButton);
//        mRecyclerView.setAdapter(mAdapter);
        mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });
        upcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              loadvideos();
            }
        });
        downcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadaudios();
            }
        });

        mDatabaseReference.child("User").child(uid).child("Video").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    item.add(dataSnapshot1.getKey().toString());
                    file.add(dataSnapshot1.getValue().toString());
                }
                mAdapter = new Adapter(getContext(),item,mButton,file,uid);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        if(Admin)
//            {
//                mDatabaseReference.child("Total files").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
//                        {
//                            item.add(dataSnapshot1.getKey().toString());
//                            file.add(dataSnapshot1.getValue().toString());
//                        }
//                        mAdapter = new Adapter(getContext(),item,mButton,file,uid);
//                        mRecyclerView.setAdapter(mAdapter);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }else {
//                mDatabaseReference.child("User").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
//                        {
//                            if(dataSnapshot1.getKey().equals(uid))
//                            {
//                                for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
//                                {
//                                    if(dataSnapshot2.getKey().equals("total")){
//                                        for(DataSnapshot dataSnapshot3:dataSnapshot2.getChildren())
//                                        {
//                                            item.add(dataSnapshot3.getKey());
//                                            file.add(dataSnapshot3.getValue().toString());
//                                        }
//
//                                    }
//                                }
//                            }
//                            mAdapter = new Adapter(getContext(),item,mButton,file,uid);
//                            mRecyclerView.setAdapter(mAdapter);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        }
//    down.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    });


        return root;
    }

    private void loadaudios() {
        item.clear();
        file.clear();
        mDatabaseReference.child("User").child(uid).child("Audio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    item.add(dataSnapshot1.getKey());
                    file.add(dataSnapshot1.getValue().toString());
                }
                mAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void loadvideos() {

        item.clear();
        file.clear();
        mDatabaseReference.child("User").child(uid).child("Video").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    item.add(dataSnapshot1.getKey());
                    file.add(dataSnapshot1.getValue().toString());
                    Log.d("Data",dataSnapshot1.getKey());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        mAdapter.notifyDataSetChanged();
    }

//    private String niceLink (String filename,String uid1){
//        String link;
//        // Points to the root reference
//        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//        StorageReference linkRef = storageRef.child("Upload").child("Video").child(uid1).child(filename);
//        link = linkRef.getDownloadUrl().toString();
//        return link;
//    }
    private void openDialog() {
        dialogbox mdialog = new dialogbox();
        mdialog.show(getFragmentManager(),"example diaog");
    }

    @Override
    public void getInput(Boolean temp) {
            flag = temp;
    }
}
