package com.deb.vid4u;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_DOWNLOADS;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context ;
    private LayoutInflater mLayoutInflater;
    private List<String> data;
    private List<String> link;
    private String btntitle,uid;
    private Button mButton;
    private Date mDate = new Date();

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());

    public Adapter(Context context, List<String> data, String btntitle,List<String>link,String uid){

        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
        this.link = link;
        this.uid = uid;
        this.btntitle = btntitle;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mLayoutInflater.inflate(R.layout.custom_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final String item_name1 = data.get(position);
        viewHolder.title.setText(item_name1);
        viewHolder.mButton.setText(btntitle);
        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uid.equals("FW48CMDhbcayWFGt8WD4T1kJcRD3")){
                    file nf = new file(sdf.format(mDate),item_name1);
                DownloadManager downloadmanager = (DownloadManager) context.
                        getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(link.get(position));
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(context, DIRECTORY_DOWNLOADS , data.get(position));
                downloadmanager.enqueue(request);
                FirebaseDatabase.getInstance().getReference().child("Downloaded").child(uid).push().setValue(nf);
                FirebaseDatabase.getInstance().getReference(). child("user").child(uid).child("Downloaded").push().setValue(nf);
                FirebaseDatabase.getInstance().getReference(). child("Total files").child("Downloaded").push().setValue(nf);
                }
                else {
                    Intent m = new Intent(context,pay.class);
                    m.putExtra("name",item_name1);
                    Log.d("Data",item_name1);
                    m.putExtra("uri",link.get(position));
                    context.startActivity(m);
                }


            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,amount;
        Button mButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            mButton = itemView.findViewById(R.id.button2);
        }
    }
}
