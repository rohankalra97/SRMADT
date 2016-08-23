package rohan.srmadt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Photos extends AppCompatActivity {

    private static String urlString;
    public TextView tv1;
    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        tv1 = (TextView) findViewById(R.id.Tv1);
        tv1.setText(tv1.getText() + "\n\n");
        db=openOrCreateDatabase("PhotosDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Photos(Title VARCHAR,Id VARCHAR,AlbumId VARCHAR,Url VARCHAR,Thumbnail_url VARCHAR);");
        urlString = "http://jsonplaceholder.typicode.com/photos";
        new ProcessJSON().execute(urlString);
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
                    for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    String id = jsonObject.getString("id");
                    String albumid = jsonObject.getString("albumId");
                    String url1 = jsonObject.getString("url");
                    String url = jsonObject.getString("thumbnailUrl");
                    tv1.setText(tv1.getText() +"\n Title "+title+"\n id "+id+"\n AlbumId " + albumid +"\n Url " + url1 + "Thumbnail Url"+url+"\n\n");
                    db.execSQL("INSERT INTO Photos VALUES('"+title+"','"+id+"','"+albumid+"','"+url1+"','"+url+"');");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
