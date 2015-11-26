package kr.hs.zion.zionbob.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.hs.zion.zionbob.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MealDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MealDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealDetailsFragment extends Fragment {
    static String TAG = "MealDetailsFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static String[] ARG_PARAMS;
    String mMeal;
    String mOrigin;
    String mNutrients;
    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;

    public MealDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MealDetailsFragment newInstance(String Meal, String Origin, String Nutrients, Context context) {
        // set passed data as arguments
        MealDetailsFragment fragment = new MealDetailsFragment();
        Bundle args = new Bundle();
        args.putString("meal", Meal);
        args.putString("origin", Origin);
        args.putString("nutrients", Nutrients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get arguments
        if (getArguments() != null) {
            mMeal = getArguments().getString("meal");
            mOrigin = getArguments().getString("origin");
            mNutrients = getArguments().getString("nutrients");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Layout = inflater.inflate(R.layout.fragment_meal_details, container, false);
        TextView MealText = (TextView)Layout.findViewById(R.id.mealtxt);
        TextView OriginText = (TextView)Layout.findViewById(R.id.origintxt);
        TextView NutrientsText = (TextView)Layout.findViewById(R.id.nutrientstxt);
        // Show Informations
        MealText.setText(mMeal.replaceAll(" ","\n"));
        OriginText.setText(mOrigin);
        NutrientsText.setText(mNutrients);
        return Layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMealDetailsFragmentInteraction(uri);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onMealDetailsFragmentInteraction(Uri uri);
    }
}
