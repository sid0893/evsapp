package evsapp.sid.com.evsapp;

/**
 * Created by hp on 07-04-2015.
 */

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hp1 on 28-12-2014.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public LocationManager service;

    private String mNavTitles[];
    private int mIcons[];
    Context context;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int Holderid;
        TextView textView;
        ImageView imageView;
        Context mContext;

        public ViewHolder(View itemView, int ViewType, Context c) {
            super(itemView);
            mContext = c;

            if (ViewType == TYPE_ITEM) {
                itemView.setClickable(true);
                itemView.setOnClickListener(this);
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                Holderid = 1;
            } else {
                Holderid = 0;
            }
        }


        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "The Item Clicked is: " + MainActivity.navDrawer[getPosition() - 1], Toast.LENGTH_SHORT).show();
            int id = getPosition();
            switch (id) {
                case 1:
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                    break;
                case 2:
                    mContext.startActivity(new Intent(mContext, LocationBased.class));
                    break;
                case 3:
                    mContext.startActivity(new Intent(mContext, AboutUs.class));

                    break;
                case 4:

                    break;

            }


        }
    }


    MyAdapter(String Titles[], int Icons[], Context con) {
        mNavTitles = Titles;
        mIcons = Icons;
        context = con;

    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

            ViewHolder vhItem = new ViewHolder(v, viewType, context);

            return vhItem;

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);

            ViewHolder vhHeader = new ViewHolder(v, viewType, context);

            return vhHeader;


        }
        return null;

    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        if (holder.Holderid == 1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.textView.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
            holder.imageView.setImageResource(mIcons[position - 1]);// Settimg the image with array of our icons
        }
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return mNavTitles.length + 1; // the number of items in the list will be +1 the titles including the header view.
    }


    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
