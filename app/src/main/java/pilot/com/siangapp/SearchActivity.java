package pilot.com.siangapp;

import android.app.Dialog;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
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

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SearchView scrh ;
    Spinner spinner ;
    ArrayAdapter<CharSequence> adapter;
    SearchView1 adapter1;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        final String server = settings.getString("server", "server");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final List<String> listItem = new ArrayList<String>();

        spinner = (Spinner) findViewById(R.id.spinner2);
        adapter = ArrayAdapter.createFromResource(this, R.array.search, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


//        lv = (ListView)findViewById(R.id.listview);
//        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
//        lv.setAdapter(adapter1);

        final List<SearchData> lsd;
        lsd = new ArrayList<SearchData>();

        adapter1 = new SearchView1(getApplicationContext(), lsd);
        lv = (ListView) findViewById(R.id.listview);
        lv.setAdapter(adapter1);

        scrh = (SearchView) findViewById(R.id.searchView);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    scrh.setQueryHint("Enter Name");
                } else if (position == 1) {
                    scrh.setQueryHint("Enter Roll No.");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        scrh.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                final String category = spinner.getSelectedItem().toString();
                final String value = query;

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DefaultHttpClient httpClient = new DefaultHttpClient();

                        HttpPost httpPost = new HttpPost("http://" +server + "/SiangApp/search.php");


                        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                        nameValuePair.add(new BasicNameValuePair("category", category));
                        nameValuePair.add(new BasicNameValuePair("value", value));


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
//                                    LayoutInflater divyanshInflater = LayoutInflater.from(getApplicationContext());
//                                    View customView = divyanshInflater.inflate(R.layout.custom_row, lv.getRootView().getParent(),false);

                                    String[] data = response.split("::");
                                    if (data[0].equals("failure")) {
                                        Toast.makeText(getApplicationContext(), "No entry found", Toast.LENGTH_SHORT).show();

                                    } else if (data[0].equals("success")) {

                                        lsd.clear();
                                        adapter1.notifyDataSetChanged();
                                        for (int i = 1; i < data.length; i++) {

                                            String[] entry = data[i].split("~~~");

                                            Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT);
                                            String name = entry[0];
                                            String phoneno = entry[1];
                                            SearchData sd = new SearchData(name, phoneno);
                                            lsd.add(sd);

                                        }

                                        adapter1.notifyDataSetChanged();
                                        Toast.makeText(getApplicationContext(), data.length+"results found", Toast.LENGTH_LONG).show();


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

                lv.setAdapter(adapter1);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                        final String book = (String) lsd.get(position).phoneno;

                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DefaultHttpClient httpClient = new DefaultHttpClient();

                                HttpPost httpPost = new HttpPost("http://" +server + "/SiangApp/search.php");


                                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                                nameValuePair.add(new BasicNameValuePair("roomNo", book));



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
//                                    LayoutInflater divyanshInflater = LayoutInflater.from(getApplicationContext());
//                                    View customView = divyanshInflater.inflate(R.layout.custom_row, lv.getRootView().getParent(),false);

                                            String[] data = response.split("::");
                                            if (data[0].equals("failure")) {
                                                Toast.makeText(getApplicationContext(), "Connection Problem", Toast.LENGTH_SHORT).show();

                                            } else if (data[0].equals("success")) {



                                                for (int i = 1; i < data.length; i++) {

                                                    String[] entry = data[i].split("~~~");

                                                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT);
                                                    String Name = entry[0];
                                                    String Room = entry[1];
                                                    String Contact = entry[2];
                                                    String Department = entry[3];
                                                    final String Batch = entry[4];


                                                    final Dialog dialog = new Dialog(getApplicationContext());
                                                    dialog.setContentView(R.layout.popupscreenbooks);
                                                    dialog.setTitle("Title...");
                                                    // set the custom dialog components - text, image and button
                                                    TextView text = (TextView) dialog.findViewById(R.id.text);
                                                    text.setText(Name);
                                                    TextView text1 = (TextView) dialog.findViewById(R.id.text1);
                                                    text1.setText(Room );
                                                    TextView text2 = (TextView) dialog.findViewById(R.id.text2);
                                                    text2.setText(Contact);
                                                    TextView text3 = (TextView) dialog.findViewById(R.id.text3);
                                                    text3.setText(Department);
                                                    TextView text4 = (TextView) dialog.findViewById(R.id.text4);
                                                    text4.setText( Batch);

                                                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                                                    // if button is clicked, close the custom dialog

                                                    dialogButton.setText("Close");


                                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                                dialog.dismiss();

                                                        }
                                                    });

                                                    dialog.show();
                                                }




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

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
