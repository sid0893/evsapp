package evsapp.sid.com.evsapp;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.Arrays;


import static android.widget.AdapterView.OnItemSelectedListener;


public class MainActivity extends ActionBarActivity implements OnItemSelectedListener {

    public static String[] navDrawer;
    int[] navDrawerIcons;
    DrawerLayout mDrawerLayout;
    //ListView mListView;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    AnimationDrawable backAnimation;
    RecyclerView.LayoutManager mLayoutManager;

    private CharSequence mTitle;
    private android.support.v7.app.ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mToolbar;
    public static final String STATE_AND_CITY = "1";
    Spinner stateList, cityList;
    Button getPollutionData = null;
    ArrayAdapter<CharSequence> mStateArrayAdapter = null, mCityArrayAdapter = null;
    ArrayList<String> cities = null;
    ArrayList<String> delhi = null;
    ArrayList<String> uttar_pradesh = null;
    ArrayList<String> andhra_pradesh = null;
    ArrayList<String> bihar = null;
    ArrayList<String> gujarat = null;
    ArrayList<String> haryana = null;
    ArrayList<String> karnataka = null;
    ArrayList<String> maharashtra = null;
    ArrayList<String> tamil_nadu = null;


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //display = (TextView)findViewById(R.id.resultDisplay);
        switch (parent.getId()) {
            case R.id.states_list: /*display.setText("state selected");*/
                setCityList(position);
                break;

            case R.id.city_list: /*display.setText("city selected");*/
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setCityList(int position) {

        cities.clear();
        switch (position) {
            case 0:
                cities.addAll(andhra_pradesh);
                break;
            case 1:
                cities.addAll(bihar);
                break;
            case 2:
                cities.addAll(delhi);
                break;
            case 3:
                cities.addAll(gujarat);
                break;
            case 4:
                cities.addAll(haryana);
                break;
            case 5:
                cities.addAll(karnataka);
                break;
            case 6:
                cities.addAll(maharashtra);
                break;
            case 7:
                cities.addAll(tamil_nadu);
                break;
            case 8:
                cities.addAll(uttar_pradesh);
                break;
        }

        //mCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCityArrayAdapter.notifyDataSetChanged();
    }

    private void initialiseStates() {
        cities = new ArrayList<>();
        delhi = new ArrayList<>(Arrays.asList("D.C.E.", "Shadipur", "I.T.O.", "Dilshad Garden", "Dwarka"));
        uttar_pradesh = new ArrayList<>(Arrays.asList("Agra", "Lucknow", "Kanpur", "Varanasi"));
        andhra_pradesh = new ArrayList<>(Arrays.asList("Hyderabad"));
        bihar = new ArrayList<>(Arrays.asList("Patna"));
        gujarat = new ArrayList<>(Arrays.asList("Ahmedabad"));
        haryana = new ArrayList<>(Arrays.asList("Faridabad"));
        karnataka = new ArrayList<>(Arrays.asList("Bangalore"));
        maharashtra = new ArrayList<>(Arrays.asList("Mumbai"));
        tamil_nadu = new ArrayList<>(Arrays.asList("Chennai"));
        cities.addAll(delhi);
    }

    private void initialiseDropDownMenus() {
        stateList = (Spinner) findViewById(R.id.states_list);
        cityList = (Spinner) findViewById(R.id.city_list);
        mStateArrayAdapter = ArrayAdapter.createFromResource(this, R.array.states_list, R.layout.spinner);
        mStateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateList.setAdapter(mStateArrayAdapter);

        mCityArrayAdapter = new ArrayAdapter(this, R.layout.spinner, cities);
        mCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityList.setAdapter(mCityArrayAdapter);
        stateList.setOnItemSelectedListener(this);
        cityList.setOnItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        mTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mActionBarDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle);
                // Code here will execute once drawer is closed
            }

        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        navDrawer = new String[]{"Select City", "Based On Current Location", "About Us"};
        navDrawerIcons = new int[]{R.drawable.ic_action_place, R.drawable.ic_action_location_found, R.drawable.ic_action_about};
        //mListView = (ListView)findViewById(R.id.left_drawer);
        mRecyclerView = (RecyclerView) findViewById(R.id.left_drawer);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(navDrawer, navDrawerIcons, this);

        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getPollutionData = (Button) findViewById(R.id.buttonGetData);

        initialiseStates();
        initialiseDropDownMenus();

        getPollutionData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] stateAndCity = new String[]{stateList.getSelectedItem().toString(), cityList.getSelectedItem().toString()};
                Intent displayResult = new Intent(getApplicationContext(), DisplayResult.class);
                displayResult.putExtra(STATE_AND_CITY, stateAndCity);
                startActivity(displayResult);
            }
        });

        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.fragment_container);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //    backAnimation.start();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* private class DrawerItemClickListener implements RecyclerView.OnClickListener {
         @Override
         public void onClick(View v) {
             Toast mToast = Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT);
             mToast.show();
         }
     }*/
       /* @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           *//**//* mListView.setItemChecked(position,true);
            if(position==0)
                setTitle(R.string.app_name);
            else
                setTitle(navDrawer[position]);
            if(position==3){
                Intent mIntent = new Intent(getApplicationContext(),AboutUs.class);
                startActivity(mIntent);
            }*//**//*
            switch(position){
                case 3: Intent mIntent = new Intent(getApplicationContext(),AboutUs.class);
                        startActivity(mIntent);break;

            }
            mDrawerLayout.closeDrawer(mRecyclerView);
        }*//*
    }
*/
    @Override
    public void setTitle(CharSequence title) {
        //mTitle = title;
        //getSupportActionBar().setTitle(title);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar wild
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (id == R.id.action_share_app) {
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT, "share app link or some text");
            share.setType(HTTP.PLAIN_TEXT_TYPE);
            if (share.resolveActivity(getPackageManager()) != null) {
                startActivity(share);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
