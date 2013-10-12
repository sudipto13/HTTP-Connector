/** Simple HTTP Client for Android
 * */

package com.example.httpconnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
    	Log.i ("GetButtonPressed","Success");
    	GetXMLTask task = new GetXMLTask();
        task.execute(new String[] { URL });
    	break;
    	
    	case R.id.button2:
    	//do stuff
    	Log.i ("PostButtonPressed","Success");
    	postData();	
    	break;
    	}
    	//GetXMLTask task = new GetXMLTask();
        //task.execute(new String[] { URL });
    }
 
    private void postData() {
		// TODO Auto-generated method stub
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        //HttpPost httppost = new HttpPost("http://192.168.2.126:8000");
        HttpPost httppost = new HttpPost(URL);
        Log.i("success","executed postData");
        
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", "12345"));
            nameValuePairs.add(new BasicNameValuePair("stringdata", "test data to be posted"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            //HttpResponse httppost = httpclient.execute((HttpUriRequest) httppost);
            Log.w("Success", "Execute HTTP Post Request");     
            HttpResponse response = httpclient.execute(httppost);
            
            String str = inputStreamToString(response.getEntity().getContent()).toString();
            Log.w("Success", str);
//            
//            if(str.toString().equalsIgnoreCase("true"))
//            {
//             Log.w("Success", "TRUE");
//             outputText.setText("Login successful");   
//            }else
//            {
//             Log.w("Success", "FALSE");
//             outputText.setText(str);             
//            }
 
        } catch (ClientProtocolException e) {
         e.printStackTrace();
        } catch (IOException e) {
         e.printStackTrace();
        }
	}
    private StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        // Read response until the end
        try {
         while ((line = rd.readLine()) != null) { 
           total.append(line); 
         }
        } catch (IOException e) {
         e.printStackTrace();
        }
        // Return full string
        return total;
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
    
    // Post block before merge
        // Http post request
/*		public void postData() {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.2.126:8000");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("id", "12345"));
                nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        } */
    
    }
}