package com.deb.vid4u.ui.newvideo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deb.vid4u.CommonStatusBarColour;
import com.deb.vid4u.MainActivity;
import com.deb.vid4u.dialogbox;
import com.deb.vid4u.Adapter;
import com.deb.vid4u.R;
import com.deb.vid4u.dialogbox;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class NewVideoFragment extends Fragment implements dialogbox.dialogboxlistener {
    Adapter mAdapter;
    FirebaseUser mUser;
    ArrayList<String> item;
    Toolbar toolbar;
    ArrayList<String> file;
    AppBarLayout barLayout;
    String mButton,uid;
    ConstraintLayout constraintLayout;
    Boolean Admin;
    Boolean flag = false;
    Window mWindow;
    DatabaseReference mDatabaseReference;
    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("Range")
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

        View root = inflater.inflate(R.layout.fragment_newvideos, container, false);
        final RecyclerView mRecyclerView = root.findViewById(R.id.newrecycle);
        final CardView mCard = root.findViewById(R.id.addbtn);
        final CardView upcard = root.findViewById(R.id.uploadedbtn);
        final  CardView downcard = root.findViewById(R.id.downloadebtn);
        MainActivity main = (MainActivity)getActivity();
//You can access all public variable and methods of MainActivity.
//simply call
        main.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C1C1E")));
        main.getWindow().setStatusBarColor(Color.parseColor("#1C1C1E"));
//        main.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C1C1E")));
//        main.getWindow().setStatusBarColor(Color.parseColor("#1C1C1E"));

//        constraintLayout.setBackgroundColor(Color.parseColor("#1C1C1E"));
//        final Toolbar toolbar = root.findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setBackgroundColor(Color.parseColor("#1C1C1E"));
        final Button down = root.findViewById(R.id.button2);
//        MainActivity mainActivity = new MainActivity();
//       mainActivity.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C1C1E")));
//        final ActionBar actionBar = getActivity().getActionBar();
//        assert actionBar != null;
//        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setTitle(dateFormatMonth.format(new Date()));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mCard.setBackgroundResource(R.drawable.icon_plus_circle);
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

        if(Admin)
        {

            mDatabaseReference.child("Total files").child("Total").addValueEventListener(new ValueEventListener() {
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
        }else {

            mDatabaseReference.child("user").child(uid).child("Video").addValueEventListener(new ValueEventListener() {
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
        }

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    private void loadaudios() {
        item.clear();
        file.clear();

        if(Admin)
        {
            mDatabaseReference.child("Total files").child("Audio").addValueEventListener(new ValueEventListener() {
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
        else{
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




    }

    private void loadvideos() {

        item.clear();
        file.clear();
        if (Admin) {
            mDatabaseReference.child("Total files").child("Video").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        item.add(dataSnapshot1.getKey());
                        file.add(dataSnapshot1.getValue().toString());
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            mDatabaseReference.child("User").child(uid).child("Video").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
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
    }
    private void openDialog() {
        dialogbox mdialog = new dialogbox();
        mdialog.show(getFragmentManager(),"example diaog");
    }
    @Override
    public void getInput(Boolean temp) {
            flag = temp;
    }
}
