package evsapp.sid.com.evsapp.Utils;

/**
 * Created by manan on 30-04-2015.
 */

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static RequestQueue mQueue;
    private static ImageLoader mImageLoader;
    private static VolleySingleton mInstance = null;


    private VolleySingleton(Context mContext) {
        mQueue = Volley.newRequestQueue(mContext);
    }

    public static VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }


    public static RequestQueue getReqQueue(Context context) {
        if (mQueue == null)
            mQueue = Volley.newRequestQueue(context);


        return mQueue;
    }


}