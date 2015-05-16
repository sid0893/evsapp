package evsapp.sid.com.evsapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

/**
* Created by manan on 04-05-2015.
*/


public class AQIDisplay extends ActionBarActivity {

    HashMap<String,String> query=null;
    Toolbar mToolbar;String myQuery = null;
    ImageView mImageView;
    Bitmap myOriginalImage,overLayPin;
    AQI mAqi = new AQI();
    TextView mTextView;
    double ratio;
    //HashMap<String,Double> pollutants;


    private class ReadWebpageContents extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(params[0]).timeout(0).get();
                return doc;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            mTextView = (TextView)findViewById(R.id.aqi);
            //tableLayout = (TableLayout) findViewById(R.id.displaytable);
            Element table = document.getElementById("Td1").select("table").get(0); //selecting first table
            Elements rows = table.select("tr");
            Element row;
            Elements cols;
            // String[] header = {"Parameters", " Date", "Time", "Concentration", "Unit", "Concentration (previous 24 Hours) / Prescribed Standard" };
            //String[][] pollutants = new String[rows.size()][2];
            //pollutants = new HashMap<>();

            // display.append(cols.get(0).text()+"\t"+cols.get(3).text()+"\t"+cols.get(5).text());
            // display.append("\n");
            int max=-1;
            int index;
            for (int i = 2; (i < rows.size())&&(i<8); i++) { //first row is the col names so skipping it.
                //row=null;cols=null;
                row = rows.get(i);
                cols = row.select("td");
                Log.d("into aqi","now");
                index = mAqi.getIndex(cols.get(0).text(),Double.parseDouble(cols.get(3).text()));
                if(max<index){
                    max = index;
                    Log.d("max",""+max);
                }
                else{
                    Log.d("max is more than index",max+"");
                    //mTextView.setText("AQI is: "+ max);
                }

            }
            mTextView.setText("AQI is: "+ max);
            Display disp = getWindowManager().getDefaultDisplay();
            int w = mImageView.getWidth();
            int h = mImageView.getHeight();
            ratio = max/350 * h ;

            overLayPin = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_place);  //BMP(GREY COLOR) TO DISPLAY OUR LOCATION
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inMutable = true;
            myOriginalImage = BitmapFactory.decodeResource(getResources(), R.drawable.aqi_bar,opt);
            Canvas canvas = new Canvas(myOriginalImage);
            Paint paint = new Paint();


            canvas.drawBitmap(overLayPin,  (float)mImageView.getTop(),(float)ratio, paint);

            mImageView.setImageBitmap(myOriginalImage);

            //display.append(document.html());

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aqi);
        mTextView = (TextView)findViewById(R.id.aqi);
        mImageView = (ImageView)findViewById(R.id.bar);
        mTextView.setText("Calculating AQI, wait... ");
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(AQIDisplay.this);
            }
        });
        query = new HashMap<>();
        query.put("D.C.E." ,"http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=D.C.E.&StateId=6&CityId=85");
        query.put("Civil Lines","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Civil5Lines&StateId=6&CityId=85");
        query.put("Dilshad Garden",
                "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Ihbas&StateId=6&CityId=85");
        query.put("Punjabi Bagh",
                "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Punjabi5Bagh&StateId=6&CityId=85");
        query.put("I.S.B.T. Anand Vihar",
                "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Anand5Vihar&StateId=6&CityId=85");
        query.put("Shadipur",
                "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Shadipur&StateId=6&CityId=85");
        query.put("Mandi Marg",
                "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Mandir5Marg&StateId=6&CityId=85");
        query.put( "I.T.O.",
                 "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=ITO&StateId=6&CityId=85");
        query.put("Dwarka",
               "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Dwarka&StateId=6&CityId=85");
        query.put("I.G.I Airport","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=IGI5Airport&StateId=6&CityId=85");
        query.put("R.K. Puram","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=R5K5Puram&StateId=6&CityId=85");
        query.put("Sector16A Faridabad","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Sector16A%20Faridabad&StateId=9&CityId=365");
        query.put("Sanjay Palace","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Sanjay%20Palace&StateId=28&CityId=253");
        query.put("Talkatora","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Talkatora&StateId=28&CityId=256");
        query.put("Lalbagh","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Lalbagh&StateId=28&CityId=256");
        query.put("Central School","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Central%20School&StateId=28&CityId=256");
        query.put("Nehru Nagar","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Nehru%20Nagar&StateId=28&CityId=278");
        query.put("Ardhali Bazar","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Ardhali%20Bazar&StateId=28&CityId=270");
        query.put("Maninagar","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Maninagar&StateId=8&CityId=337");
        query.put("Navi Mumbai Municipal Corporation AIROLI","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=NMMC%20Airoli&StateId=16&CityId=310");
        query.put("Chandrapur","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Chandrapur&StateId=16&CityId=329");
        query.put("SanathNagar","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Hyderabad&StateId=1&CityId=7");
        query.put("BTM","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=BTM&StateId=13&CityId=136");
        query.put("Peenya","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Peenya&StateId=13&CityId=136");
        query.put("BWSSB Kadabesanahalli","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=BWSSB&StateId=13&CityId=136");
        query.put("Alandur Bus Depot","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Alandur%20&StateId=25&CityId=546");
        query.put("IIT","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=IIT&StateId=25&CityId=546");
        query.put("Manali","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Manali&StateId=25&CityId=546");


        Intent mIntent = getIntent();
        String centreName = mIntent.getStringExtra(MainActivity.STATE_AND_CITY);
        String[] cityAndCentre=null;
        if(centreName.contains(": ")) {
            cityAndCentre = centreName.split(": ");

            myQuery = query.get(cityAndCentre[1]);
            Log.d(cityAndCentre[1],myQuery);
        }else{
            myQuery = query.get(centreName);
        }
//        Log.d("query",cityAndCentre[1]);
         new ReadWebpageContents().execute(myQuery);


        myOriginalImage = BitmapFactory.decodeResource(getResources(), R.drawable.aqi_bar);
        mImageView.setImageBitmap(myOriginalImage);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aqidisplay, menu);
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
}
