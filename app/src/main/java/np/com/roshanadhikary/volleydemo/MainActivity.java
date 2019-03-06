package np.com.roshanadhikary.volleydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    EditText etUrl;
    Button btnFetch;
    TextView tvResponse;
    String url;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUrl = findViewById(R.id.etUrl);
        btnFetch = findViewById(R.id.btnFetch);
        tvResponse = findViewById(R.id.tvResponse);

        queue = Volley.newRequestQueue(this);


        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url= etUrl.getText().toString();
                Toast.makeText(MainActivity.this, url+"", Toast.LENGTH_SHORT).show();
                Log.i("hello", ""+url);
                tvResponse.setText("");
                JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Iterator<String> iterator = response.keys();
                            while(iterator.hasNext()) {
                                String key = iterator.next();
                                String value = response.get(key).toString();
                                addToTV(key, value);
                            }
                        }
                        catch (Exception e)
                        {
                            tvResponse.setText("Exception occurred! " + e.getMessage());
                        }
                    }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            tvResponse.setText("Cannot GET " + error.getMessage());
                        }
                    });

                //Adding to the request queue actually makes the GET request
                queue.add(request);
            }
        });
    }
    public void addToTV(String key, String value) {
        String currentText = tvResponse.getText().toString();
        tvResponse.setText(currentText + "\n\n" + key +": " + value);
    }
}
