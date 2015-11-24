package kr.hs.zion.zionbob.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import kr.hs.zion.zionbob.fragments.MealDetailsFragment;
import kr.hs.zion.zionbob.R;
import kr.hs.zion.zionbob.fragments.ReviewsFragment;

/**
 * Created by youngbin on 15. 11. 21.
 */
public class DetailsActivity extends AppCompatActivity
implements MealDetailsFragment.OnFragmentInteractionListener, ReviewsFragment.OnFragmentInteractionListener {

    Context mContext = DetailsActivity.this;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int mealtype;
    String Date;
    String Meal;
    String Origin;
    String Nutrients;

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
        mealtype = getIntent().getIntExtra("mealtype",2);
        Date = getIntent().getStringExtra("date");
        Meal = getIntent().getStringExtra("meal");
        Origin = getIntent().getStringExtra("origin");
        Nutrients = getIntent().getStringExtra("nutrients");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MealDetailsFragment().newInstance(Meal, Origin, Nutrients, mContext),
                getResources().getString(R.string.tab_details_details));
        adapter.addFragment(new ReviewsFragment().newInstance(Date, mealtype),
                getResources().getString(R.string.tab_details_reviews));
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
