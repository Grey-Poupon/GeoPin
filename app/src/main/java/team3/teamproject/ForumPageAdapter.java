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
 * Created by Steve on 06/03/2018.
 */

public class ForumPageAdapter extends ArrayAdapter<ForumPage> {

    public ForumPageAdapter(@NonNull Context context, List<ForumPage> resources) {
        super(context,0, resources);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ForumPage post = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.forum_post_layout, parent, false);
        }
        TextView title = convertView.findViewById(R.id.postTitle);
        TextView creator = convertView.findViewById(R.id.postCreator);

        title.setText(post.getTitle());
        creator.setText(post.getUserID());

        return convertView;
    }
}
