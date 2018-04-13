package evarancorp.stression;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrollingActivity extends AppCompatActivity {
    static ArrayList<DateEntry> entries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ListView dl = (ListView) findViewById(R.id.dates_list);
        final Intent intent = new Intent(this, TimetableActivity.class);
        dl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                LinearLayout ll = (LinearLayout) itemClicked;
                TextView linkView = (TextView) ll.getChildAt(2);
                String strText = linkView.getText().toString(); // получаем текст нажатого элемента
                intent.putExtra("link", strText);
                intent.putExtra("date", ((TextView)ll.getChildAt(0)).getText().toString());
                startActivity(intent);

            }
        });
        fillList();
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                entries.size());
        Map<String, Object> m;
        for (DateEntry entry : entries) {
            m = new HashMap<String, Object>();
            m.put("date", entry.getDate());
            m.put("dow", entry.getDow());
            m.put("link", entry.getLink());
            data.add(m);
        }

        String[] from = {"date", "dow", "link"};
        int[] to = {R.id.text1, R.id.text2, R.id.linkText };
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.list_date_item, from, to);
        dl.setAdapter(adapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    private void fillList() {
        XmlPullParser parser = getResources().getXml(R.xml.dates_list);
        try {
        entries = new ArrayList<>();



        DateEntry tmpEntry = new DateEntry();
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (parser.getEventType()) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("day"))
                            tmpEntry = new DateEntry();
                        else if (parser.getName().equals("date"))
                            tmpEntry.setDate(parser.nextText());
                        else if (parser.getName().equals("dow"))
                            tmpEntry.setDow(parser.nextText());
                        else if (parser.getName().equals("link"))
                            tmpEntry.setLink(parser.nextText());
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("day"))
                            entries.add(tmpEntry);
                        break;
                    default:
                        break;
                }
                parser.next();
            }
        }
        catch (Exception e)
        {}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
