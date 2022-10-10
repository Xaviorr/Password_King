package com.onemorelvl.passwordking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class PasswordKingAdapter extends RecyclerView.Adapter<PasswordKingAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<PasswordKingModel> mPasswordKingModels;
    private ItemClickListener mItemClickListener;


    public PasswordKingAdapter(Context context, ArrayList<PasswordKingModel> passwordKingModels, ItemClickListener itemClickListener) {
        mContext = context;
        mPasswordKingModels = passwordKingModels;
        mItemClickListener = itemClickListener;

    }

    @NonNull
    @Override
    public PasswordKingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.password_row,parent,false);

        return new MyViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordKingAdapter.MyViewHolder holder, int position) {
        holder.mImageView.setImageResource(R.drawable.ic_baseline_account_circle_48);
        holder.tvCompany.setText(mPasswordKingModels.get(position).getCompanyName());
        holder.tvUserName.setText(mPasswordKingModels.get(position).getUserName());
        holder.tvPassword.setText(mPasswordKingModels.get(position).getPassword());

    }

    @Override
    public int getItemCount() {
        return mPasswordKingModels.size();
    }

    public PasswordKingModel getAccountAt(int position) {
        return mPasswordKingModels.get(position);
    }

    public ArrayList<PasswordKingModel> getPasswordKingModels() {
        return mPasswordKingModels;
    }

    public void updateList(ArrayList<PasswordKingModel> updateList) {
        mPasswordKingModels = updateList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        TextView tvCompany, tvUserName, tvPassword;
        private ItemClickListener mItemClickListener;


        public MyViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView);
            tvCompany = itemView.findViewById(R.id.tvCompanyName);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvPassword = itemView.findViewById(R.id.tvPassword);
            mItemClickListener = itemClickListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClick(getAdapterPosition());

        }
    }
}
