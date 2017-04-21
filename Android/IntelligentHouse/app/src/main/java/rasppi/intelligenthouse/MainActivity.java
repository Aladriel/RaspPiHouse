package rasppi.intelligenthouse;

import android.content.Intent;
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

import java.io.IOException;

import rasppi.intelligenthouse.Comms.CommsManager;
import rasppi.intelligenthouse.Comms.CommsProtocol;
import rasppi.intelligenthouse.Comms.IReadingsEvent;
import rasppi.intelligenthouse.Comms.UserLoginInfo;
import rasppi.intelligenthouse.Sensors.SensorReading;
import rasppi.intelligenthouse.Sensors.SensorReadings;
import rasppi.intelligenthouse.Sensors.SensorType;
import rasppi.intelligenthouse.controller.FragmentController;


public class MainActivity extends AppCompatActivity implements FragmentController.OnFragmentInteractionListener, IReadingsEvent {

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
    UserLoginInfo userInfo;
    CommsManager commsManager;

    private FragmentController fragmentArray[] = new FragmentController[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        Intent i = getIntent();
        userInfo = (UserLoginInfo)i.getSerializableExtra("userLoginInfo");
        commsManager = LoginActivity.getInstance().getCommsManager();
        commsManager.addReadingEventListener(this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);




    }

    private void setTemp(final int room, final float temp){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(fragmentArray[room] != null)
                    fragmentArray[room].setTemp(temp);
                else
                    tempArray[room] = temp;
            }
        });


    }
    private void setHum(final int room, final float hum){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(fragmentArray[room] != null)
                    fragmentArray[room].setHum(hum);
                else
                    humArray[room] = hum;

            }
        });


    }
    private void setLight(final int room, final float state){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(fragmentArray[room] != null)
                    fragmentArray[room].setLight(state);
                else
                    lightArray[room] = state;

            }
        });

    }
    private void setFire(final int room, final float state){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(fragmentArray[room] != null)
                    fragmentArray[room].setFire(state);
                else
                    fireArray[room] = state;

            }
        });


    }
    private void setMotion(final int room, final float state){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(fragmentArray[room] != null)
                    fragmentArray[room].setMotion(state);
                else
                    motionArray[room] = state;

            }
        });


    }


    @Override
    public void bulbSetAction(float value, int deviceId) {
        byte[] message = CommsProtocol.createLightStateMessage(value,deviceId);
        try {
            commsManager.sendToMaster(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ReadingsReceived(byte[] readings) {
        SensorReadings sReadings = CommsProtocol.processReadingMessage(readings);
        processReadings(sReadings,sReadings.getDeviceId()-1);
    }

    private void processReadings(SensorReadings readings, int deviceId)
    {
        if(readings == null)
            return;

        if(readings.getCount() > 0)
        {
            for(SensorReading reading : readings.getReadings())
            {
                switch(reading.getType())
                {
                    case Light:
                    {
                        processLightReading(reading, deviceId);
                        break;
                    }
                    case Humidity:
                    {
                        processHumidityReading(reading, deviceId);
                        break;
                    }
                    case Thermo:
                    {
                        processThermoReading(reading, deviceId);
                        break;
                    }
                    case Smoke:
                    {
                        processSmokeReading(reading, deviceId);
                        break;
                    }
                    case Motion:
                    {
                        processMotionReading(reading, deviceId);
                        break;
                    }
                }
            }
        }
    }
    private void processLightReading(SensorReading reading, int deviceId)
    {
        try
        {
            setLight(deviceId, reading.getValue());
        }
        catch(NullPointerException e)
        {
        }
    }

    private void processSmokeReading(SensorReading reading, int deviceId)
    {
        try
        {
            setFire(deviceId, reading.getValue());
        }
        catch(NullPointerException e)
        {
        }
    }



    private void processMotionReading(SensorReading reading, int deviceId)
    {
        try
        {
            setMotion(deviceId, reading.getValue());
        }
        catch(NullPointerException e)
        {
        }
    }


    private void processThermoReading(SensorReading reading, int deviceId)
    {
        try
        {
            setTemp(deviceId, reading.getValue());
        }
        catch(NullPointerException e)
        {
        }
    }

    private void processHumidityReading(SensorReading reading, int deviceId)
    {
        try
        {
            setHum(deviceId,reading.getValue());
        }
        catch(NullPointerException e)
        {
        }
    }



/*
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
*/


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
        public int getCount() { return fragmentAmount; }

        @Override
        public CharSequence getPageTitle(int position) {
            //TODO change to room names sent from master
            switch (position) {
                case 0:
                    return userInfo.getRoomNames()[0];
                case 1:
                    return userInfo.getRoomNames()[1];
                case 2:
                    return userInfo.getRoomNames()[2];
                case 3:
                    return userInfo.getRoomNames()[3];
                case 4:
                    return userInfo.getRoomNames()[4];
                case 5:
                    return userInfo.getRoomNames()[5];
            }
            return null;
        }
    }
}
