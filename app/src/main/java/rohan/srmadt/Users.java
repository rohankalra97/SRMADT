package rohan.srmadt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Users extends AppCompatActivity {

    public TextView tv;
    private static String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView tv = (TextView) findViewById(R.id.tv);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        urlString = "http://jsonplaceholder.typicode.com/users";
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
                TextView tv = (TextView) findViewById(R.id.tv);
                if (stream != null) {
                    try {
                        JSONObject reader = new JSONObject(stream);
                        String id = reader.getString("id");
                        String name = reader.getString("name");
                        String username = reader.getString("username");
                        tv.setText("We are processing the JSON data....\n\n");
                        tv.setText(tv.getText() + id);
                        tv.setText(tv.getText() + name);
                        tv.setText(tv.getText() + username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }