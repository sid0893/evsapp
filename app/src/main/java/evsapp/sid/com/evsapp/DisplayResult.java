package evsapp.sid.com.evsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;


public class DisplayResult extends ActionBarActivity {

    private final static String TAG = "DisplayResult";
    TextView display;
    //HashMap<String,String[]> pollutants;
    String query = null;
    Bitmap mDataBitmap = null;
    Toolbar mToolbar;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        mToolbar = (Toolbar) findViewById(R.id.disp_toolbar);
        setSupportActionBar(mToolbar);
        //pollutants = new HashMap<>();
        display = (TextView) findViewById(R.id.resultDisplay);
        display.setMovementMethod(new ScrollingMovementMethod());
        Intent mIntent = getIntent();
        String[] stateAndCity = mIntent.getStringArrayExtra(MainActivity.STATE_AND_CITY);

        query = "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=D.C.E.&StateId=6&CityId=85";  //HOMEPAGE OF THE WEBSITE
        new ReadWebpageContents().execute(query);
        display.setText("Table\t" + stateAndCity[0] + " : " + stateAndCity[1] + "\n");

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
            Element row = rows.get(0);
            Elements cols = row.select("td");
            // String[] header = {"Parameters", " Date", "Time", "Concentration", "Unit", "Concentration (previous 24 Hours) / Prescribed Standard" };
            String[][] pollutants = new String[rows.size()][3];


            // display.append(cols.get(0).text()+"\t"+cols.get(3).text()+"\t"+cols.get(5).text());
            // display.append("\n");

            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skipping it.
                row = rows.get(i);
                cols = row.select("td");
                //pollutants[i][0] = cols.get(0).text();
                // pollutants[i][1] = cols.get(3).text();
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
                tv_i2.setText(cols.get(3).text());
                tv_i3.setText(cols.get(5).text());
                Log.d(TAG, tv_i1.toString() + "\n" + tv_i2.toString() + "\n" + tv_i3.toString());
                tr.addView(tv_i1);
                tr.addView(tv_i2);
                tr.addView(tv_i3);
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

    private Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
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
                new ReadWebpageContents().execute(query);
                mDataBitmap = null;
                return true;
            // case R.id.action_share_item : shareDataImage();return true;
            case R.id.action_share_data:
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, "share app link or some text");
                share.setType(HTTP.PLAIN_TEXT_TYPE);
                if (share.resolveActivity(getPackageManager()) != null) {
                    startActivity(share);
                }

        }

        return super.onOptionsItemSelected(item);
    }
}
