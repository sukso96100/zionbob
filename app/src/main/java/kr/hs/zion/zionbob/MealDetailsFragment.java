package kr.hs.zion.zionbob;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;


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
    // TODO: Rename and change types of parameters
    private String[] mParams;
    private String[] OriginTitles;
    private String[] NutrinetTitles;
    private OnFragmentInteractionListener mListener;

    public MealDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MealDetailsFragment newInstance(String[] DATA, Context context) {
        // set passed data as arguments
        MealDetailsFragment fragment = new MealDetailsFragment();
        Bundle args = new Bundle();
        ARG_PARAMS = context.getResources().getStringArray(R.array.array_params);
        Log.d(TAG, DATA.toString());
        Log.d(TAG, ARG_PARAMS.toString());
        for(int i=0; i<24; i++){
            args.putString(ARG_PARAMS[i], DATA[i]);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get arguments
        if (getArguments() != null) {
            mParams = new String[24];
            for(int i=0; i<24; i++){
                mParams[i] = getArguments().getString(ARG_PARAMS[i]);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OriginTitles = getActivity().getResources().getStringArray(R.array.array_origin);
        NutrinetTitles = getActivity().getResources().getStringArray(R.array.array_nutrinets);
        String OriginString = "";
        String NutrinetsString = "";
        // Inflate the layout for this fragment
        View Layout = inflater.inflate(R.layout.fragment_meal_details, container, false);
        TextView MealText = (TextView)Layout.findViewById(R.id.mealtxt);
        TextView OriginText = (TextView)Layout.findViewById(R.id.origintxt);
        TextView NutrientsText = (TextView)Layout.findViewById(R.id.nutrientstxt);
        // Show Informations
        MealText.setText(mParams[1]);
        for(int i=2; i<14; i++){
            if(mParams[i].length()>0) {
                OriginString += OriginTitles[i - 2] + " : " + mParams[i] + "\n";
            }
        }
        OriginText.setText(OriginString);
        for(int i=14; i<24; i++){
            if(mParams[i].length()>0) { //ERR!
                NutrinetsString += NutrinetTitles[i - 14] + " : " + mParams[i] + "\n";
            }
        }
        NutrientsText.setText(NutrinetsString);
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
