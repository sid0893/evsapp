package evsapp.sid.com.evsapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.apache.http.protocol.HTTP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


import static android.widget.AdapterView.OnItemSelectedListener;



public class MainActivity extends ActionBarActivity implements OnItemSelectedListener {

    public static String[] navDrawer;
    int[] navDrawerIcons;
    DrawerLayout mDrawerLayout;
    //ListView mListView;
    HashMap<String,ArrayList<String>> stateToCity;
    //HashMap<String,ArrayList<String>> cityToDistrict = new HashMap<>();
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    AnimationDrawable backAnimation;
    RecyclerView.LayoutManager mLayoutManager;

    private CharSequence mTitle;
    private android.support.v7.app.ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mToolbar;
    public static final String STATE_AND_CITY = "1";
    public static final String JSON = "2";
    Spinner stateList, cityList;
    Button getPollutionData = null;
    Button getAQI = null;
    ArrayAdapter<CharSequence> mStateArrayAdapter = null, mCityArrayAdapter = null ;
    ArrayList<String> cities = null;
    ArrayList<String> districts = null;
    ArrayList<String> delhi = null;
    ArrayList<String> uttar_pradesh = null;
    //ArrayList<String> andhra_pradesh = null;
    ArrayList<String> telangana = null;
    //ArrayList<String> bihar = null;
    ArrayList<String> gujarat = null;
    ArrayList<String> haryana = null;
    ArrayList<String> karnataka = null;
    ArrayList<String> maharashtra = null;
    ArrayList<String> tamil_nadu = null;
    String centreName=null;
    public static Jsontodata mJsontodata=null;

//    ArrayList<String> NewDelhi = null;
//    ArrayList<String> Agra=null,Lucknow=null,Kanpur=null,Varanasi=null;
//    ArrayList<String> Hyderabad = null;
//    ArrayList<String> Ahmedabad = null;
//    ArrayList<String> Faridabad = null;
//    ArrayList<String> Bangalore = null;
//    ArrayList<String> Mumbai=null,Chandrapur=null;
//    ArrayLsist<String> Chennai = null;




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //display = (TextView)findViewById(R.id.resultDisplay);
        switch (parent.getId()) {
            case R.id.states_list: /*display.setText("state selected");*/
                setCityList(parent.getSelectedItem().toString());
                //String s = parent.getSelectedItem().toString();
                //Log.d("select state",s);
                break;

            case R.id.city_list: /*display.setText("city selected");*/
                centreName= parent.getSelectedItem().toString();

                //Log.d("select city",s1);
                break;
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    private void setDistrictList(String city){
//        districts.clear();
//        districts.addAll(cityToDistrict.get(city));
//        /*switch (position) {
//            case 0:
//                districts.addAll(telangana);
//                break;
//            case 1:
//                districts.addAll(delhi);
//                break;
//            case 2:
//                districts.addAll(gujarat);
//                break;
//            case 3:
//                districts.addAll(haryana);
//                break;
//            case 4:
//                districts.addAll(karnataka);
//                break;
//            case 5:
//                districts.addAll(maharashtra);
//                break;
//            case 6:
//                districts.addAll(tamil_nadu);
//                break;
//            case 7:
//                districts.addAll(uttar_pradesh);
//                break;
//
//        }*/
//
//        //mCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mDistrictArrayAdapter.notifyDataSetChanged();
//
//    }
    private void setCityList(String state) {

        cities.clear();
        cities.addAll(stateToCity.get(state));
//        switch (position) {
//            case 0:
//                cities.addAll(telangana);
//                break;
//            case 1:
//                cities.addAll(delhi);
//                break;
//            case 2:
//                cities.addAll(gujarat);
//                break;
//            case 3:
//                cities.addAll(haryana);
//                break;
//            case 4:
//                cities.addAll(karnataka);
//                break;
//            case 5:
//                cities.addAll(maharashtra);
//                break;
//            case 6:
//                cities.addAll(tamil_nadu);
//                break;
//            case 7:
//                cities.addAll(uttar_pradesh);
//                break;
//
//        }

        //mCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCityArrayAdapter.notifyDataSetChanged();
    }

    private void initialiseStates(){

        delhi = new ArrayList<>(Arrays.asList("Delhi : D.C.E.", "Delhi : Shadipur", "Delhi : I.T.O.", "Delhi : Dilshad Garden",
                "Delhi : Dwarka","Delhi : Punjabi Bagh","Delhi : I.G.I Airport",
                "Delhi : I.S.B.T. Anand Vihar","Delhi : Mandir Marg","Delhi : Civil Lines","Delhi : R.K. Puram"));
        stateToCity.put("DELHI",delhi);
        uttar_pradesh = new ArrayList<>(Arrays.asList("Agra : Sanjay Palace", "Lucknow : Talkatora",
                "Lucknow : Lalbagh","Lucknow : Central School","Kanpur : Central School",
                "Varanasi : Ardhali Bazar"));
        stateToCity.put("UTTAR PRADESH",uttar_pradesh);
        telangana = new ArrayList<>(Arrays.asList("Hyderabad : SanathNagar"));
        stateToCity.put("TELANGANA",telangana);
        //bihar = new ArrayList<>(Arrays.asList("Patna"));
        gujarat = new ArrayList<>(Arrays.asList("Ahmedabad : Maninagar"));
        stateToCity.put("GUJARAT",gujarat);
        haryana = new ArrayList<>(Arrays.asList("Faridabad : Sector16A Faridabad"));
        stateToCity.put("HARYANA",haryana);
        karnataka = new ArrayList<>(Arrays.asList("Bangalore : BTM","Bangalore : Peenya",
                "Bangalore : BWSSB Kadabesanahalli"));
        stateToCity.put("KARNATAKA",karnataka);
        maharashtra = new ArrayList<>(Arrays.asList("Mumbai : Navi Mumbai Municipal Corporation AIROLI",
                "Chandrapur : Chandrapur"));
        stateToCity.put("MAHARASHTRA",maharashtra);
        tamil_nadu = new ArrayList<>(Arrays.asList("Chennai : Alandur Bus Depot",
                "Chennai : IIT","Chennai : Manali"));
        stateToCity.put("TAMIL NADU",tamil_nadu);
        cities.addAll(telangana);
        mCityArrayAdapter.notifyDataSetChanged();
    }
//    private void initialiseCities() {
//
//        NewDelhi = new ArrayList<>(Arrays.asList("D.C.E.", "Shadipur", "I.T.O.", "Dilshad Garden", "Dwarka","Punjabi Bagh","I.G.I Airport",
//                "I.S.B.T. Anand Vihar","Mandir Marg","Civil Lines","R.K. Puram"));
//        cityToDistrict.put("New Delhi",NewDelhi);
//        Agra = new ArrayList<>(Arrays.asList("Sanjay Palace"));
//        cityToDistrict.put("Agra",Agra);
//        Lucknow = new ArrayList<>(Arrays.asList("Talkatora","Lalbagh","Central School"));
//        cityToDistrict.put("Lucknow",Lucknow);
//        Kanpur  = new ArrayList<>(Arrays.asList(""Nehru Nagar));
//        cityToDistrict.put("Kanpur",Kanpur);
//        Varanasi = new ArrayList<>(Arrays.asList("Ardhali Bazar"));
//        cityToDistrict.put("Varanasi",Varanasi);
//        Hyderabad = new ArrayList<>(Arrays.asList("SanathNagar"));
//        cityToDistrict.put("Hyderabad",Hyderabad);
//        Ahmedabad = new ArrayList<>(Arrays.asList("Maninagar"));
//        cityToDistrict.put("Ahmedabad",Ahmedabad);
//        Faridabad = new ArrayList<>(Arrays.asList("Sector16A Faridabad"));
//        cityToDistrict.put("Faridabad",Faridabad);
//        Bangalore = new ArrayList<>(Arrays.asList("BTM","Peenya","BWSSB Kadabesanahalli"));
//        cityToDistrict.put("Bangalore",Bangalore);
//        Mumbai = new ArrayList<>(Arrays.asList("Navi Mumbai Municipal Corporation AIROLI"));
//        cityToDistrict.put("Mumbai",Mumbai);
//        Chandrapur = new ArrayList<>(Arrays.asList("Chandrapur"));
//        cityToDistrict.put("Chandrapur",Chandrapur);
//        Chennai = new ArrayList<>(Arrays.asList("Alandur Bus Depot","IIT","Manali"));
//        cityToDistrict.put("Chennai",Chennai);
//        districts.addAll(Hyderabad);
//        mDistrictArrayAdapter.notifyDataSetChanged();
//   }

    private void initialiseDropDownMenus() {
        districts = new ArrayList<>();
        cities = new ArrayList<>();
        stateList = (Spinner) findViewById(R.id.states_list);
        cityList = (Spinner) findViewById(R.id.city_list);
        //districtList = (Spinner)findViewById(R.id.district_list);
        mStateArrayAdapter = ArrayAdapter.createFromResource(this, R.array.states_list, R.layout.spinner);
        mStateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateList.setAdapter(mStateArrayAdapter);

        mCityArrayAdapter = new ArrayAdapter(this, R.layout.spinner, cities);
        mCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityList.setAdapter(mCityArrayAdapter);

//        mDistrictArrayAdapter = new ArrayAdapter(this,R.layout.spinner,districts);
//        mDistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        districtList.setAdapter(mDistrictArrayAdapter);

        stateList.setOnItemSelectedListener(this);
        cityList.setOnItemSelectedListener(this);
        //districtList.setOnItemSelectedListener(this);
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
        mJsontodata = new Jsontodata(getApplicationContext());
        mJsontodata.startDataDownload();

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
        getAQI = (Button)findViewById(R.id.buttonGetAQI);
        stateToCity= new HashMap<>();
        //cityToDistrict = new HashMap<>();
        initialiseDropDownMenus();
        initialiseStates();
        //initialiseCities();


        getPollutionData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String[] stateAndCity = new String[]{stateList.getSelectedItem().toString(), cityList.getSelectedItem().toString()};
                Intent displayResult = new Intent(getApplicationContext(), DisplayResult.class);
                displayResult.putExtra(STATE_AND_CITY, centreName);

                startActivity(displayResult);
            }
        });
        getAQI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent disp = new Intent(getApplicationContext(),AQIDisplay.class);
                disp.putExtra(STATE_AND_CITY, centreName);
                startActivity(disp);
            }
        });
        //LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.fragment_container);

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


        return super.onOptionsItemSelected(item);
    }


}
