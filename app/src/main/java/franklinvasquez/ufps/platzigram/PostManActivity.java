package franklinvasquez.ufps.platzigram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class PostManActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_man);

        new  MyHttpAsyncTask().execute("https://jsonplaceholder.typicode.com/users");
    }

    private class MyHttpAsyncTask extends AsyncTask<String,Void,String>{

        InputStream is = null;
        String json ="";
        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(urls[0]);

                HttpResponse httpResponse = httpClient.execute(httpget);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }catch (ClientProtocolException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuffer sb = new StringBuffer();
                String line = null;
                while((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            }catch (Exception e){
                Log.e("Buffer Error","Error converting result "+ e.toString());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            try{
                JSONArray jArrrayObject = new JSONArray(result);

                //JSONObject jObject = new JSONObject(result);

            }catch (JSONException e){
                Log.e("JSON Parser","Error parsing data" + e.toString());
            }
        }
    }
}
