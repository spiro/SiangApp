package pilot.com.siangapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class ComplaintActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final int REQUEST_CAPTURE_IMAGE = 1;
    ImageView imageView3;
    Spinner spn ;
    EditText  sub;
    EditText descp;
    Button post;
    ImageButton imagebtn;

//    public void launchMain(){
//
//        Intent i = new Intent(getApplication(),MainActivity.class);
//        startActivity(i);
//        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        ImageButton capture = (ImageButton) findViewById(R.id.imageButton3);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        if(!hasCamera()){
            capture.setEnabled(false);
        }




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        final String server = settings.getString("server", "server");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        spn = (Spinner)findViewById(R.id.spinner);
        spn.setPrompt("Choose your category");
        descp = (EditText) findViewById(R.id.Description);
        sub = (EditText)findViewById(R.id.editText3);
        post = (Button)findViewById(R.id.button);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String subject = sub.getText().toString();
                final String description = descp.getText().toString();
                final String category = spn.getSelectedItem().toString();
                if(subject.equals("") ){
                    Toast.makeText(getApplicationContext(),"Subject is empty", Toast.LENGTH_SHORT).show();

                }else if(description.equals("")){
                    Toast.makeText(getApplicationContext(),"Description is empty" , Toast.LENGTH_LONG).show();
                }else {

                    final String url = "http://"+ server +"/SiangApp/complaint.php";

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DefaultHttpClient httpClient = new DefaultHttpClient();

                            HttpPost httpPost = new HttpPost(url);

                            SharedPreferences settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                            final String name = settings.getString("name", "User");
                            String webmail = settings.getString("webmail", "Nomail");



                            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                            nameValuePair.add(new BasicNameValuePair("subject", subject));
                            nameValuePair.add(new BasicNameValuePair("description", description));
                            nameValuePair.add(new BasicNameValuePair("category", category));
                            nameValuePair.add(new BasicNameValuePair("name", name));
                            nameValuePair.add(new BasicNameValuePair("webmail", webmail));


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


                                        String data  = response;

                                        if(data.equals("failure")){
                                            Toast.makeText(getApplicationContext(), "Something went wrong. Try Again!" , Toast.LENGTH_SHORT).show();

                                        }else if(data.equals("success")){

                                            Toast.makeText(getApplicationContext(), "Complaint Registered Sucessfully." , Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_developers) {
            Intent i = new Intent(getApplicationContext(),DeveloperActivity.class);
            startActivity(i);finish();
            return true;
        }
        else if(id == R.id.action_account){
            Intent i = new Intent(getApplicationContext(),Account.class);
            startActivity(i);finish();
            return true;
        }
        else if(id==R.id.action_utility){
            Intent i = new Intent(getApplicationContext(),UtilityActivity.class);
            startActivity(i);finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_complaints) {

            Intent i = new Intent(getApplicationContext(), ComplaintActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_feedback) {
            Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_issue) {
            Intent i = new Intent(getApplicationContext(), BookActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_map) {
            Intent i = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_list) {
            Intent i = new Intent(getApplicationContext(), ListsActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_hmc) {
            Intent i = new Intent(getApplicationContext(), HmcActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_search) {
            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(i);finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
