package team3.teamproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Petr Makarov on 21.04.2018.
 */


//Controlls regestering a new user
//When an instance is created

public class ContactFormActivity extends AppCompatActivity {
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);


        //Stores information regarding the user (modifiable)
        final EditText your_name        = (EditText) findViewById(R.id.nameEditContact);
        final EditText your_email       = (EditText) findViewById(R.id.emailEditContact);
        final EditText your_subject     = (EditText) findViewById(R.id.subjectEditContact);
        final EditText your_message     = (EditText) findViewById(R.id.messageContact);


        Button email = (Button) findViewById(R.id.bt_postContact);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Applies data inputted as a new user
                String name      = your_name.getText().toString();
                String email     = your_email.getText().toString();
                String subject   = your_subject.getText().toString();
                String message   = your_message.getText().toString();

                //Error checking
                if (TextUtils.isEmpty(name)){
                    your_name.setError("Enter Your Name");
                    your_name.requestFocus();
                    return;
                }

                Boolean onError = false;
                if (!isValidEmail(email)) {
                    onError = true;
                    your_email.setError("Invalid Email");
                    return;
                }

                if (TextUtils.isEmpty(subject)){
                    your_subject.setError("Enter Your Subject");
                    your_subject.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(message)){
                    your_message.setError("Enter Your Message");
                    your_message.requestFocus();
                    return;
                }

                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

                sendEmail.setType("plain/text");
                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"tyler@duffin.co"});
                sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                        "name:"+ name +'\n'+'\n'+"Message:"+'\n'+message);

                startActivity(Intent.createChooser(sendEmail, "Send mail..."));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
