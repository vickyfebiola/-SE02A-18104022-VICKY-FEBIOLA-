package com.vickyfebiola_18104022.praktikum11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vickyfebiola_18104022.praktikum11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val gso = GoogleSignInOptions.DEFAULT_SIGN_IN
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.btnSignOut.setOnClickListener(this)
        binding.btnEmailVerify.setOnClickListener(this)

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUI(currentUser: FirebaseUser) {
        currentUser?.let {
            val name = currentUser.displayName
            val phoneNumber = currentUser.phoneNumber
            val email = currentUser.email
            val photoUrl = currentUser.photoUrl
            val emailVerified = currentUser.isEmailVerified
            val uid = currentUser.uid
            binding.ivImage.load(photoUrl){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            binding.tvName.text = name
            if(TextUtils.isEmpty(name)){
                binding.tvName.text = "No Name"
            }
            binding.tvUserId.text = email
            for (profile in it.providerData) {
                val providerId = profile.providerId
                if(providerId=="password" && emailVerified==true){
                    binding.btnEmailVerify.isVisible = false
                }
                if(providerId=="phone"){
                    binding.tvName.text = phoneNumber
                    binding.tvUserId.text = providerId
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            updateUI(currentUser)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSignOut -> {
                signOut()
            }
            R.id.btnEmailVerify -> {
                sendEmailVerification()
            }
        }
    }

    private fun sendEmailVerification() {
        binding.btnEmailVerify.isEnabled = false
        val user = auth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this) { task ->
            binding.btnEmailVerify.isEnabled = true
            if (task.isSuccessful) {
                Toast.makeText(baseContext,"Verification email sent to ${user.email} ",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(baseContext,"Failed to send verification email.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener(this) {}
        auth.signOut()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}