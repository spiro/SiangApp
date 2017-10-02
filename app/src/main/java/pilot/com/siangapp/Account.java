package pilot.com.siangapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
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

public class Account extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText name;
    EditText department;
    EditText contact;
    Button update, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        final String name2 = settings.getString("name", "User");
        //String webmail = settings.getString("webmail", "Nomail");
        final String server = settings.getString("server","Address");

        name = (EditText)findViewById(R.id.name);
        name.setText(name2);


        department = (EditText)findViewById(R.id.department);
        contact = (EditText)findViewById(R.id.contact);
        update = (Button)findViewById(R.id.update);
        logout = (Button)findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = "http://"+server+"/SiangApp/logout.php";
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DefaultHttpClient httpClient = new DefaultHttpClient();

                        HttpPost httpPost = new HttpPost(url);

                        SharedPreferences settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                        final String name = settings.getString("name", "User");


                        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                        nameValuePair.add(new BasicNameValuePair("name", name));


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


                                    String data = response;

                                    if (data.equals("failure")) {
                                        Toast.makeText(getApplicationContext(), "Something went wrong. Try Again!", Toast.LENGTH_SHORT).show();

                                    } else if (data.equals("success")) {


                                        Toast.makeText(getApplicationContext(), "Logged Out Sucessfully.", Toast.LENGTH_SHORT).show();
                                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("status", "loggedout");
                                        Intent i = new Intent(Account.this, Login.class);
                                        startActivity(i);
                                        MainActivity.ma.finish();
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
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Name = name.getText().toString();
                final String Department= department.getText().toString();
                final String Contact = contact.getText().toString();
                if(Name.equals("") ){
                    Toast.makeText(getApplicationContext(),"Name is empty", Toast.LENGTH_SHORT).show();

                }else if(Department.equals("")){
                    Toast.makeText(getApplicationContext(),"Roll Number is empty" , Toast.LENGTH_LONG).show();
                }
                else if(Contact.equals("")){
                    Toast.makeText(getApplicationContext(),"Contact is empty" , Toast.LENGTH_LONG).show();
                }else {

                    boolean connect = haveNetworkConnection();
                    if (connect == false) {
                        Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_LONG).show();
                    }
                else{
                    final String url = "http://"+server+"/SiangApp/account.php";

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DefaultHttpClient httpClient = new DefaultHttpClient();

                            HttpPost httpPost = new HttpPost(url);

                            SharedPreferences settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                            final String name = settings.getString("name", "User");
                            String webmail = settings.getString("webmail", "Nomail");


                            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                            nameValuePair.add(new BasicNameValuePair("name", Name));
                            nameValuePair.add(new BasicNameValuePair("rollno", Department));
                            nameValuePair.add(new BasicNameValuePair("contact", Contact));
                            nameValuePair.add(new BasicNameValuePair("name", name));
                            nameValuePair.add(new BasicNameValuePair("webmail", webmail));


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


                                        String data = response;

                                        if (data.equals("failure")) {
                                            Toast.makeText(getApplicationContext(), "Something went wrong. Try Again!", Toast.LENGTH_SHORT).show();

                                        } else if (data.equals("success")) {

                                            Toast.makeText(getApplicationContext(), "Updated Sucessfully.", Toast.LENGTH_SHORT).show();
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
            }
        });

    }



    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
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