package dk.cngroup.m_calendar;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.independentsoft.exchange.ServiceException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.StringTokenizer;

import java.net.MalformedURLException;
import java.net.URL;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@EActivity(R.layout.activity_rssfeed)
public class RssfeedActivity extends Activity {

    @ViewById(R.id.rss)
    TextView rss;

    String rssResult = "";

    URL rssUrl;

    @AfterViews
    void init() {
        Log.e("SERVICE", "init");
        AsyncTask<Object, Void, String> t = new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    Log.e("SERVICE", "do in Background");
                    rssUrl = new URL("http://davinci.fmph.uniba.sk/~bachronikov2/testing/calendar1.ics");
                    StringBuilder b = new StringBuilder();
                    BufferedReader r = new BufferedReader(new InputStreamReader(rssUrl.openStream()));

                    try {
                        for (String line; (line = r.readLine()) != null; ) {
                            b.append(line).append("\n");
                        }
                    } finally {
                        r.close();
                    }
                    rssResult = b.toString();
                    return rssResult;

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String mess) {
                Log.e("SERVICE", "post execute ");
                rss.setText(mess);
            }
        };

        t.execute();

    }

}