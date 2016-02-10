package com.newfeds.icare.activities;

import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.newfeds.icare.R;
import com.newfeds.icare.fragments.MemberAppointmentFragment;
import com.newfeds.icare.fragments.MemberDetailsFragment;
import com.newfeds.icare.fragments.MemberDietFragment;
import com.newfeds.icare.fragments.MemberPrescriptionFragment;
import com.newfeds.icare.fragments.MemberVaccineFragment;
import com.newfeds.icare.fragments.MembersFragment;

import java.util.ArrayList;
import java.util.List;

public class MemberDashboard extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static String memberId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        memberId = getIntent().getStringExtra(MembersFragment.MEMBER_ID_KEY);

        //toolbar = (Toolbar) findViewById(R.id.toolbarMemberLayout);
        //setSupportActionBar(toolbar);

        viewPager  = (ViewPager) findViewById(R.id.viewpagerMemberDashboard);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutMemberDashboard);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapeter adapter = new ViewPagerAdapeter(getSupportFragmentManager());
        adapter.addFragment(new MemberDetailsFragment(), "Details");
        adapter.addFragment(new MemberAppointmentFragment(), "AppointMents");
        adapter.addFragment(new MemberDietFragment(), "Diet");
        adapter.addFragment(new MemberVaccineFragment(), "Vaccines");
        adapter.addFragment(new MemberPrescriptionFragment(),"Prescriptions");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapeter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapeter(FragmentManager manager){
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

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
