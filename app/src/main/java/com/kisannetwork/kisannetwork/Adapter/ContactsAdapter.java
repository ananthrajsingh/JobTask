package com.kisannetwork.kisannetwork.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kisannetwork.kisannetwork.Model.Contact;
import com.kisannetwork.kisannetwork.R;

import java.util.List;

/**
 * Created by ananthrajsingh on 17/09/18
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context mContext;
    private List<Contact> mContacts;
    private LayoutInflater mInflater;

    private ContactsAdapter.ItemClickListener mClickListener;


    public ContactsAdapter(Context context, ContactsAdapter.ItemClickListener itemClickListener, List<Contact> list){
        mContext = context;
        mClickListener = itemClickListener;
        mContacts = list;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.contacts_list_item, parent, false);
        return new ContactsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.contactName.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView contactName;

        public ViewHolder(View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contacts_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null){
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
