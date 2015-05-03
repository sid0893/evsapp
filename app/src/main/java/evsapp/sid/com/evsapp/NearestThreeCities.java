package evsapp.sid.com.evsapp;


import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class NearestThreeCities extends Fragment {

    //RadioButton r1,r2,r3;
    /*RadioGroup mRadioGroup;
    Button mButton;
    Location current;
    HashMap<String,Location> listOfLocations = new HashMap<>();
    public NearestThreeCities() {
        // Required empty public constructor
    }

    IN-DL': [
    {latLng: [28.719804828832736,77.25068104282566], name: 'Dilshad Garden'},
    {latLng: [28.783376811716238,77.00896146131728], name:'D.C.E'},
    {latLng: [28.585246821204954,76.90802361409402], name:'Dwarka'}
    ],
            'IN-UP': [
    {latLng: [27.161830820078162,77.83037160828435], name: 'Agra'},
    {latLng: [27.111786805400317,79.79768552106408], name:'Lucknow'},
    {latLng: [26.303126672921213,79.75353238460825], name:'Kanpur'},
    {latLng: [25.371813455349116,82.89064557518128], name:'Varanasi'}

    ],
            'IN-HR' : [
    {latLng: [28.294864650453857,77.34307227196392], name:'Faridabad'}
    ],

            'IN-GJ' : [
    {latLng: [22.51450643032689,71.76962942369802], name:'Ahmedabad'}
    ],

            'IN-AP' : [
    {latLng: [17.050299940948165,78.69975191254275], name:'Hyderabad'}
    ],


            'IN-KA' : [
    {latLng: [13.053009776010425,77.47333750835979], name:'Bangalore'}
    ],

            'IN-TN' : [
    {latLng: [12.810340886808428,80.01802839488053], name:'Chennai'}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mData = getArguments();
        current = mData.getParcelable(LocationBased.TAG);
        listOfLocations.put("Dilshad Garden",)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nearest_three_cities, container, false);
        mRadioGroup = (RadioGroup)rootView.findViewById(R.id.myRadioGroup);
        *//*r1 = (RadioButton)rootView.findViewById(R.id.radioButton);
        r2 = (RadioButton)rootView.findViewById(R.id.radioButton2);
        r3 = (RadioButton)rootView.findViewById(R.id.radioButton3);*//*
        mButton = (Button)rootView.findViewById(R.id.buttonGetData);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(mRadioGroup.getCheckedRadioButtonId()){

                    case R.id.radioButton:
                    case R.id.radioButton2:
                    case R.id.radioButton3:
                }
            }
        });
        return rootView;
    }

    private Location nearestCity(){

    }

*/
}
