package kr.hs.zion.zionbob;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by youngbin on 15. 11. 23.
 */
public class ReviewsAdapter extends BaseAdapter {

    String TAG = "ReviewsAdapter";

    Activity context;
    List<Object> mGUIDs;
    List<Object> mRates;
    List<Object> mReviews;

    public ReviewsAdapter(Activity context, List<Object> GUIDs,
                          List<Object> Rates, List<Object> Reviews) {
        super();
        this.context = context;
        this.mGUIDs = GUIDs;
        this.mRates = Rates;
        this.mReviews = Reviews;
        Log.d(TAG, mGUIDs.toString());
        Log.d(TAG, mRates.toString());
        Log.d(TAG, mReviews.toString());
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return mGUIDs.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    private class ViewHolder {
        TextView txtViewGUID;
        TextView txtViewRate;
        TextView txtViewReview;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_review, null);
            holder = new ViewHolder();
            holder.txtViewGUID = (TextView) convertView.findViewById(R.id.guid);
            holder.txtViewRate = (TextView) convertView.findViewById(R.id.rate);
            holder.txtViewReview = (TextView) convertView.findViewById(R.id.review);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewGUID.setText((String) mGUIDs.get(position));
        holder.txtViewRate.setText(mRates.get(position).toString());
        holder.txtViewReview.setText((String)mReviews.get(position));

        return convertView;
    }

}