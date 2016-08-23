package rohan.srmadt;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Usersdb extends AppCompatActivity {
    public SQLiteDatabase db;
    public TextView tt;
    public Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersdb);
        db=openOrCreateDatabase("UsersDB", Context.MODE_PRIVATE, null);
        tt=(TextView) findViewById(R.id.textView4) ;
        c=db.rawQuery("SELECT * FROM Users", null);
        if(c.getCount()==0)
        {
            tt.setText("no records downloaded yet");
            return;
        }
        StringBuffer buffer=new StringBuffer();
        while(c.moveToNext())
        {
            buffer.append("Id: "+c.getString(0)+"\n");
            buffer.append("Name: "+c.getString(1)+"\n");
            buffer.append("Username: "+c.getString(2)+"\n");
            buffer.append("Email: "+c.getString(3)+"\n");
            buffer.append("Street: "+c.getString(4)+"\n");
            buffer.append("Suite: "+c.getString(5)+"\n");
            buffer.append("City: "+c.getString(6)+"\n");
            buffer.append("Zipcode: "+c.getString(7)+"\n");
            buffer.append("Latitude: "+c.getString(8)+"\n");
            buffer.append("Longitude: "+c.getString(9)+"\n");
            buffer.append("Phone: "+c.getString(10)+"\n");
            buffer.append("Website: "+c.getString(11)+"\n");
            buffer.append("Company Name: "+c.getString(12)+"\n");
            buffer.append("Company Phase: "+c.getString(13)+"\n");
            buffer.append("Buisness: "+c.getString(14)+"\n\n");

        }
        tt.setText(buffer.toString());

    }
}
