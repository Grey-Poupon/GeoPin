package team3.teamproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Date;

/**
 * Created by Steve on 08/03/2018.
 */

public class ForumMessageAdapter extends ArrayAdapter<ForumMessage>{
ForumPostActivity page;
private String msgID;
private Date msgDate;
private ForumMessage source;

    public ForumMessageAdapter(@NonNull Context context, List<ForumMessage> resources,ForumPostActivity page) {
        super(context,0, resources);
        this.page = page;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ForumMessage msg = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_comment_layout, parent, false);
        }

        // get name date msg
        TextView name = convertView.findViewById(R.id.usrName);
        TextView date = convertView.findViewById(R.id.date);
        TextView text = convertView.findViewById(R.id.usrMsg);
        TextView id   = convertView.findViewById(R.id.msgID);

        // make reply tags clickable
        String comment = msg.getText();

        // find & validate reply tag
        int i = 1;
        if(comment.length()>0 && comment.startsWith("@")){
            while (i<comment.length() && Character.isDigit(comment.charAt(i))){
                i++;
            }
            // pass the source Forum msg so it can be used in the onclick
            String sourceID = comment.substring(1,i);
            source = page.getMsgMap().get(sourceID);


        }
        // build onclick for reply tag
        if(i>1 && source!=null) {
            SpannableStringBuilder sb = new SpannableStringBuilder(msg.getText());
            ClickableSpan span = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    if (source != null) {
                        ListView listView = (ListView) page.findViewById(R.id.commentsList);
                        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                        listView.setSelection(page.getAdapter().getPosition(source));

                    }
                }
            };
            sb.setSpan(span, 0, i, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            text.setText(sb);
        }
        else{
            text.setText(msg.getText());
        }
        // set vars
        name.setText(msg.getUsername());
        date.setText(msg.getDateString());
        id.setText(msg.getID());
        text.setMovementMethod(LinkMovementMethod.getInstance());

        // pass vars to class so it can be accessed by the onClickListener
        this.msgID = msg.getID();
        this.msgDate = msg.getDate();

        // functionality for bump msg button

        return convertView;
    }

}
