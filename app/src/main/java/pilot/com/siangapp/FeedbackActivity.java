package pilot.com.siangapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
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

public class FeedbackActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        final String server = settings.getString("server", "server");
        final FeedbackView adapter1;
        final ListView lv;

        final List<FeedbackData> lsd ;
        lsd = new ArrayList<FeedbackData>();

        adapter1 = new FeedbackView(getApplicationContext(), lsd);
        lv = (ListView) findViewById(R.id.feedlist);
        lv.setAdapter(adapter1);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                DefaultHttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost("http://"+ server + "/SiangApp/getfeedback.php");

                SharedPreferences settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                final String name = settings.getString("name", "User");
                String webmail = settings.getString("webmail", "Nomail");
                final String roll = settings.getString("roll", "0");

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                nameValuePair.add(new BasicNameValuePair("email", webmail));
                nameValuePair.add(new BasicNameValuePair("name", name));
                nameValuePair.add(new BasicNameValuePair("roll", roll));


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
                                Toast.makeText(getApplicationContext(), "Problem in updating. Try again" , Toast.LENGTH_SHORT).show();

                            }else if(data[0].equals("success")){

                                for(int i=1;i<data.length;i++){

                                    String [] entity = data[i].split("~~~");



                                    String from = entity[0];
                                    String text = entity[1];
                                    String choice1 = entity[2];
                                    String choice2 = entity[3];
                                    String id = entity[4];

                                    FeedbackData uc = new FeedbackData(id,from,text,choice1, choice2,roll);
                                    lsd.add(uc);
                                    Toast.makeText(getApplicationContext(), uc.text, Toast.LENGTH_SHORT).show();




                                }

                                adapter1.notifyDataSetChanged();
                                lv.setAdapter(adapter1);


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
