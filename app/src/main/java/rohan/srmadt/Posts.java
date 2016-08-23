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

import java.sql.SQLData;

public class Posts extends AppCompatActivity {

    private static String urlString;
    public TextView tv1;
    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        db=openOrCreateDatabase("PostsDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Posts(UserId VARCHAR,Id VARCHAR,Title VARCHAR,Body VARCHAR);");
        tv1 = (TextView) findViewById(R.id.Tv);
        tv1.setText(tv1.getText() + "\n\n");
        urlString = "http://jsonplaceholder.typicode.com/posts";
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
                        String userid=jsonObject.getString("userId");
                        String id=jsonObject.getString("id");
                        String title =jsonObject.getString("title");
                        String body =jsonObject.getString("body");
                        tv1.setText(tv1.getText()+"\n UserID "+userid+"\n Id "+id+"\n Title "+title+"\n Body "+body+"\n\n");
                        db.execSQL("INSERT INTO Posts VALUES('"+userid+"','"+id+"','"+title+"','"+body+"');");
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
