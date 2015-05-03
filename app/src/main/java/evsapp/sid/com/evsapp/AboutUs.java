package evsapp.sid.com.evsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.aboutlibraries.Libs;


public class AboutUs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_about_us);
        new Libs.Builder()
                //Pass the fields of your application to the lib so it can find all external lib information
                .withFields(R.string.class.getFields()).withAboutIconShown(true)
                .withAboutVersionShown(true)
                .withAboutDescription("This is an app to show real time pollution data and Air Quality Index.<br /><b>Try it </b>")
                        //start the activity
                .start(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
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
