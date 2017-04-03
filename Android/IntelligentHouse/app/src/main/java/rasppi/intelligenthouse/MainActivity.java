package rasppi.intelligenthouse;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import rasppi.intelligenthouse.controller.FragmentController;


public class MainActivity extends AppCompatActivity implements FragmentController.OnFragmentInteractionListener  {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static final int fragmentAmount = 6;
    private int currentPosition;
    private float tempArray[] = new float[fragmentAmount];
    private float humArray[] = new float[fragmentAmount];
    private float lightArray[] = new float[fragmentAmount];
    private float fireArray[] = new float[fragmentAmount];
    private float motionArray[] = new float[fragmentAmount];

    private FragmentController fragmentArray[] = new FragmentController[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initArrays();
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                setTemp(5,30);
                setHum(5,50);
                setLight(5,1);
                setFire(5,1);
                setMotion(5,1);

            }
        });



    }

    private void setTemp(int room, float temp){
        if(fragmentArray[room] != null)
            fragmentArray[room].setTemp(temp);
        else
            tempArray[room] = temp;
    }
    private void setHum(int room, float hum){
        if(fragmentArray[room] != null)
            fragmentArray[room].setHum(hum);
        else
            humArray[room] = hum;
    }
    private void setLight(int room, float state){
        if(fragmentArray[room] != null)
            fragmentArray[room].setLight(state);
        else
            lightArray[room] = state;
    }
    private void setFire(int room, float state){
        if(fragmentArray[room] != null)
            fragmentArray[room].setFire(state);
        else
            fireArray[room] = state;
    }
    private void setMotion(int room, float state){
        if(fragmentArray[room] != null)
            fragmentArray[room].setMotion(state);
        else
            motionArray[room] = state;
    }

    private void initArrays(){
        for(int i =0;i<6;i++){
            tempArray[1] = 0;
            humArray[1] = 0;
            lightArray[1] = 0;
            fireArray[1] = 0;
            motionArray[1] = 0;
        }
    }

    @Override
    public void myAction() {

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //currentPosition = position;
            FragmentController fragment = FragmentController.newInstance(position,tempArray[position],
                    humArray[position], lightArray[position],fireArray[position],motionArray[position]);
            fragmentArray[position] = fragment;
            return fragment;
        }

        @Override
        public int getCount() {
            return fragmentAmount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //TODO change to room names sent from master
            switch (position) {
                case 0:
                    return "ROOM 1";
                case 1:
                    return "ROOM 2";
                case 2:
                    return "ROOM 3";
                case 3:
                    return "ROOM 4";
                case 4:
                    return "ROOM 5";
                case 5:
                    return "ROOM 6";
            }
            return null;
        }
    }
}
