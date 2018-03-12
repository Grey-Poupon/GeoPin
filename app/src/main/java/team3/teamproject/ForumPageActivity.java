package team3.teamproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Steve on 08/03/2018.
 */

public class ForumPageActivity extends AppCompatActivity {

    private ForumPost post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_post);

        //get the main post
        post = getIntent().getParcelableExtra("ForumPost");


        ForumMessageAdapter adapter = new ForumMessageAdapter(this, getChildren(post));
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.commentsList);
        listView.setAdapter(adapter);
        ((TextView) findViewById(R.id.postTitle)).setText(post.getTitle());
        ((TextView) findViewById(R.id.date)).setText(post.getDateString());
        ((TextView) findViewById(R.id.usrName)).setText(post.getUserID());
        ((TextView) findViewById(R.id.postTxt)).setText(post.getText());
    }

    private List<ForumMessage> getChildren(ForumMessage message) {
        //ToDo get messages from server
        List<ForumMessage> messages = new ArrayList<>();
        messages.add(new ForumMessage("Test 1","STE",post.getID(),"1",  new Date()));
        messages.add(new ForumMessage("Test 2","STE",post.getID(),"2",  new Date()));
        messages.add(new ForumMessage("Test 3","STE",post.getID(),"3",  new Date()));
        messages.add(new ForumMessage("Test 4","STE",post.getID(),"4",  new Date()));
        messages.add(new ForumMessage("Test 5","STE",post.getID(),"5",  new Date()));
        return messages;

    }
}
