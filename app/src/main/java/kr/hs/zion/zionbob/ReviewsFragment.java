package kr.hs.zion.zionbob;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


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

    private ListView LV;
    private ReviewsAdapter RA;

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
        // Inflate the layout for this fragment
        View Layout = inflater.inflate(R.layout.fragment_reviews, container, false);
        LV = (ListView)Layout.findViewById(R.id.reviews);
        loadReviews(mDate,mMealType);
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

    void loadReviews(String Date, int mealType){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.whereEqualTo("date", Date + "_" + mealType);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d(TAG, "The getFirst request failed.");
                } else {
                    Log.d(TAG, "Retrieved the object.");
                    LV.addHeaderView(getHeader(object.getList("guids"),object.getList("rates"),object.getList("reviews")));
                    RA = new ReviewsAdapter(getActivity(),
                            object.getList("guids"),object.getList("rates"),object.getList("reviews"));
                    LV.setAdapter(RA);

                }
            }
        });
    }

    View getHeader(List<Object> GUIDs,
                   List<Object> Rates, List<Object> Reviews){
        // Caculate Average Rate
        int Sum = 0;
        for(int i=0; i<Rates.size(); i++){
            Sum += (int) Rates.get(i);
        }
        float Average = Sum / Rates.size();
        View v = (View) getActivity()
                .getLayoutInflater().inflate(R.layout.header_review, null);
        RatingBar AvrRB = (RatingBar)v.findViewById(R.id.rateavr);
        AvrRB.setNumStars(5);
        AvrRB.setRating(Average);
        return v;
    }
}
