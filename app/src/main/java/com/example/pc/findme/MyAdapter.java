package com.example.pc.findme;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final ListItem listItem = listItems.get(i);

        viewHolder.textViewPersonName.setText(listItem.getFullName());
        viewHolder.textViewGender.setText(listItem.getGender());
        viewHolder.textViewAge.setText(String.valueOf(listItem.getAge()));
        viewHolder.textViewdetials.setText(String.valueOf(listItem.getPersonData()));
        viewHolder.textViewcity.setText(String.valueOf(listItem.getCity()));
        viewHolder.userName.setText(String.valueOf(listItem.getUserName()));
//        viewHolder.userImage.setText(String.valueOf(listItem.getUserImg()));
        viewHolder.postTime.setText(String.valueOf(listItem.getPostTime()));
        viewHolder.number.setText(String.valueOf(listItem.getNumber()));





        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = viewHolder.imageViewPost.getWidth();

                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };


        Picasso.with(context)
                .load(listItem.getImgUrl())
                .error(android.R.drawable.stat_notify_error)
                .into(viewHolder.imageViewPost);

        Picasso.with(context)
                .load(listItem.getUserImg())
                .error(android.R.drawable.stat_notify_error)
                .into(viewHolder.userImage);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   Toast.makeText(context, "You Clicked on "+listItem.getFullName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewPersonName;
        public TextView textViewGender;
        public TextView textViewcity;
        public TextView textViewdetials;
        public TextView postTime;
        public TextView userName;
        public ImageView userImage;
        public TextView number;

        public TextView textViewAge;
        public ImageView imageViewPost;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewPersonName = (TextView) itemView.findViewById(R.id.textViewPersonName);
            textViewGender = (TextView) itemView.findViewById(R.id.textViewGender);
            textViewAge = (TextView) itemView.findViewById(R.id.textViewAge);
            textViewcity=itemView.findViewById(R.id.textViewCity);
            textViewdetials=itemView.findViewById(R.id.textViewDts);
            number = itemView.findViewById(R.id.textViewNumber);
            userName = itemView.findViewById(R.id.userName);
            userImage = (ImageView) itemView.findViewById(R.id.userImage);
            postTime = itemView.findViewById(R.id.postTime);
            imageViewPost    = (ImageView) itemView.findViewById(R.id.imageViewPost);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);

        }
    }

}
