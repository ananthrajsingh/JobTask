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
 * This adapter is used to populate recycler view in ContactsFragment
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context mContext;
    private List<Contact> mContacts;
    private LayoutInflater mInflater;

    private ContactsAdapter.ItemClickListener mClickListener;


    /**
     * This is our public constructor, this will take context, a class which implements ItemClickListener
     * and the list of contacts. We need a class implementing clickListener as it is efficient this way.
     * Here we have the view clicked and its position.
     * @param context Context
     * @param itemClickListener class implementing out ItemClickListener
     * @param list list having all the contacts
     */
    public ContactsAdapter(Context context, ContactsAdapter.ItemClickListener itemClickListener, List<Contact> list){
        mContext = context;
        mClickListener = itemClickListener;
        mContacts = list;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * This method creates a new view. This is called only if there are no views to recycle.
     * If views are present to recycle, there is no need to create a new view. Therefore, it is
     * called when the recycler view is laid out. Enough views are created to fill the screen
     * and few more to ease scrolling.
     *
     * @param parent The ViewGroup in which these views are contained within
     * @param viewType when there are more than one view type in a recycler view
     *                 then codes are used to differentiate between views. then
     *                 this is used. In our case, we have all views similar,
     *                 hence no need to care about it.
     * @return a ViewHolder containing all list items
     */
    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.contacts_list_item, parent, false);
        return new ContactsAdapter.ViewHolder(view);
    }

    /**
     * This is called to display data at a particular position. This is NOT called when item
     * positions change.
     * @param holder the view holder which is to updated with information of the position
     * @param position position of view in the current data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.contactName.setText(contact.getName());
    }

    /**
     * This method simply returns the number of items to display. This function is called behind
     * the scenes to help in layout the views and in animations
     *
     * @return number of items available in the main table
     */
    @Override
    public int getItemCount() {
        return mContacts.size();
    }


    /**
     * RecyclerView recycles views. A ViewHolder is a required part of the pattern
     * for RecyclerViews. findViewById() calls are quiet expensive. Therefore
     * ViewHolder objects are created. These are recycled with new values without
     * findVIewById() calls. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //We onl have to show name
        private TextView contactName;

        public ViewHolder(View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contacts_name_tv);
            itemView.setOnClickListener(this);
        }

        //This handles click on a view
        @Override
        public void onClick(View view) {
            if (mClickListener != null){
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    /*
     **********************************************************************************************
     * This must be implemented by the ContactsFragment in order to click functionality to work.
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
