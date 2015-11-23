package kr.hs.zion.zionbob;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youngbin on 15. 11. 21.
 */
public class DetailsActivity extends AppCompatActivity
implements MealDetailsFragment.OnFragmentInteractionListener, ReviewsFragment.OnFragmentInteractionListener{
    Context mContext = DetailsActivity.this;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String[] DATA;
    static String[] PARAMS;
    int mealtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get passed data
        PARAMS = getResources().getStringArray(R.array.array_params);
        DATA = new String[24];
        for(int i=0; i<24; i++){
            DATA[i] = getIntent().getStringExtra(PARAMS[i]);
        }
        mealtype = getIntent().getIntExtra("mealtype",2);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MealDetailsFragment().newInstance(DATA, mContext), getResources().getString(R.string.tab_details_details));
        adapter.addFragment(new ReviewsFragment().newInstance(DATA[0], mealtype), getResources().getString(R.string.tab_details_reviews));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    @Override
    public void onMealDetailsFragmentInteraction(Uri uri){}

    @Override
    public void onReviewsFragmentInteraction(Uri uri){}
}
