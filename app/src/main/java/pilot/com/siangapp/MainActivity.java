package pilot.com.siangapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static AppCompatActivity ma ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ma = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        SharedPreferences settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        final String name = settings.getString("name", "User");
        //String webmail = settings.getString("webmail", "Nomail");
        final String server = settings.getString("server","Address");

        Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_SHORT).show();
        //TextView siang = (TextView)findViewById(R.id.username);
        //Toast.makeText(getApplicationContext(), siang.getText(), Toast.LENGTH_SHORT).show();
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Update.class);
                startActivity(i);

            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

 //       final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.swipeContainer);
//        final WebView webView = (WebView) findViewById(R.id.webView);
//         webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
//        webView.setWebViewClient(new WebViewClient());
//
//
//        webView.loadUrl("https://www.facebook.com/groups/present.siang.boarders/");
//
//        mySwipeRefreshLayout.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        final WebView webView = (WebView) findViewById(R.id.webView);
//                        webView.reload();
//                        mySwipeRefreshLayout.setRefreshing(false);
//                    }
//                }
//        );


        //webView.setHorizontalScrollBarEnabled(false);
        final UpdateView adapter1;
        final ListView lv;

        final List<UpdateContent> lsd ;
        lsd = new ArrayList<UpdateContent>();

        adapter1 = new UpdateView(getApplicationContext(), lsd);
        lv = (ListView) findViewById(R.id.updatelistview);
        lv.setAdapter(adapter1);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                DefaultHttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost("http://" + server + "/SiangApp/getupdate.php");

                SharedPreferences settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                final String name = settings.getString("name", "User");
                String webmail = settings.getString("webmail", "Nomail");
                String roll = settings.getString("roll", "0");


                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                nameValuePair.add(new BasicNameValuePair("email", webmail));
                nameValuePair.add(new BasicNameValuePair("name", name));


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

                                lsd.clear();
                                for(int i=1;i<data.length;i++){


                                    String [] entity = data[i].split("~~~");

                                    String from = entity[0];
                                    String text = entity[1];

                                    UpdateContent uc = new UpdateContent(from,text);
                                    lsd.add(uc);




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



//        lsd.clear();
//        adapter1.notifyDataSetChanged();
//        for(int i=1 ; i<data.length; i++){
//
//            String[] entry = data[i].split("~~~");
//
//            Toast.makeText(getApplicationContext(),data[i],Toast.LENGTH_SHORT);
//            String name = entry[0];
//            String phoneno = entry[1];
//            SearchData sd = new SearchData(name, phoneno);
//            lsd.add(sd);
//
//        }
//
//        adapter1.notifyDataSetChanged();

        /*DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpHost proxy = new HttpHost("202.141.80.20", 3128); //proxy that i need

        httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);

    }*/

    /*public static boolean setProxy(WebView webview, String host, int port, String applicationClassName="android.app.Application") {
        // 3.2 (HC) or lower
        if (Build.VERSION.SDK_INT <= 13) {
            return setProxyUpToHC(webview, host, port);
        }
        // ICS: 4.0
        else if (Build.VERSION.SDK_INT <= 15) {
            return setProxyICS(webview, host, port);
        }
        // 4.1-4.3 (JB)
        else if (Build.VERSION.SDK_INT <= 18) {
            return setProxyJB(webview, host, port);
        }
        // 4.4 (KK) & 5.0 (Lollipop)
        else {
            return setProxyKKPlus(webview, host, port, applicationClassName);
        }
    }

    *//**
         * Set Proxy for Android 3.2 and below.
         *//*
    @SuppressWarnings("all")
    private static boolean setProxyUpToHC(WebView webview, String host, int port) {
        Log.d(LOG_TAG, "Setting proxy with <= 3.2 API.");

        HttpHost proxyServer = new HttpHost(host, port);
        // Getting network
        Class networkClass = null;
        Object network = null;
        try {
            networkClass = Class.forName("android.webkit.Network");
            if (networkClass == null) {
                Log.e(LOG_TAG, "failed to get class for android.webkit.Network");
                return false;
            }
            Method getInstanceMethod = networkClass.getMethod("getInstance", Context.class);
            if (getInstanceMethod == null) {
                Log.e(LOG_TAG, "failed to get getInstance method");
            }
            network = getInstanceMethod.invoke(networkClass, new Object[]{webview.getContext()});
        } catch (Exception ex) {
            Log.e(LOG_TAG, "error getting network: " + ex);
            return false;
        }
        if (network == null) {
            Log.e(LOG_TAG, "error getting network: network is null");
            return false;
        }
        Object requestQueue = null;
        try {
            Field requestQueueField = networkClass
                    .getDeclaredField("mRequestQueue");
            requestQueue = getFieldValueSafely(requestQueueField, network);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "error getting field value");
            return false;
        }
        if (requestQueue == null) {
            Log.e(LOG_TAG, "Request queue is null");
            return false;
        }
        Field proxyHostField = null;
        try {
            Class requestQueueClass = Class.forName("android.net.http.RequestQueue");
            proxyHostField = requestQueueClass
                    .getDeclaredField("mProxyHost");
        } catch (Exception ex) {
            Log.e(LOG_TAG, "error getting proxy host field");
            return false;
        }

        boolean temp = proxyHostField.isAccessible();
        try {
            proxyHostField.setAccessible(true);
            proxyHostField.set(requestQueue, proxyServer);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "error setting proxy host");
        } finally {
            proxyHostField.setAccessible(temp);
        }

        Log.d(LOG_TAG, "Setting proxy with <= 3.2 API successful!");
        return true;
    }

    @SuppressWarnings("all")
    private static boolean setProxyICS(WebView webview, String host, int port) {
        try
        {
            Log.d(LOG_TAG, "Setting proxy with 4.0 API.");

            Class jwcjb = Class.forName("android.webkit.JWebCoreJavaBridge");
            Class params[] = new Class[1];
            params[0] = Class.forName("android.net.ProxyProperties");
            Method updateProxyInstance = jwcjb.getDeclaredMethod("updateProxy", params);

            Class wv = Class.forName("android.webkit.WebView");
            Field mWebViewCoreField = wv.getDeclaredField("mWebViewCore");
            Object mWebViewCoreFieldInstance = getFieldValueSafely(mWebViewCoreField, webview);

            Class wvc = Class.forName("android.webkit.WebViewCore");
            Field mBrowserFrameField = wvc.getDeclaredField("mBrowserFrame");
            Object mBrowserFrame = getFieldValueSafely(mBrowserFrameField, mWebViewCoreFieldInstance);

            Class bf = Class.forName("android.webkit.BrowserFrame");
            Field sJavaBridgeField = bf.getDeclaredField("sJavaBridge");
            Object sJavaBridge = getFieldValueSafely(sJavaBridgeField, mBrowserFrame);

            Class ppclass = Class.forName("android.net.ProxyProperties");
            Class pparams[] = new Class[3];
            pparams[0] = String.class;
            pparams[1] = int.class;
            pparams[2] = String.class;
            Constructor ppcont = ppclass.getConstructor(pparams);

            updateProxyInstance.invoke(sJavaBridge, ppcont.newInstance(host, port, null));

            Log.d(LOG_TAG, "Setting proxy with 4.0 API successful!");
            return true;
        }
        catch (Exception ex)
        {
            Log.e(LOG_TAG, "failed to set HTTP proxy: " + ex);
            return false;
        }
    }

    *//**
         * Set Proxy for Android 4.1 - 4.3.
         *//*
    @SuppressWarnings("all")
    private static boolean setProxyJB(WebView webview, String host, int port) {
        Log.d(LOG_TAG, "Setting proxy with 4.1 - 4.3 API.");

        try {
            Class wvcClass = Class.forName("android.webkit.WebViewClassic");
            Class wvParams[] = new Class[1];
            wvParams[0] = Class.forName("android.webkit.WebView");
            Method fromWebView = wvcClass.getDeclaredMethod("fromWebView", wvParams);
            Object webViewClassic = fromWebView.invoke(null, webview);

            Class wv = Class.forName("android.webkit.WebViewClassic");
            Field mWebViewCoreField = wv.getDeclaredField("mWebViewCore");
            Object mWebViewCoreFieldInstance = getFieldValueSafely(mWebViewCoreField, webViewClassic);

            Class wvc = Class.forName("android.webkit.WebViewCore");
            Field mBrowserFrameField = wvc.getDeclaredField("mBrowserFrame");
            Object mBrowserFrame = getFieldValueSafely(mBrowserFrameField, mWebViewCoreFieldInstance);

            Class bf = Class.forName("android.webkit.BrowserFrame");
            Field sJavaBridgeField = bf.getDeclaredField("sJavaBridge");
            Object sJavaBridge = getFieldValueSafely(sJavaBridgeField, mBrowserFrame);

            Class ppclass = Class.forName("android.net.ProxyProperties");
            Class pparams[] = new Class[3];
            pparams[0] = String.class;
            pparams[1] = int.class;
            pparams[2] = String.class;
            Constructor ppcont = ppclass.getConstructor(pparams);

            Class jwcjb = Class.forName("android.webkit.JWebCoreJavaBridge");
            Class params[] = new Class[1];
            params[0] = Class.forName("android.net.ProxyProperties");
            Method updateProxyInstance = jwcjb.getDeclaredMethod("updateProxy", params);

            updateProxyInstance.invoke(sJavaBridge, ppcont.newInstance(host, port, null));
        } catch (Exception ex) {
            Log.e(LOG_TAG,"Setting proxy with >= 4.1 API failed with error: " + ex.getMessage());
            return false;
        }

        Log.d(LOG_TAG, "Setting proxy with 4.1 - 4.3 API successful!");
        return true;
    }

    // from https://stackoverflow.com/questions/19979578/android-webview-set-proxy-programatically-kitkat
    @SuppressLint("NewApi")
    @SuppressWarnings("all")
    private static boolean setProxyKKPlus(WebView webView, String host, int port, String applicationClassName) {
        Log.d(LOG_TAG, "Setting proxy with >= 4.4 API.");

        Context appContext = webView.getContext().getApplicationContext();
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", port + "");
        System.setProperty("https.proxyHost", host);
        System.setProperty("https.proxyPort", port + "");
        try {
            Class applictionCls = Class.forName(applicationClassName);
            Field loadedApkField = applictionCls.getField("mLoadedApk");
            loadedApkField.setAccessible(true);
            Object loadedApk = loadedApkField.get(appContext);
            Class loadedApkCls = Class.forName("android.app.LoadedApk");
            Field receiversField = loadedApkCls.getDeclaredField("mReceivers");
            receiversField.setAccessible(true);
            ArrayMap receivers = (ArrayMap) receiversField.get(loadedApk);
            for (Object receiverMap : receivers.values()) {
                for (Object rec : ((ArrayMap) receiverMap).keySet()) {
                    Class clazz = rec.getClass();
                    if (clazz.getName().contains("ProxyChangeListener")) {
                        Method onReceiveMethod = clazz.getDeclaredMethod("onReceive", Context.class, Intent.class);
                        Intent intent = new Intent(Proxy.PROXY_CHANGE_ACTION);

                        onReceiveMethod.invoke(rec, appContext, intent);
                    }
                }
            }

            Log.d(LOG_TAG, "Setting proxy with >= 4.4 API successful!");
            return true;
        } catch (ClassNotFoundException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Log.v(LOG_TAG, e.getMessage());
            Log.v(LOG_TAG, exceptionAsString);
        } catch (NoSuchFieldException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Log.v(LOG_TAG, e.getMessage());
            Log.v(LOG_TAG, exceptionAsString);
        } catch (IllegalAccessException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Log.v(LOG_TAG, e.getMessage());
            Log.v(LOG_TAG, exceptionAsString);
        } catch (IllegalArgumentException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Log.v(LOG_TAG, e.getMessage());
            Log.v(LOG_TAG, exceptionAsString);
        } catch (NoSuchMethodException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Log.v(LOG_TAG, e.getMessage());
            Log.v(LOG_TAG, exceptionAsString);
        } catch (InvocationTargetException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Log.v(LOG_TAG, e.getMessage());
            Log.v(LOG_TAG, exceptionAsString);
        }
        return false;
    }

    private static Object getFieldValueSafely(Field field, Object classInstance) throws IllegalArgumentException, IllegalAccessException {
        boolean oldAccessibleValue = field.isAccessible();
        field.setAccessible(true);
        Object result = field.get(classInstance);
        field.setAccessible(oldAccessibleValue);
        return result;
    }*/

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
    //@Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        WebView webView = (WebView) findViewById(R.id.webView);
//        // Check if the key event was the Back button and if there's history
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack();
//            return true;
//        }
//        // If it wasn't the Back key or there's no web page history, bubble up to the default
//        // system behavior (probably exit the activity)
//        return super.onKeyDown(keyCode, event);
//    }

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
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_account){
            Intent i = new Intent(getApplicationContext(),Account.class);
            startActivity(i);
            return true;
        }
        else if(id==R.id.action_utility){
            Intent i = new Intent(getApplicationContext(),Utilities1.class);
            startActivity(i);
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
            startActivity(i);

        } else if (id == R.id.nav_feedback) {
            Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_issue) {
            Intent i = new Intent(getApplicationContext(), BookActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_map) {
            Intent i = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_list) {
            Intent i = new Intent(getApplicationContext(), ListsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_hmc) {
            Intent i = new Intent(getApplicationContext(), HmcActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_search) {
            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


