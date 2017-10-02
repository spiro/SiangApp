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
class DeveloperAdapter extends ArrayAdapter<String> {

    DeveloperAdapter(@NonNull Context context, String[] HMC, String[] HMC1) {
        super(context, R.layout.custom_row, HMC);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater divyanshInflater = LayoutInflater.from(getContext());
        View customView = divyanshInflater.inflate(R.layout.developer_row,parent,false);


        TextView divyanshText = (TextView) customView.findViewById(R.id.divyanshText);
        ImageView divyanshImage = (ImageView) customView.findViewById(R.id.divyanshImage);
        TextView divyanshText1 = (TextView) customView.findViewById(R.id.divyanshText1);
        TextView divyanshText2 = (TextView) customView.findViewById(R.id.divyanshText2);
        TextView divyanshText3 = (TextView) customView.findViewById(R.id.divyanshText3);



        return customView;
    }
}
