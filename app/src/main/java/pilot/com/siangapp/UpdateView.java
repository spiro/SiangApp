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

class UpdateView extends ArrayAdapter<UpdateContent> {

    UpdateView(@NonNull Context context,List<UpdateContent> data) {
        super(context, R.layout.mainupdate, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater divyanshInflater = LayoutInflater.from(getContext());
        View customView = divyanshInflater.inflate(R.layout.mainupdate,parent,false);


        TextView divyanshText = (TextView) customView.findViewById(R.id.updatetext);
        TextView divyanshText1 = (TextView) customView.findViewById(R.id.updatefrom);

        for(int i=0;i<=position;i++) {
            UpdateContent sd = getItem(i);
            String text = sd.text;
            String from = sd.fromuser;

            divyanshText.setText(text);
            divyanshText1.setText(from);
        }

        return customView;
    }
}
