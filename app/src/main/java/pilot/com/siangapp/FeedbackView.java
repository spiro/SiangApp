package pilot.com.siangapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
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

class FeedbackView extends ArrayAdapter<FeedbackData> {

    public String status;

    FeedbackView(@NonNull Context context,List<FeedbackData> data) {
        super(context, R.layout.feedbackview, data);
    }


    public static boolean sendResponse(String id, String nature)
    {

        final String fid = id;
        final String fnature = nature;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                DefaultHttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost("http://10.42.0.1/SiangApp/feedbackResponse.php");

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                nameValuePair.add(new BasicNameValuePair("id", fid));
                nameValuePair.add(new BasicNameValuePair("nature",fnature));


                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpClient.execute(httpPost, responseHandler);
                    Log.d("Http Response:", response);



                }catch (IOException e) {
                    e.printStackTrace();

                }

            }
        });


        t.start();
        return true;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater divyanshInflater = LayoutInflater.from(getContext());
        View customView = divyanshInflater.inflate(R.layout.feedbackview,parent,false);


        TextView divyanshText = (TextView) customView.findViewById(R.id.feedbacktext);
        TextView divyanshText1 = (TextView) customView.findViewById(R.id.feedbackfrom);
        Button divyanshText2 = (Button) customView.findViewById(R.id.feedbackc1);
        Button divyanshText3 = (Button) customView.findViewById(R.id.feedbackc2);

        for(int i=0;i<=position;i++) {
            FeedbackData fd = getItem(i);
            String text = fd.text;
            String from = fd.from;
            String choice1 = fd.choice1;
            String choice2 = fd.choice2;
            final String id = fd.id;


            divyanshText.setText(text);
            divyanshText1.setText(from);
            divyanshText2.setText(choice1);
            divyanshText3.setText(choice2);

            divyanshText2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendResponse(id, "1");
                }
            });

            divyanshText3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendResponse(id, "0");

                }
            });

        }

        return customView;
    }
}
