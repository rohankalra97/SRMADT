package rohan.srmadt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Photos extends AppCompatActivity {

    private static String urlString;
    public TextView tv1;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        tv1 = (TextView) findViewById(R.id.Tv);
        imageView=(ImageView) findViewById(R.id.imageView);
        tv1.setText(tv1.getText() + "\n\n");
        urlString = "http://jsonplaceholder.typicode.com/photos";
        new ProcessJSON().execute(urlString);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

    private class ProcessJSON extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            HTTPHandler hh = new HTTPHandler();
            stream = hh.GetHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String stream) {
            if (stream != null) {
                try {
                    JSONArray jsonArray = new JSONArray(stream);

                    //for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(1);
                        String title = jsonObject.getString("title");
                        String id = jsonObject.getString("id");
                        String albumid = jsonObject.getString("albumId");
                        String url1 = jsonObject.getString("url");
                        String url = jsonObject.getString("thumbnailUrl");
                        tv1.setText(tv1.getText() +"\n Title "+title+"\n id "+id+"\n AlbumId " + albumid + "\n Id " + id + "\n Url " + url1 + "\n\n");
                        //imageView.setImageBitmap(getBitmapFromURL(url));
                        new GetBitmap().execute(url);
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class GetBitmap extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... strings) {
            try {
                String string = strings[0];
                Log.e("src", string);
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(string);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                InputStream imgStream = httpResponse.getEntity().getContent();
                Bitmap myBitmap = BitmapFactory.decodeStream(imgStream);
                Log.e("Bitmap", "returned");
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Exception", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap stream) {
            if(stream != null)
            imageView.setImageBitmap(stream);
        }

    }


}