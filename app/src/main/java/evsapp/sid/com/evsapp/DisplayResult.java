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
    String query = null;
    Bitmap mDataBitmap = null;
    Toolbar mToolbar;
    TableLayout tableLayout;
    TableRow.LayoutParams params;
    Calendar c;
    int seconds;
    String filePath = Environment.getExternalStorageDirectory()
            + File.separator + "Pictures/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
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
        display = (TextView) findViewById(R.id.resultDisplay);
        //display.setMovementMethod(new ScrollingMovementMethod());
        Intent mIntent = getIntent();
        String centreName = mIntent.getStringExtra(MainActivity.STATE_AND_CITY);
        String[] cityAndCentre=null;
        if(centreName.contains(":")){
            cityAndCentre = centreName.split(": ");
        }else{
            cityAndCentre = new String[2];
            cityAndCentre[0] = "Delhi";
            cityAndCentre[1] = centreName;
        }
        params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;

        query = "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=D.C.E.&StateId=6&CityId=85";  //HOMEPAGE OF THE WEBSITE
        new ReadWebpageContents().execute("http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=Sanjay%20Palace&StateId=28&CityId=253");
        //display.setText("Table\t" + stateAndCity[0] + " : " + stateAndCity[1] + "\n");


        display.setText("Air Pollution Levels of: " + cityAndCentre[1] + "," + cityAndCentre[0]);
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


            for (int i = 1; i < rows.size()-1; i++) { //first row is the col names so skipping it.
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
                new ReadWebpageContents().execute(query);
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
