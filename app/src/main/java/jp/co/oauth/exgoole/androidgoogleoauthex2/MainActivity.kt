package jp.co.oauth.exgoole.androidgoogleoauthex2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.common.SignInButton
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult

class MainActivity() : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {


    var mGoogleApiClient: GoogleApiClient ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnGooglesignin: SignInButton = findViewById(R.id.btnGoogleSignIn) as SignInButton
        btnGooglesignin.setOnClickListener {
            signIn()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess()){
                val idToken = result.getSignInAccount()!!.getIdToken()
                Toast.makeText(this, "OK  ROI DO " +idToken + " >> "+ result, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "FAILED  ROI DO", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn(){
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        var RC_SIGN_IN = 9002
        var TAG = "MainActivity"
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
/*
val viewSCrollId: ScrollView =  findViewById(R.id.scroll_content_layout_id) as ScrollView
        val viewSettingAreaId: FrameLayout = findViewById(R.id.view_setting_area_id) as FrameLayout

        viewContentId.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d(TAG, "action down")
                    viewSettingAreaId.visibility = View.INVISIBLE
                }

                MotionEvent.ACTION_MOVE -> Log.d(TAG, "action move")

                MotionEvent.ACTION_UP -> {
                    Log.d(TAG, "action_up")
                    viewSettingAreaId.visibility = View.VISIBLE
                }
                else -> {

                }
            }
            true
        }

        viewSCrollId.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d(TAG, "action down scroll")
                    viewSettingAreaId.visibility = View.INVISIBLE
                }

                MotionEvent.ACTION_MOVE -> Log.d(TAG, "action move scroll")

                MotionEvent.ACTION_UP -> {
                    Log.d(TAG, "action_up scroll ")
                    viewSettingAreaId.visibility = View.VISIBLE
                }
                else -> {

                }
            }
            false
        }


*/
}
