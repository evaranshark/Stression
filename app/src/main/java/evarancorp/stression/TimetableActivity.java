package evarancorp.stression;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimetableActivity extends AppCompatActivity {
    public ArrayList<LessonEntry> entries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        toolbar.setTitle(intent.getStringExtra("date"));
        String dayRef = intent.getStringExtra("link");
        XmlPullParser parser = getResources().getXml(R.xml.timetable);
        try {
            entries = new ArrayList<>();
            LessonEntry entry = new LessonEntry();
            boolean flag = false;
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT && !flag) {
                if (parser.getEventType() == XmlPullParser.START_TAG &&
                        parser.getName().equals("link"))
                if (parser.getAttributeValue(null, "ref").equals(dayRef))
                {
                    while (parser.getEventType()!=XmlPullParser.END_TAG || !parser.getName().equals("link")) {
                        switch (parser.getEventType()) {
                            case XmlPullParser.START_TAG:
                                String name = parser.getName();
                                if (name.equals("lesson"))
                                    entry = new LessonEntry();
                                else if (name.equals("time"))
                                    entry.setTime(parser.nextText());
                                else if (name.equals("course"))
                                    entry.setCourse(parser.nextText());
                                else if (name.equals("aud"))
                                    entry.setAud(parser.nextText());
                                else if (name.equals("type"))
                                    entry.setType(parser.nextText());
                                break;
                            case XmlPullParser.END_TAG:
                                if (parser.getName().equals("lesson"))
                                    entries.add(entry);
                                break;
                        }
                        parser.next();
                    }
                    flag = true;

                }
                parser.next();
            }
        }
        catch (Throwable e)
        {

        }

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                entries.size());
        Map<String, Object> m;
        for (LessonEntry entry : entries) {
            m = new HashMap<String, Object>();
            m.put("time", entry.getTime());
            m.put("course", entry.getCourse());
            m.put("aud", entry.getAud());
            data.add(m);
        }

        String[] from = {"time", "course", "aud"};
        int[] to = {R.id.timeT, R.id.courseT, R.id.audT };
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.list_tt_item, from, to);
        ListView tt_list = (ListView) findViewById(R.id.tt_list);
        tt_list.setAdapter(adapter);

    }

}
