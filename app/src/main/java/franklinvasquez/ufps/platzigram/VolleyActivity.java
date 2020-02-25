package franklinvasquez.ufps.platzigram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;

public class VolleyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        ImageView imageView = (ImageView) findViewById(R.id.my_image_view);

        Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nombre","Franklin");
        editor.putString("apellido","Vasquez");
        editor.putString("pais","Colombia");
        editor.putString("idioma","Español");
        editor.putInt("edad",25);
        editor.commit();

        String nombre = sharedPreferences.getString("nombre","");
        int edad = sharedPreferences.getInt("edad",0);

        Toast.makeText(this,"edad: "+edad+" nombre: "+nombre,Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/photos", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("JSON", "Respuesta" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error","Error Respuesta en JSON: " + error.getMessage());
                    }
                });
        queue.add(jsonArrayRequest);
    }
}
