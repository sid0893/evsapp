package evsapp.sid.com.evsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;


public class DisplayResult extends Activity {

    TextView display;
    HashMap<String,String[]> pollutants;
    String query=null;
    Bitmap mDataBitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        pollutants = new HashMap<>();
        display = (TextView)findViewById(R.id.resultDisplay);
        display.setMovementMethod(new ScrollingMovementMethod());
        Intent mIntent = getIntent();
        String[] stateAndCity = mIntent.getStringArrayExtra(MainActivity.STATE_AND_CITY);

        query = "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=D.C.E.&StateId=6&CityId=85";  //HOMEPAGE OF THE WEBSITE
        new ReadWebpageContents().execute(query);
        display.setText("Table\t"+stateAndCity[0]+" : "+stateAndCity[1]+"\n");

    }

    private class ReadWebpageContents extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(params[0]).get();
                return doc;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            Element table = document.getElementById("Td1").select("table").get(0); //selecting first table
            Elements rows = table.select("tr");
            Element row = rows.get(0);
            Elements cols = row.select("td");
            //String[][] pollutants = new String[rows.size()][3];
           /* pollutants[0][0] = cols.get(0).text();
            pollutants[0][1] = cols.get(3).text();
            pollutants[0][2] = cols.get(5).text();
            */
            //display.append(cols.get(0).text()+"\t"+cols.get(3).text()+"\t"+cols.get(5).text());
            //display.append("\n");
            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skipping it.
                row = rows.get(i);
                cols = row.select("td");
                pollutants.put(cols.get(0).text(),new String[]{cols.get(3).text()+" "+cols.get(4).text(),cols.get(5).text()});
                /*display.append(cols.get(0).text()+"\t"+cols.get(3).text()+" "+cols.get(4).text()+"\t"+cols.get(5).text());
                display.append("\n");*/
            }
            display.append(document.html());

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_result, menu);
        return true;
    }

   /* private void shareDataImage(){

        if(mDataBitmap==null)
            mDataBitmap = takeScreenshot();
        Intent shareData = new Intent(Intent.ACTION_SEND,);
        shareData.putExtra()
        shareData.setType("images/*");

    }*/

    private Bitmap takeScreenshot(){
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_refresh : new ReadWebpageContents().execute(query);mDataBitmap=null;return true;
           // case R.id.action_share_item : shareDataImage();return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
