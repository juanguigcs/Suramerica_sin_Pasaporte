package cualmemo.suramerica_sin_pasaporte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button Btest,Btest2;
    // Write a message to the database
   // FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("message");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);

        Btest=(Button)findViewById(R.id.btest);
        Btest2=(Button)findViewById(R.id.btest2);
        Btest.setOnClickListener(this);
        Btest2.setOnClickListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null){
            Toast.makeText(getApplicationContext(), user.getDisplayName()+ " , "+user.getEmail()+" , "+user.getUid(),Toast.LENGTH_LONG).show();
        }
        else{
            goLoginActivity();
        }
    }

    private void goLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), AutenticacionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginActivity();
    }

    @Override
    public void onClick(View view) {
        int i= view.getId();
        switch (i){
            case R.id.btest:
                // myRef.setValue("Hello, World 1!");
                logout(view);
                break;
            case R.id.btest2:
                //myRef.setValue("Hello, World 2 :D!");
                break;
        }

    }




}
