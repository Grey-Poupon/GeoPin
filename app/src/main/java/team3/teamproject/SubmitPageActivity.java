package team3.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Date;
import java.util.Random;

public class SubmitPageActivity extends AppCompatActivity {

    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_page);
        ID = getIntent().getStringExtra("BoardID");

        // post message onClick
        Button submit = (Button) findViewById(R.id.submitPageBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText title = (TextInputEditText) findViewById(R.id.titleInput);
                TextInputEditText text  = (TextInputEditText) findViewById(R.id.descriptionInput);
                setResult(RESULT_OK,new Intent().putExtra("Page",createForumPage(title.getText().toString(),text.getText().toString())));
                finish();
            }
        });
    }

    public ForumPost createForumPage(String title, String text){
        String uID = ((User) this.getApplication()).getUserID();
        String username = ((User) this.getApplication()).getUserName();

        ForumPost post = new ForumPost(title,username,text,uID,ID,getNewID(),new Date());
        return post;

    }

    private String getNewID() {
        // ToDo get new ID from Server to stop ID duplication
        return String.valueOf(new Random().nextInt(10000));
    }


}
