package com.example.skoroxod.auth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import android.content.Intent
import android.util.Log
import android.widget.Button
import com.facebook.*
import com.facebook.login.LoginManager
import java.util.*
import com.facebook.FacebookException
import com.facebook.FacebookCallback
import com.facebook.CallbackManager
import com.facebook.AccessToken


class MainActivity : AppCompatActivity() {


    val facebookCallback = object :
            FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            Log.d("FB_Profile Success", loginResult.accessToken.userId)
            val p = Profile.getCurrentProfile()

            Log.d("FB_Profile Success", p?.firstName + p?.lastName + p?.linkUri + p?.getProfilePictureUri(200, 200))


            Log.d("FB_Profile Success", "logged" + isLoggedIn())
        }

        override fun onCancel() {
            // App code
        }

        override fun onError(exception: FacebookException) {
            // App code
        }
    }

    private fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callbackManager = CallbackManager.Factory.create();

//        loginButton = findViewById<View>(R.id.login_button) as LoginButton
//        loginButton.setReadPermissions("email")
//
//        // Callback registration
//        loginButton.registerCallback(callbackManager, facebookCallback  )


    }

    private var callbackManager: CallbackManager? = null

    private lateinit var loginButton: LoginButton

    override fun onResume() {
        super.onResume()
        val p = Profile.getCurrentProfile()
        Log.d("FB_Profile", p?.firstName + p?.lastName + p?.linkUri + p?.getProfilePictureUri(200, 200))

        val b = findViewById<Button>(R.id.button2)
        b.text =  if(isLoggedIn()) "logout" else "login with facebook"
        b.setOnClickListener {
            Log.d("FB_Profile", "click login")
            if(isLoggedIn()) {
                LoginManager.getInstance().logOut()
            } else {
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
            }
        }

        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback)


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
