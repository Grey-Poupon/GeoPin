package team3.teamproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Steve on 08/03/2018.
 */

public class ForumPostActivity extends AppCompatActivity {

    private ForumPost post;
    private List<ForumMessage> messages;
    private HashMap<String, ForumMessage> msgMap = new HashMap<>();
    private ForumMessageAdapter adapter;
    private String boardID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_post);

        //get the data from intent
        post = getIntent().getParcelableExtra("ForumPost");

        // set variables
        this.messages = getComments(post);
        this.adapter = new ForumMessageAdapter(this, messages,this);
        populateMap(messages);


        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.commentsList);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(adapter);
        listView.setClickable(true);

        ((TextView) findViewById(R.id.postTitle)).setText(post.getTitle());
        ((TextView) findViewById(R.id.date)).setText(post.getDateString());
        ((TextView) findViewById(R.id.usrName)).setText(post.getUserID());
        ((TextView) findViewById(R.id.postTxt)).setText(post.getText());


        // list view onclick
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                quickReply(messages.get(position));
            }
        });

        // post message onClick
        Button commentBtn = (Button) findViewById(R.id.commentBtn);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createForumMessage(((EditText) findViewById(R.id.commentBox)).getText().toString());
                cleanCommentBox();
            }
        });
    }

    private void cleanCommentBox() {
        // wipe text
        ((EditText) findViewById(R.id.commentBox)).setText("");
        // drop keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void quickReply(ForumMessage forumMessage) {
        // focus on the comment Box
        EditText commentText = (EditText) findViewById(R.id.commentBox);
        commentText.requestFocus();
        // add msg Id of the msg you're replying to
        commentText.setText("@"+forumMessage.getID()+" ",TextView.BufferType.EDITABLE);
        // move cursor to end
        commentText.setSelection(forumMessage.getID().length()+2);
        // popup the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(commentText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void populateMap(List<ForumMessage> messages) {
        for(int i= 0;i<messages.size();i++){
            msgMap.put(messages.get(i).getID(),messages.get(i));
        }
    }

    private List<ForumMessage> getComments(ForumPost post) {
        try {
           return JsonCommentMessage.toListForumMessages(PostStreamReader.getComments(post.getID()),post.getID());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<ForumMessage>();

    }
    private void createForumMessage(String txt){
        String uID = ((User) this.getApplication()).getUserID();
        ForumMessage message = new ForumMessage(txt,uID,post.getID(),getNewMsgID(),new Date());
        addMsgToServer(message);
        addMessageToList(message);
    }

    private String getNewMsgID() {
        //ToDo get msgId from server to stop id duplication
        if(messages.size()<1){return "1";}
        return String.valueOf(Integer.parseInt(messages.get(messages.size()-1).getID())+1);
    }

    private void addMsgToServer(ForumMessage message) {
        try {
            PostStreamReader.createComment(message.getUserID(),message.getText(),message.getParentID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addMessageToList(ForumMessage message) {
        adapter.add(message);
        msgMap.put(message.getID(),message);
        // focus on last message
        ListView lv = ((ListView) findViewById(R.id.commentsList));
        lv.setSelection(lv.getAdapter().getCount()-1);
    }

    public ForumMessageAdapter getAdapter() {
        return adapter;
    }

    public HashMap<String, ForumMessage> getMsgMap() {
        return msgMap;
    }
}
