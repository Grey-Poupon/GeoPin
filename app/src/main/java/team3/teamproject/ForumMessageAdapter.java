package team3.teamproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Steve on 08/03/2018.
 */

public class ForumMessageAdapter extends ArrayAdapter<ForumMessage>{

    public ForumMessageAdapter(@NonNull Context context, List<ForumMessage> resources) {
        super(context,0, resources);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ForumMessage msg = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_comment_layout, parent, false);
        }
        // name date msg
        TextView name = convertView.findViewById(R.id.usrName);
        TextView date = convertView.findViewById(R.id.date);
        TextView text = convertView.findViewById(R.id.usrMsg);


        name.setText(msg.getUserID());
        date.setText(msg.getDateString());
        text.setText(msg.getText());

        return convertView;
    }

}
