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

public class PageListActivity extends AppCompatActivity {

    private List<ForumPost> posts = new ArrayList<>();
    private String ID;
    private ForumPostAdapter adapter;
    public PageListActivity(){}

    private void getPosts() {
        //TODO get messsages from server
        posts.add(new ForumPost("Title 1","Test 1","STE",ID,"2",new Date()));
        posts.add(new ForumPost("Title 2","Test 2","STE",ID,"3",new Date()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        ID = getIntent().getStringExtra("PinID");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getBaseContext(), SubmitPageActivity.class);
                intent.putExtra("BoardID",ID);
                startActivityForResult(intent,1);
            }
        });
        ListView lv = (ListView) findViewById(R.id.messageList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Intent intent = new Intent(getBaseContext(), ForumPostActivity.class);
                intent.putExtra("ForumPost", posts.get(position));
                startActivity(intent);
            }
        });
        getPosts();
        Collections.sort(posts);
        createForum();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if creation was successful
        if(resultCode == RESULT_OK){
            ForumPost newPage = data.getParcelableExtra("Page");
            addNewPage(newPage);
        }
    }

    private void addNewPage(ForumPost newPage) {
        // ToDO add new page to server
        adapter.add(newPage);
        ListView lv = (ListView) findViewById(R.id.messageList);
        lv.setSelection(lv.getAdapter().getCount()-1);
    }

    // setups all the dynamic elements of the activity
    private void createForum() {
        ((TextView) findViewById(R.id.forumTitle)).setText("Title");
        setupMessageText();
    }

    private void setupMessageText() {
        adapter = new ForumPostAdapter(this, posts);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.messageList);
        listView.setAdapter(adapter);
    }




}
