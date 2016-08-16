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
        tv1.setText(tv1.getText() + "\n\n");
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
            if (stream != null) {
                try {
                    JSONArray jsonArray = new JSONArray(stream);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id =jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String username =jsonObject.getString("username");
                        String email =jsonObject.getString("email");
                        JSONObject address=jsonObject.getJSONObject("address");
                        JSONObject geo=address.getJSONObject("geo");
                        String street=address.getString("street");
                        String suite=address.getString("suite");
                        String city=address.getString("city");
                        String zipcode=address.getString("zipcode");
                        String lat =geo.getString("lat");
                        String lng=geo.getString("lng");
                        String phone=jsonObject.getString("phone");
                        String website=jsonObject.getString("website");
                        JSONObject company=jsonObject.getJSONObject("company");
                        String cname=company.getString("name");
                        String cphase=company.getString("catchPhrase");
                        String bs=company.getString("bs");

                        tv1.setText(tv1.getText() +"\n Id "+ id+"\n Name "+ name+"\n Username "+ username+"\n Email "+email+"\n Street "+street+"\n Suite "+suite+"\n City "+city+"\n Zipcode "+zipcode+"\n Latitude "+lat+"\n Longitude "+lng+"\n Phone "+phone+"\n Website "+website+"\n Company Name "+cname+"\n Company Phase "+cphase+"\n Buisness "+bs+"\n\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}