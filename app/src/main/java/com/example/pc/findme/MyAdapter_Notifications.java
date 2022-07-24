package com.example.pc.findme;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter_Notifications extends RecyclerView.Adapter<MyAdapter_Notifications.ViewHolder> {

    private List<NotificationItem> listItems;
    private Context context;

    public MyAdapter_Notifications(List<NotificationItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_item,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final NotificationItem listItem = listItems.get(i);

        viewHolder.notifyType.setText(listItem.getNotifyType());
        viewHolder.notifyMessage.setText(listItem.getNotifyMessage());
        viewHolder.notifyTime.setText(String.valueOf(listItem.getNotifyTime()));


        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listItem.getNotifyType().equals("reporting")) {
                    Toast.makeText(context, "Your report submitted successfully ", Toast.LENGTH_LONG).show();
                }else{
                    Intent i = new Intent(context.getApplicationContext(),MatchingActivity.class);

                    //Set the Data to pass
                    String notifyId = listItem.getId();
                    i.putExtra("notifyId", notifyId);
                    context.startActivity(i);
                }
            }
        });

}

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView notifyType;
        public TextView notifyMessage;
        public TextView notifyTime;
        public TextView id;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notifyType = (TextView) itemView.findViewById(R.id.notifyType);
            notifyMessage = (TextView) itemView.findViewById(R.id.notifyMessage);
            notifyTime = (TextView) itemView.findViewById(R.id.notifyTime);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.notificationLayout);

        }
    }
}
