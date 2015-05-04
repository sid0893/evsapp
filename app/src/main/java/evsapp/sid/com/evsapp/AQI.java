package evsapp.sid.com.evsapp;

import android.support.v7.app.ActionBarActivity;

import java.util.HashMap;

/**
 * Created by manan on 18-04-2015.
 */
public class AQI {

    HashMap<String, double[]> map = new HashMap<>();

    public AQI(){
        initialisemap();
    }

    public void initialisemap() {
        map.put("Ozone", new double[]{138, 178, 220, 262, 790, 1070, 1280});
        map.put("PM 10", new double[]{54, 154, 254, 354, 424, 504, 604});
        map.put("PM 2.5", new double[]{15.4, 40.4, 65.4, 150.4, 250.4, 350.4, 500.4});
        map.put("Carbon Monoxide", new double[]{5.43, 11.6, 15.3, 19.0, 37.5, 49.8, 62.5});
        map.put("Sulfur Dioxide", new double[]{95, 406, 632, 857, 1700, 2280, 2832});
        map.put("Nitrogen Dioxide", new double[]{0, 0, 0, 1320, 2510, 3320, 4130});
        map.put("AQI", new double[]{50, 100, 150, 200, 250, 300, 350});

    }

    public int getAQI(double I_low, double I_high, double C_low, double C_high, double C){
        return (int)(((I_high - I_low)/(C_high - C_low))*(C - C_low) + I_low);

    }

    public int getIndex(String str,double val){
        double high=0,low=0,I_high=0,I_low=0;
        for(int i=0;i<7;i++) {
            if (map.containsKey(str)) {
                if (map.get(str)[i] >= val) {
                    high = map.get(str)[i];
                    I_high = map.get("AQI")[i];
                    if (i != 0) {
                        low = map.get(str)[i - 1] + 1;
                        I_low = map.get("AQI")[i - 1] + 1;
                    } else {
                        low = 0;
                        I_low = 0;
                    }
                    break;
                }
            }
        }
        return getAQI(I_low, I_high, low, high, val);

    }


}
