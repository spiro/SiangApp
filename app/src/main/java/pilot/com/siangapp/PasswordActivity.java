package pilot.com.siangapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Intent i = this.getIntent();
        Bundle b = i.getExtras();

        String name = b.getCharSequence("name").toString();
        final String webmail = b.getString("webmail");

        TextView txt = (TextView)findViewById(R.id.textView);

        txt.setText("Welcome" + name);

        final EditText edt = (EditText)findViewById(R.id.editText);
        Button btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String password = edt.getText().toString();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DefaultHttpClient httpClient = new DefaultHttpClient();

                        HttpPost httpPost = new HttpPost("http://10.42.0.1/SiangApp/password.php");



                        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                        nameValuePair.add(new BasicNameValuePair("password",password ));
                        nameValuePair.add(new BasicNameValuePair("webmail",webmail ));


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
                                        Toast.makeText(getApplicationContext(), "Something went wrong" , Toast.LENGTH_SHORT).show();

                                    }else if(data[0].equals("success")){


                                        Intent i = new Intent(PasswordActivity.this, PasswordActivity.class);
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


}
