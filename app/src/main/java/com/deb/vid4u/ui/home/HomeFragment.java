package com.deb.vid4u.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.deb.vid4u.MainActivity;
import com.deb.vid4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference local;
    FirebaseUser mUser;
    String uid;
    Boolean Admin;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mUser.getUid();
        final TextView mTextView = root.findViewById(R.id.ttlupd);
        final TextView nText = root.findViewById(R.id.ttlupdtoday);
        final  TextView textView = root.findViewById(R.id.ttluser);
        final  TextView down = root.findViewById(R.id.ttlddwld);
        final  TextView downmy = root.findViewById(R.id.ttldwldtoday);
        final TextView payment = root.findViewById(R.id.ttlpay);
        local = FirebaseDatabase.getInstance().getReference();
        MainActivity main = (MainActivity)getActivity();
//You can access all public variable and methods of MainActivity.
//simply call
        main.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3700B3")));
        main.getWindow().setStatusBarColor(Color.parseColor("#3700B3"));
        local.child("Total files").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String m = String.valueOf(dataSnapshot.getChildrenCount());
                mTextView.setText(m);
                Log.d("No of child",m);
                Log.d("No of child",""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        local.child("User").addValueEventListener(new ValueEventListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
//                {
//                    for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
//                    {
//                        if(dataSnapshot2.getKey().equals("Video"))
//                        {
//                            mTextView.setText(Integer.toString((int) dataSnapshot2.getChildrenCount()));
////                           mTextView.setText((int) dataSnapshot2.getChildrenCount());
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        local.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot11:dataSnapshot.getChildren())
                {
                    textView.setText(Integer.toString((int)dataSnapshot11.getChildrenCount()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        local.child("Payment").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount() == 0)
                {
                    payment.setText("0");
                }
                else
                    payment.setText(Integer.toString( (int) dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        local.child("User").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if(uid.equals(dataSnapshot1.getKey())){
                        for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                        {
                            if(dataSnapshot2.getKey().equals("Video"))
                            {
                                nText.setText(Integer.toString((int) dataSnapshot2.getChildrenCount()));
//                           mTextView.setText((int) dataSnapshot2.getChildrenCount());
                            }
                        }
                    }
                  if(nText.getText().toString().equals("")){
                      nText.setText("0");
                  }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        local.child("Total files").child("Downloaded").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                down.setText(Integer.toString((int) dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        local.child("user").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if(dataSnapshot1.getKey().equals("Download"))
                    {
                        downmy.setText(Integer.toString((int) dataSnapshot1.getChildrenCount()));
                    }
                    else
                        downmy.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return root;
    }
}
