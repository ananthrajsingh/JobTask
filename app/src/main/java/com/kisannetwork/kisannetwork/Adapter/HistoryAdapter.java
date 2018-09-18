package com.kisannetwork.kisannetwork.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kisannetwork.kisannetwork.Model.History;
import com.kisannetwork.kisannetwork.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ananthrajsingh on 17/09/18
 * This adapter is used to populate recycler view in HistoryFragment.
 ***************************************************************************************************
 * For proper documentation of functions, see ContactsAdapter class
 ***************************************************************************************************
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mContext;
    private List<History> mList;
    private LayoutInflater mInflater;

    public HistoryAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setHistory(List<History> list){
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_list_item, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History currentHistory = mList.get(position);
        holder.name.setText(currentHistory.getName());
        holder.otp.setText(currentHistory.getOtp());


        /*
         * We want date in format dd/MM/yyyy from the milliseconds
         */
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(currentHistory.getTime()));

        holder.date.setText(dateString);

        /*
         * We also want time from milliseconds
         */
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentHistory.getTime());
        String time = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE);

        holder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView otp;
        private TextView date;
        private TextView time;

        private ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.history_name_tv);
            otp = itemView.findViewById(R.id.history_otp_tv);
            date = itemView.findViewById(R.id.history_date_tv);
            time = itemView.findViewById(R.id.history_time_tv);
        }
    }
}
