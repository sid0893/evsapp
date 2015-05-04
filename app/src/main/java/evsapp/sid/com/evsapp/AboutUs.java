package evsapp.sid.com.evsapp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;


public class AboutUs extends ActionBarActivity {

    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(AboutUs.this);
            }
        });
        int listImages[] = new int[]{R.drawable.manan, R.drawable.me,
                R.drawable.steve, R.drawable.bill, R.drawable.mark};

        ArrayList<Card> cards = new ArrayList<Card>();
        String[] names = new String[]{"Manan Wason","Siddharth Jain","Abhishek Jain","Priyanshu Singh","Hammad Akhtar"};
        for (int i = 0; i<5; i++) {
            // Create a Card
            Card card = new Card(this);
            // Create a CardHeader
            CardHeader header = new CardHeader(this);
            // Add Header to card
            header.setTitle("                               "+names[i]);
            card.setTitle("              Android");
            card.setCardElevation(1000);
            card.setShadow(true);

            //card.setBackgroundResourceId(R.color.PaleTurquoise);
            card.addCardHeader(header);

            CardThumbnail thumb = new CardThumbnail(this);
            thumb.setDrawableResource(listImages[i]);
            card.addCardThumbnail(thumb);

            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, cards);

        CardListView listView = (CardListView) this.findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }
   /* public class ContactInfo {
        protected String name;
        protected String surname;
        protected String email;
        protected static final String NAME_PREFIX = "Name_";
        protected static final String SURNAME_PREFIX = "Surname_";
        protected static final String EMAIL_PREFIX = "email_";
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected TextView vTitle;

        public ContactViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vSurname = (TextView)  v.findViewById(R.id.txtSurname);
            vEmail = (TextView)  v.findViewById(R.id.txtEmail);
            vTitle = (TextView) v.findViewById(R.id.title);
        }
    }


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
    }*/
}
