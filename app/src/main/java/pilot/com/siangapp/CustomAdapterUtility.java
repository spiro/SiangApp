package pilot.com.siangapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by inspiro on 31/7/17.
 */

public class CustomAdapterUtility extends ArrayAdapter<String> {
    CustomAdapterUtility(@NonNull Context context, String[] Utility, String[] Utility1){
        super(context,R.layout.custom_row_utility, Utility);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater utilityInflater = LayoutInflater.from(getContext());
        View customView = utilityInflater.inflate(R.layout.custom_row_utility,parent,false);


        TextView utilityDescription = (TextView) customView.findViewById(R.id.utility);
        TextView utilityLink = (TextView) customView.findViewById(R.id.utility1);


        if(position==0){
            utilityDescription.setText("PDF of some HowTos and study material");


            utilityLink.setClickable(true);
            utilityLink.setMovementMethod(LinkMovementMethod.getInstance());
            String text = "<a href='https://lookaside.fbsbx.com/file/Implinks.pdf?token=AWw9HyS4ZvvUDoQVViLCFOoOf0ZgZ1iC9QxJ-z59czHx-mLc4xoMtcQbNgrhD9ePiwgPiT-r2jdetdBEt--pFwkbP06rCR94aAc0EF3acyV-hlE6z4YrYtEQrCz6KxhBvycCaAlcp3O54moDIJiMUjX6q10SoUWqbhhVc6XZiGBgP8oonhoNBspLKyRNqXSugRKWZXFsChje9mCabKo2P0RzY9QgcmXE2Dd7GYhTIDs7_A'> Click Here </a>";
            utilityLink.setText(Html.fromHtml(text));

        }

        if(position==1){
            utilityDescription.setText("Intranet Complaint Section");
            utilityLink.setClickable(true);
            utilityLink.setMovementMethod(LinkMovementMethod.getInstance());
            String text = "<a href='https://intranet.iitg.ernet.in/cb/index.php'> Click Here </a> ";
            utilityLink.setText(Html.fromHtml(text));

        }
        if(position==2){
            utilityDescription.setText("Central Library");
            utilityLink.setClickable(true);
            utilityLink.setMovementMethod(LinkMovementMethod.getInstance());
            String text = "<a href='http://bidya.iitg.ernet.in/opac/'> Click Here </a> ";
            utilityLink.setText(Html.fromHtml(text));

        }
        if(position==3){
            utilityDescription.setText("Academic Affairs");
            utilityLink.setClickable(true);
            utilityLink.setMovementMethod(LinkMovementMethod.getInstance());
            String text = "<a href='http://shilloi.iitg.ernet.in/~acad/intranet/index.php'> Click Here </a> ";
            utilityLink.setText(Html.fromHtml(text));

        }
        if(position==4){
            utilityDescription.setText("Webmail");
            utilityLink.setClickable(true);
            utilityLink.setMovementMethod(LinkMovementMethod.getInstance());
            String text = "<a href='http://webmail.iitg.ernet.in/'> Click Here </a> ";
            utilityLink.setText(Html.fromHtml(text));

        }
        if(position==5){
            utilityDescription.setText("Bus Timings - to and from City");
            utilityLink.setClickable(true);
            utilityLink.setMovementMethod(LinkMovementMethod.getInstance());
            String text = "<a href='http://intranet.iitg.ernet.in/sites/default/files/bus-timings-2010.pdf'> Click Here </a> ";
            utilityLink.setText(Html.fromHtml(text));

        }
        return customView;
    }



}
