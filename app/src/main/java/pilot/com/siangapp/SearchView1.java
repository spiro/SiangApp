package pilot.com.siangapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class SearchView1 extends ArrayAdapter<SearchData> {

    SearchView1(@NonNull Context context,List<SearchData> data) {
        super(context, R.layout.listsearch, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater divyanshInflater = LayoutInflater.from(getContext());
        View customView = divyanshInflater.inflate(R.layout.listsearch,parent,false);


        TextView divyanshText = (TextView) customView.findViewById(R.id.searchName);
        TextView divyanshText1 = (TextView) customView.findViewById(R.id.searchPhone);


        for(int i=0;i<=position;i++) {
            SearchData sd = getItem(i);
            String name = sd.name;
            String phoneno = sd.phoneno;

            divyanshText.setText(name);
            divyanshText1.setText(phoneno);

        }


        return customView;
    }
}
