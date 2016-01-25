package dk.cngroup.m_calendar;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

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
    boolean isBooked = true;

    @ViewById(R.id.rss)
    TextView rss;

    String rssResult = "";
    boolean item = false;

    URL rssUrl;

    @AfterViews
    void init(){
        final Context context = this;
        Log.e("SERVICE","init  ");



        AsyncTask<Object, Void, String> t = new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    Log.e("SERVICE", "do in background");
                    rssUrl = new URL("http://www.javaworld.com/index.xml");
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    XMLReader xmlReader = saxParser.getXMLReader();

                    RSSHandler rssHandler = new RSSHandler();
                    xmlReader.setContentHandler(rssHandler);
                    InputSource inputSource = new InputSource(rssUrl.openStream());
                    xmlReader.parse(inputSource);


                    return rssResult;




                }  catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                catch (IOException e) {return e.getMessage();
                } catch (SAXException e) {return e.getMessage();
                } catch (ParserConfigurationException e) {return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute (String mess){
                Log.e("SERVICE", "post" +
                        " execute ");
                rss.setText(mess);
            }
        };

        t.execute();



   /*     try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            RSSHandler rssHandler = new RSSHandler();
            xmlReader.setContentHandler(rssHandler);
            InputSource inputSource = new InputSource(rssUrl.openStream());
            xmlReader.parse(inputSource);

        } catch (IOException e) {rss.setText(e.getMessage());
        } catch (SAXException e) {rss.setText(e.getMessage());
        } catch (ParserConfigurationException e) {rss.setText(e.getMessage());
        }

        rss.setText(rssResult);*/
    }
    /**public String removeSpaces(String s) {
     StringTokenizer st = new StringTokenizer(s," ",false);
     String t="";
     while (st.hasMoreElements()) t += st.nextElement();
     return t;
     }*/
    private class RSSHandler extends DefaultHandler {

        public RSSHandler() {
            super();
        }


        public void startElement(String uri, String localName, String qName,
                                 Attributes attrs) throws SAXException {
            if (localName.equals("item"))
                item = true;

            if (!localName.equals("item") && item == true)
                rssResult = rssResult + localName + ": ";

        }

        public void endElement(String namespaceURI, String localName,
                               String qName) throws SAXException {

        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {
            String cdata = new String(ch, start, length);
            if (item == true)
                rssResult = rssResult + (cdata.trim()).replaceAll("\\s+", " ") + "\t";
        }


    }
}