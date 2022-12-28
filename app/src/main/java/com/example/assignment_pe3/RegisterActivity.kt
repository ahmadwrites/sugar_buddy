package com.example.assignment_pe3

import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    lateinit var btnRegister: Button
    lateinit var tvLogin: TextView
    lateinit var etEmail: EditText
    lateinit var etUsername: EditText
    lateinit var etFirstName: EditText
    lateinit var etLastName: EditText
    lateinit var etPassword: EditText
    val firestoreDatabase = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        btnRegister = findViewById<Button>(R.id.btn_register)
        tvLogin = findViewById<TextView>(R.id.tv_login)
        etEmail = findViewById<EditText>(R.id.et_email)
        etUsername = findViewById<EditText>(R.id.et_username)
        etFirstName = findViewById<EditText>(R.id.et_firstName)
        etLastName = findViewById<EditText>(R.id.et_lastName)
        etPassword = findViewById<EditText>(R.id.et_password)

        tvLogin.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })

        btnRegister.setOnClickListener {
            when {
                TextUtils.isEmpty(etEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etFirstName.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter First Name",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etLastName.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter Last Name",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etUsername.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter Username",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = etEmail.text.toString().trim { it <= ' ' }
                    val password: String = etPassword.text.toString().trim { it <= ' ' }
                    val firstName: String = etFirstName.text.toString().trim { it <= ' ' }
                    val lastName: String = etLastName.text.toString().trim { it <= ' ' }
                    val username: String = etUsername.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!;
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "You are registered Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val user: MutableMap<String, Any> = HashMap()
                                user["username"] = username;
                                user["firstName"] = firstName;
                                user["lastName"] = lastName;
                                user["userId"] = firebaseUser.uid;

                                firestoreDatabase.collection("users").add(user)
                                    .addOnSuccessListener { documentReference ->
                                        Log.d(
                                            TAG,
                                            "DocumentSnapshot added with ID: ${documentReference.id}"
                                        )

                                    }.addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding document", e)
                                    }

                                val intent =
                                    Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", firebaseUser.email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                }
            }
        }
    }
}