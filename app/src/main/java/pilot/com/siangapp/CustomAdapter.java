package pilot.com.siangapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;
class CustomAdapter extends ArrayAdapter<String> {

    CustomAdapter(@NonNull Context context, String[] HMC, String[] HMC1) {
        super(context, R.layout.custom_row, HMC);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater divyanshInflater = LayoutInflater.from(getContext());
        View customView = divyanshInflater.inflate(R.layout.custom_row,parent,false);


        TextView divyanshText = (TextView) customView.findViewById(R.id.divyanshText);
        ImageView divyanshImage = (ImageView) customView.findViewById(R.id.divyanshImage);
        TextView divyanshText1 = (TextView) customView.findViewById(R.id.divyanshText1);
        TextView divyanshText2 = (TextView) customView.findViewById(R.id.divyanshText2);
        TextView divyanshText3 = (TextView) customView.findViewById(R.id.divyanshText3);


        if(position==0) {
            divyanshText.setText("General Secretary");
            divyanshText1.setText("Shivam Yadav");
            divyanshText2.setText("Room No: 240");
            divyanshText3.setText("9435664011");
            divyanshImage.setImageResource(R.drawable.shivam_dp_min);
        }
        else if(position==1){
            divyanshText.setText("Technical Secretary");
            divyanshText1.setText("Daman Tekchandani");
            divyanshText2.setText("Room No: 25");
            divyanshText3.setText("9126874508");
            divyanshImage.setImageResource(R.drawable.daman_dp_min);
        }
        else if (position==2){
            divyanshText.setText("Cultural Secretary");
            divyanshText1.setText("Kartikey kant");
            divyanshText2.setText("Room No: 59");
            divyanshText3.setText("7577994159");
            divyanshImage.setImageResource(R.drawable.kar_dp_min);
        }
        else if(position==3){
            divyanshText.setText("Welfare Secretary");
            divyanshText1.setText("Sahil Chaturvedi");
            divyanshText2.setText("Room No: 223");
            divyanshText3.setText("9644992223");
            divyanshImage.setImageResource(R.drawable.sahil_dp_min);
        }
        else if (position==4){
            divyanshText.setText("Maintenance Secretary");
            divyanshText1.setText("Aman Sai");
            divyanshText2.setText("Room No: 157");
            divyanshText3.setText("7086052916");
            divyanshImage.setImageResource(R.drawable.aman_dp_min);
        }
        else if (position==5){
            divyanshText.setText("Sports Secretary");
            divyanshText1.setText("Ishan Azad");
            divyanshText2.setText("Room No: 143");
            divyanshText3.setText("9127512324");
            divyanshImage.setImageResource(R.drawable.ishan_dp_min);
        }
        else if(position == 6){
            divyanshText.setText("Literary Secretary");
            divyanshText1.setText("Anurag Barfa");
            divyanshText2.setText("Room No: 33");
            divyanshText3.setText("9127512345");
            divyanshImage.setImageResource(R.drawable.barfa_dp_min);
        }



        return customView;
    }
}
