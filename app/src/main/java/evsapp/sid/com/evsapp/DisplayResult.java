package evsapp.sid.com.evsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.NavUtils;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;


public class DisplayResult extends ActionBarActivity {

    private final static String TAG = "DisplayResult";
    TextView display;
    //HashMap<String,String[]> pollutants;
    String myQuery = null;
    Bitmap mDataBitmap = null;
    Toolbar mToolbar;
    TableLayout tableLayout;
    TableRow.LayoutParams params;
    Calendar c;
    HashMap<String,String> query=null;
    int seconds;
    String filePath = Environment.getExternalStorageDirectory()
            + File.separator + "Pictures/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
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
        query.put("Sanathnagar","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Hyderabad&StateId=1&CityId=7");
        query.put("BTM","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=BTM&StateId=13&CityId=136");
        query.put("Peenya","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Peenya&StateId=13&CityId=136");
        query.put("BWSSB Kadabesanahalli","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=BWSSB&StateId=13&CityId=136");
        query.put("Alandur Bus Depot","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Alandur%20&StateId=25&CityId=546");
        query.put("IIT","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=IIT&StateId=25&CityId=546");
        query.put("Manali","http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Manali&StateId=25&CityId=546");

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(DisplayResult.this);
            }
        });
        //pollutants = new HashMap<>();
        //display = (TextView) findViewById(R.id.resultDisplay);
        //display.setMovementMethod(new ScrollingMovementMethod());
        Intent mIntent = getIntent();
        String centreName = mIntent.getStringExtra(MainActivity.STATE_AND_CITY);
        String[] cityAndCentre=null;
        cityAndCentre = centreName.split(": ");

        params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;

        myQuery = query.get(cityAndCentre[1]);
        Log.d("query",myQuery);
        //query = "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=D.C.E.&StateId=6&CityId=85";  //HOMEPAGE OF THE WEBSITE
        new ReadWebpageContents().execute(myQuery);
        //display.setText("Table\t" + stateAndCity[0] + " : " + stateAndCity[1] + "\n");


        //display.setText("Air Pollution Levels of: " + cityAndCentre[1] + "," + cityAndCentre[0]);
        c = Calendar.getInstance();



    }

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
            tableLayout = (TableLayout) findViewById(R.id.displaytable);
            Element table = document.getElementById("Td1").select("table").get(0); //selecting first table
            Elements rows = table.select("tr");
            Element row;
            Elements cols;
            // String[] header = {"Parameters", " Date", "Time", "Concentration", "Unit", "Concentration (previous 24 Hours) / Prescribed Standard" };
            String[][] pollutants = new String[rows.size()][3];


            // display.append(cols.get(0).text()+"\t"+cols.get(3).text()+"\t"+cols.get(5).text());
            // display.append("\n");


            for (int i = 1; (i < rows.size())&&(i<8); i++) { //first row is the col names so skipping it.
                //row=null;cols=null;
                row = rows.get(i);
                cols = row.select("td");
                //pollutants[i][0] = cols.get(0).text();
                //pollutants[i][1] = cols.get(3).text();
                //pollutants[i][2] = cols.get(5).text();
                TableRow tr = new TableRow(getApplicationContext());
                TableRow.LayoutParams tlParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                tlParams.setMargins(2, 0, 0, 0);
                TextView tv_i1 = new TextView(getApplicationContext());
                TextView tv_i2 = new TextView(getApplicationContext());
                TextView tv_i3 = new TextView(getApplicationContext());
                tv_i1.setTextColor(getResources().getColor(R.color.Black));
                tv_i2.setTextColor(getResources().getColor(R.color.Black));
                tv_i3.setTextColor(getResources().getColor(R.color.Black));

                tv_i1.setText(cols.get(0).text());
                tv_i2.setText(cols.get(3).text()+" "+cols.get(4).text());
                tv_i3.setText(cols.get(5).text());

                Log.d(TAG, tv_i1.toString() + "\n" + tv_i2.toString() + "\n" + tv_i3.toString());
                tr.addView(tv_i1);
                tr.addView(tv_i2);
                tr.addView(tv_i3);
                tv_i1.setLayoutParams(params);
                tv_i2.setLayoutParams(params);
                tv_i3.setLayoutParams(params);
                tableLayout.addView(tr);
                //    pollutants.put(cols.get(0).text(), new String[]{cols.get(3).text() + " " + cols.get(4).text(), cols.get(5).text()});

                //display.append(cols.get(0).text()+"\t"+cols.get(3).text()+" "+cols.get(4).text()+"\t"+cols.get(5).text());
                //display.append("\n");
            }

            //display.append(document.html());

        }
    }

   /* private void shareDataImage(){

        if(mDataBitmap==null)
            mDataBitmap = takeScreenshot();
        Intent shareData = new Intent(Intent.ACTION_SEND,);
        shareData.putExtra()
        shareData.setType("images/*");

    }*/

    private void takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView = findViewById(R.id.scrollview);
        rootView.setDrawingCacheEnabled(true);
        mDataBitmap= rootView.getDrawingCache();
        //return null;
    }

    public void loadBitmapFromView() {
        View v = findViewById(R.id.scrollview);
        mDataBitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mDataBitmap);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        //return b;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_result, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_refresh:
                tableLayout.removeViews(1,1);
                new ReadWebpageContents().execute(myQuery);
                mDataBitmap = null;
                return true;
            // case R.id.action_share_item : shareDataImage();return true;
            case R.id.action_share_data:
                takeScreenshot();
                //loadBitmapFromView();
                saveBitmap(mDataBitmap);
                send(filePath);
              /*  Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, "share app link or some text");
                share.setType(HTTP.PLAIN_TEXT_TYPE);
                if (share.resolveActivity(getPackageManager()) != null) {
                    startActivity(share);
                }
*/
        }

        return super.onOptionsItemSelected(item);
    }
    public void saveBitmap(Bitmap bitmap) {

        seconds = c.get(Calendar.SECOND);
        File imagePath = new File(filePath+seconds+".jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    public void send(String path) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        intent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(intent, "Share data"));
    }
}
