package rohan.srmadt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Users extends AppCompatActivity {

    private static String urlString;
    public TextView tv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        tv1 = (TextView) findViewById(R.id.Tv);
        tv1.setText("");
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
            tv1.setText("reached");
            if (stream != null) {
                tv1.setText("reached 2");
                try {
                    tv1.setText(tv1.getText() + "We are processing the JSON data....\n\n");
                    JSONArray jsonArray = new JSONArray(stream);
                        /*JSONObject reader = new JSONObject(stream);
                        String id = reader.getString("id");
                        String name = reader.getString("name");
                        String username = reader.getString("username");

                        tv1.setText(tv1.getText() + id);
                        tv1.setText(tv1.getText() + name);
                        tv1.setText(tv1.getText() + username);
                    */
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        tv1.setText(tv1.getText() + name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}