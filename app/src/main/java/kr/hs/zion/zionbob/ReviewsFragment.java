package kr.hs.zion.zionbob;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ReviewsFragment extends Fragment {
    String TAG = "ReviewsFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "date";
    private static final String ARG_PARAM2 = "mealtype";

    // TODO: Rename and change types of parameters
    private String mDate;
    private int mMealType;

    private OnFragmentInteractionListener mListener;

    View Layout;
    View EmptyView;
    private ListView LV;
    private ReviewsAdapter RA;
    String GUID;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    public static ReviewsFragment newInstance(String date, int mealType) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, date);
        args.putInt(ARG_PARAM2, mealType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDate = getArguments().getString(ARG_PARAM1);
            mMealType = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GuidTool GT = new GuidTool(getActivity());
        GUID = GT.getGUID();
        // Inflate the layout for this fragment
        Layout = inflater.inflate(R.layout.fragment_reviews, container, false);
        EmptyView = (View)Layout.findViewById(R.id.empty);
        Button AddReview = (Button) EmptyView.findViewById(R.id.button);
        AddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyReview(mDate + "_" + mMealType);
            }
        });
        LV = (ListView)Layout.findViewById(R.id.reviews);
        LV.setEmptyView(EmptyView);
        loadReviews(mDate, mMealType);
        return Layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onReviewsFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onReviewsFragmentInteraction(Uri uri);
    }

    void loadReviews(final String Date, final int mealType){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.whereEqualTo("date", Date + "_" + mealType);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d(TAG, "The getFirst request failed.");

                } else {
                    Log.d(TAG, "Retrieved the object.");
                    int myReviewPos = findMyReview(GUID,object.getList("guids"));
                    LV.addHeaderView(getHeader(object.getList("guids"), object.getList("rates"), object.getList("reviews"), myReviewPos));
                    RA = new ReviewsAdapter(getActivity(),
                            object.getList("guids"),object.getList("rates"),object.getList("reviews"));
                    LV.setAdapter(RA);
                    LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0){
                                Log.d(TAG,"ADD OR EDIT MY REVIEW");
                                openMyReview(Date + "_" + mealType);
                            }
                        }
                    });
                }
            }
        });
    }

    View getHeader(List<Object> GUIDs,
                   List<Object> Rates, List<Object> Reviews, int MyReviewPos){
        // Caculate Average Rate
        float Sum = 0;
        for(int i=0; i<Rates.size(); i++){
            Sum += (double) Rates.get(i);
        }
        float Average = Sum / Rates.size();
        View v = (View) getActivity()
                .getLayoutInflater().inflate(R.layout.header_review, null);
        RatingBar AvrRB = (RatingBar)v.findViewById(R.id.rateavr);
        RatingBar MyRB = (RatingBar)v.findViewById(R.id.myrate);
        TextView MyReviewTxt = (TextView)v.findViewById(R.id.myreview);
        AvrRB.setNumStars(5);
        AvrRB.setRating(Average);
        if(MyReviewPos!=-1) {
            MyRB.setNumStars(5);
            double d = (double)Rates.get(MyReviewPos);
            MyRB.setRating((float) d);
            MyReviewTxt.setText((String) Reviews.get(MyReviewPos));
        }
        return v;
    }

    void openMyReview(String Date){
        Intent intent = new Intent(getActivity(), MyReviewActivity.class);
        intent.putExtra("date", Date);
        startActivity(intent);
    }

    int findMyReview(String GUID, List<Object> GUIDs){
        for(int i=0; i<GUIDs.size(); i++){
            if(GUIDs.get(i).toString().equals(GUID)){
                return i;
            }
        }
        return -1;
    }
}
