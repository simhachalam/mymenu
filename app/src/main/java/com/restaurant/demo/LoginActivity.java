package com.restaurant.demo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {
	private CallbackManager mCallbackManager;
	private static final String EMAIL = "email";
	private static final String USER_PROFILE = "public_profile";
	private static final String AUTH_TYPE = "rerequest";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mCallbackManager = CallbackManager.Factory.create();

		setContentView(R.layout.activity_login);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		printKeyHash();
		//init
		mCallbackManager = CallbackManager.Factory.create();
		checkFBLogin();
		Button skipButton = findViewById(R.id.skip_btn);
		skipButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//go to scan qr
				Intent menuIntent = new Intent(LoginActivity.this, QRCodeScannerActivity.class);
				startActivity(menuIntent);
			}
		});

		Button singInButton = findViewById(R.id.signup_btn);
		singInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//go to scan qr
				LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
						Arrays.asList(EMAIL, USER_PROFILE)
				);
			}
		});


//		LoginButton mLoginButton = findViewById(R.id.login_button);
//
//		// Set the initial permissions to request from the user while logging in
//		mLoginButton.setReadPermissions(Arrays.asList(EMAIL, USER_PROFILE));

//		mLoginButton.setAuthType(AUTH_TYPE);

		// Register a callback to respond to the user
//		LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(EMAIL, USER_PROFILE));
//		LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));

		LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				Toast.makeText(getApplicationContext(), "on success", Toast.LENGTH_SHORT).show();
				AccessToken accessToken = AccessToken.getCurrentAccessToken();
				boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
				if(isLoggedIn){
					RequestData();
				}
//				setResult(RESULT_OK);
//				finish();
			}

			@Override
			public void onCancel() {
				Toast.makeText(getApplicationContext(), "on cancel", Toast.LENGTH_SHORT).show();
//				setResult(RESULT_CANCELED);
//				finish();
			}

			@Override
			public void onError(FacebookException e) {
				Toast.makeText(getApplicationContext(), "on error", Toast.LENGTH_SHORT).show();
				// Handle exception
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mCallbackManager.onActivityResult(requestCode, resultCode, data);
	}
	public void RequestData(){
		GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
			@Override
			public void onCompleted(JSONObject object, GraphResponse response) {

				JSONObject json = response.getJSONObject();
				Toast.makeText(getApplicationContext(), "on jsondata"+json.toString(), Toast.LENGTH_SHORT).show();
				System.out.println("Json data :"+json);
				try {
					if(json != null){
						String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
//						details_txt.setText(Html.fromHtml(text));
//						profile.setProfileId(json.getString("id"));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		Bundle parameters = new Bundle();
		parameters.putString("fields", "id,name,link,email,picture");
		request.setParameters(parameters);
		request.executeAsync();
	}
	private void checkFBLogin(){
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
		if(isLoggedIn){
			LoginManager.getInstance().logOut();
		}
	}
	private void printKeyHash() {
		// Add code to print out the key hash
		Log.i("package name", getPackageName());
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (PackageManager.NameNotFoundException e) {
			Log.e("KeyHash:", e.toString());
		} catch (NoSuchAlgorithmException e) {
			Log.e("KeyHash:", e.toString());
		}
	}


}
