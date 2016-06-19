package com.dssp.googleplustest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
        ,GoogleApiClient.OnConnectionFailedListener /*,GoogleApiClient.ConnectionCallbacks*/ {


    TextView tv_username;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        tv_username= (TextView) findViewById(R.id.tv_username);

        // Button listeners
        //Register both button and add click listener
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.btn_logout).setOnClickListener(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestProfile()
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                // .requestScopes(Plus.SCOPE_PLUS_LOGIN, Plus.SCOPE_PLUS_PROFILE,new Scope("https://www.googleapis.com/auth/plus.profile.emails.read"))

                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
           //     .enableAutoManage(this  FragmentActivity , this  OnConnectionFailedListener )
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();
        // [END build_client]

        // [START customize_button]
        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested. Try adding the
        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
        // difference.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        // [END customize_button]

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sign_in_button:

                signIn();

                break;
            case R.id.btn_logout:

                signOut();

                break;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

 /*   @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }*/

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        tv_username.setText("");
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

            // G+
            Person person  = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        //    Person.Cover.CoverPhoto coverPhoto = person.getCover().getCoverPhoto();
            Log.e(TAG, "--------------------------------");
            Log.e(TAG, "Display Name: " + person.getDisplayName());
            Log.e(TAG, "Gender: " + person.getGender());//0 means male
            Log.e(TAG, "AboutMe: " + person.getAboutMe());
            Log.e(TAG, "Birthday: " + person.getBirthday());//null
            Log.e(TAG, "Current Location: " + person.getCurrentLocation());//null
            Log.e(TAG, "Language: " + person.getLanguage());
            Log.e(TAG, "id: " + person.getId());
            Log.e(TAG, "nikname: " + person.getNickname());
            Log.e(TAG, "age range: " + person.getAgeRange());
            Log.e(TAG, "circle count: " + person.getCircledByCount());
//            Log.e(TAG, "cover: " + person.getCover().getCoverPhoto().getUrl());//null
            Log.e(TAG, "image: " + person.getImage().getUrl());
            Log.e(TAG, "name: " + person.getName());
            Log.e(TAG, "places lived: " + person.getPlacesLived());
            Log.e(TAG, "organizations: " + person.getOrganizations());
            Log.e(TAG, "bragging rights: " + person.getBraggingRights());
            Log.e(TAG, "plus1 count: " + person.getPlusOneCount());//0
            Log.e(TAG, "relationship status: " + person.getRelationshipStatus());//0
            Log.e(TAG, "urls: " + person.getUrls());//null
         //   Log.e(TAG, "cover url: " + coverPhoto.getUrl());//null
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e("djwalw","photo "+acct.getPhotoUrl());
            Log.e("djwalw","email "+acct.getEmail());
            Log.e("djwalw","given name "+acct.getGivenName());//null
            Log.e("djwalw","id "+acct.getId());
            Log.e("djwalw","id token "+acct.getIdToken());
            Log.e("djwalw","server auth code "+acct.getServerAuthCode());
           // Log.e("djwalw","photo "+acct.());
            tv_username.setText( acct.getDisplayName());

        } else {
            Log.e("djwalw","signout ");
            // Signed out, show unauthenticated UI.
            // updateUI(false);
        }
    }
}
