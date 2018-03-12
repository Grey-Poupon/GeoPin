package team3.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ForumListActivity extends AppCompatActivity {

    private List<ForumPost> posts = new ArrayList<>();

    public ForumListActivity(){}

    private void getPosts() {
        //TODO get messsages from server
        posts.add(new ForumPost("Title 1","Test 1",        "STE","-1","2",  new Date()));
        posts.add(new ForumPost("Title 2","Test 2",        "STE","-1","3",  new Date()));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_screen);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ListView lv = findViewById(R.id.messageList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Intent intent = new Intent(getBaseContext(), ForumPageActivity.class);
                intent.putExtra("ForumPost", posts.get(position));
                startActivity(intent);

            }
        });
        getPosts();
        Collections.sort(posts);
        createForum();
    }

    // setups all the dynamic elements of the activity
    private void createForum() {
        ((TextView) findViewById(R.id.forumTitle)).setText("Title");
        setupMessageText();
    }

    private void setupMessageText() {
        ForumPostAdapter adapter = new ForumPostAdapter(this, posts);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.messageList);
        listView.setAdapter(adapter);
    }




}
