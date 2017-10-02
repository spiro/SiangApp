package pilot.com.siangapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    Button btn ;
    EditText username;
    EditText password;
    EditText Server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn = (Button)findViewById(R.id.button2);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        Server = (EditText)findViewById(R.id.server);
        Server.setText("192.168.0.7");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = username.getText().toString();
                final String pass = password.getText().toString();
                final String server = Server.getText().toString();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DefaultHttpClient httpClient = new DefaultHttpClient();

                        HttpPost httpPost = new HttpPost("http://" + server + "/SiangApp/login.php");



                        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                        nameValuePair.add(new BasicNameValuePair("id", id));
                        nameValuePair.add(new BasicNameValuePair("password", pass));


                        try {
                            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                        }
                        catch (UnsupportedEncodingException e) {
                            // writing error to Log
                            e.printStackTrace();
                        }

                        try {
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            final String response = httpClient.execute(httpPost, responseHandler);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String[] data  = response.split("::");
                                    if(data[0].equals("failure")){
                                        Toast.makeText(getApplicationContext(), "Authentication failed" , Toast.LENGTH_SHORT).show();

                                    }else if(data[0].equals("success")){

                                        String name = data[1];
                                        Intent i = new Intent(Login.this, MainActivity.class);
                                        i.putExtra("name",name);
                                        //i.putExtra("server",server);
                                        //i.putExtra("webmail",email);

                                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("status", "loggedin");
                                        editor.putString("rollno", id);
                                        editor.putString("name", name);
                                        editor.putString("server",server);
                                        //editor.putString("webmail", email);
                                        editor.apply();

                                        startActivity(i);
                                        finish();

                                    }

                                }
                            });

                            Log.d("Http Response:", response);

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });


                t.start();



            }


        });


    }




//    private  postData throws JSONException {
//// Create a new HttpClient and Post Header
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpGet httppost = new HttpGet("http://.../serverFile.php");
//        JSONObject json = new JSONObject();
//
//        try {
//            json.put("longitude", longitude);//place each of the strings as you did in postData method
//            json.put("latitude", latitude);
//
//
//            JSONArray postjson=new JSONArray();
//            postjson.put(json);
//            post.setHeader("json",json.toString());
//            post.getParams().setParameter("jsonpost",postjson);
//            HttpResponse response = httpclient.execute(httppost);
//
//            // for JSON retrieval:
//            if(response != null)
//            {
//                InputStream is = response.getEntity().getContent();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//                StringBuilder sb = new StringBuilder();
//                String line = null;
//                try {
//                    while ((line = reader.readLine()) != null) {
//                        sb.append(line + "\n");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        is.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                String jsonStr = sb.toString(); //take the string you built place in a string
//
//
//
//                JSONObject rec = new JSONObject(jsonStr);
//                String longitudecord = rec.getString("lon");
//                String latitudecord = rec.getString("lat");
//                // ...
//            }
//        }catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//        }
//    }

}

