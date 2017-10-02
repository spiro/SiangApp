package pilot.com.siangapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class Update extends AppCompatActivity {


    Button btn;
    static final int REQUEST_CAPTURE_IMAGE = 1;
    ImageView imageView3;
    EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        btn = (Button)findViewById(R.id.button3);
        edt = (EditText)findViewById(R.id.editText4);
        ImageButton capture = (ImageButton) findViewById(R.id.imageButton3);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        if(!hasCamera()){
            capture.setEnabled(false);
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txt = edt.getText().toString();

                if(txt.equals("")){

                    Toast.makeText(getApplicationContext(),"Please Enter Some Text to post",Toast.LENGTH_SHORT).show();
                }else {
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DefaultHttpClient httpClient = new DefaultHttpClient();

                            HttpPost httpPost = new HttpPost("http://10.42.0.1/SiangApp/update.php");

                            SharedPreferences settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                            final String name = settings.getString("name", "User");
                            String webmail = settings.getString("webmail", "Nomail");



                            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                            nameValuePair.add(new BasicNameValuePair("email", webmail));
                            nameValuePair.add(new BasicNameValuePair("name", name));
                            nameValuePair.add(new BasicNameValuePair("text",txt ));

                            try {
                                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                            } catch (UnsupportedEncodingException e) {
                                // writing error to Log
                                e.printStackTrace();
                            }

                            try {
                                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                                final String response = httpClient.execute(httpPost, responseHandler);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String[] data = response.split("::");
                                        if (data[0].equals("failure")) {
                                            Toast.makeText(getApplicationContext(), "Something went wrong..", Toast.LENGTH_SHORT).show();

                                        } else if (data[0].equals("success")) {

                                            Toast.makeText(getApplicationContext(), "Posted Suceesfully", Toast.LENGTH_SHORT).show();
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

            }
        });

    }
    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    public void launchCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CAPTURE_IMAGE && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            imageView3.setImageBitmap(photo);
        }
    }
}
