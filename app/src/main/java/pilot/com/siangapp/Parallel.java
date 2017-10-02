package pilot.com.siangapp;

import android.os.AsyncTask;


public class Parallel extends AsyncTask<String, Void, String> {
@Override
protected String doInBackground(String... params) {
        return "hello";
        }

@Override
protected void onPostExecute(String result) {
        }

@Override
protected void onPreExecute() {
        }

@Override
protected void onProgressUpdate(Void... values) {
        }
}

