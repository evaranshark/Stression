package evarancorp.stression;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by evaran on 11.04.2018.
 */
public class DateEntry {
    public static String ns = null;
    private String date;
    private String dow;
    private String link;

    public DateEntry(String date, String dow, String link) {
        this.date = date;
        this.dow = dow;
        this.link = link;
    }

    public DateEntry() {}

    public String getDate(){
        return date;
    }

    public void setDate(String d){
        date = d;
    }

    public String getDow(){
        return dow;
    }

    public void setDow(String d){
        dow = d;
    }

    public String getLink(){
        return link;
    }

    public void setLink(String d){
        link = d;
    }
}
