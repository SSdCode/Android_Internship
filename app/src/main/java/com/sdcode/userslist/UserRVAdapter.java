package com.sdcode.userslist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.UsersRVViewHolder>{

//    ArrayList<RVUser> usersList;
//    String[] data;

//    public UserRVAdapter(String[] data){
//        this.data = data;
//    }

    ArrayList<RVUser> modelClassList;
    private Context context;

    public UserRVAdapter(ArrayList<RVUser> objectModelClassList, Context context) {
        modelClassList = objectModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_user,parent,false);
        return new UsersRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRVViewHolder holder, int position) {
//        String userName = data[position];
//        holder.userName.setText(userName);

        RVUser modelClass = modelClassList.get(position);
        holder.userName.setText(modelClass.getUserName());
        holder.userEmail.setText(modelClass.getEmail());

        if (modelClass.getGenderId() == 1){
            holder.avatarImage.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_male));
        }else{
            holder.avatarImage.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_female));
        }
    }

    @Override
    public int getItemCount() {
        try {
            return modelClassList.size();
        } catch (Exception e) {
            Log.d("RecyclerViewAdapter", "Exception");
        }
        return 0;
    }

    public class UsersRVViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView avatarImage;
        TextView userName,userEmail;


        public UsersRVViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImage = itemView.findViewById(R.id.rvAvatar);
            userName = itemView.findViewById(R.id.rvUserName);
            userEmail = itemView.findViewById(R.id.rvEmail);



        }
    }
}
