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

public class Comments extends AppCompatActivity {


    private static String urlString;
    public TextView tv1;
    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        tv1 = (TextView) findViewById(R.id.Tv);
        tv1.setText(tv1.getText() + "\n\n");
        db=openOrCreateDatabase("CommentsDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Comments(PostId VARCHAR, Name VARCHAR, Email VARCHAR, Body VARCHAR);");
        urlString = "http://jsonplaceholder.typicode.com/comments";
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
                        String postid = jsonObject.getString("postId");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String body = jsonObject.getString("body");
                        tv1.setText(tv1.getText() + "\n PostID " + postid + "\n Name " + name + "\n Email " + email + "\n Body " + body + "\n\n");
                        db.execSQL("INSERT INTO Comments VALUES('"+postid+"','"+name+"','"+email+"','"+body+"');");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
