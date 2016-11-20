package cualmemo.suramerica_sin_pasaporte;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class AutenticacionActivity extends AppCompatActivity {
    private static final String TAG = "AutenticacionActivity";
    //face
    LoginButton loginButton;
    CallbackManager callbackManager;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_autenticacion);


        callbackManager = CallbackManager.Factory.create();
        loginButton=(LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(getApplicationContext(),"loggeo", Toast.LENGTH_SHORT).show();
                    goMainActivity();
                }
            }
        };

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               // optlog=2;
                handleFacebookAccessToken(loginResult.getAccessToken());
                // goMainActivity();
            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }
        });
    }


    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential= FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"No hubo exito en el logeooooo  ", Toast.LENGTH_SHORT).show();
                    //PROFEEEE AUQI
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }

    private void goMainActivity(){
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK
                |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}