/** Simple HTTP Client for Android
 * */

package com.example.httpconnector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{    
	Button getButton;
    Button postButton;
    TextView outputText;
 
    //TODO Replace the local server URL 
    public static final String URL = "http://192.168.2.126:8000";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        findViewsById();
 
        getButton.setOnClickListener(this);
        postButton.setOnClickListener(this);
    }
 
    private void findViewsById() {
        getButton = (Button) findViewById(R.id.button1);
        postButton = (Button) findViewById(R.id.button2);
        outputText = (TextView) findViewById(R.id.outputTxt);
    }
    
//    //default
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
// 
//        findViewsById();
// 
//        getButton.setOnClickListener(this);
//    }
// 
//    private void findViewsById() {
//        getButton = (Button) findViewById(R.id.button1);
//        outputText = (TextView) findViewById(R.id.outputTxt);
//    }
// 
    
    public void onClick(View view) {
    	switch(view.getId()){
    	case R.id.button1:
    	//do stuff
    	GetXMLTask task = new GetXMLTask();
        task.execute(new String[] { URL });
    	break;
    	
    	case R.id.button2:
    	//do stuff
    	println (URL);	
    	break;
    	}
    	GetXMLTask task = new GetXMLTask();
         
        task.execute(new String[] { URL });
    }
 
    private class GetXMLTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }
        
 // Do a get for android to actually retrieve the text from the server page
        private String getOutputFromUrl(String url) {
            String output = null;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                output = EntityUtils.toString(httpEntity);
        
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output;
        }
 
        @Override
        protected void onPostExecute(String output) {
            outputText.setText(output);
        }
    }
}