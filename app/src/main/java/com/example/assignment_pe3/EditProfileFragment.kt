package com.example.assignment_pe3

import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var etFirstName: EditText
    lateinit var etLastName: EditText

    lateinit var btnSaveProfile: Button

    val firestoreDatabase = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        etFirstName = view.findViewById<EditText>(R.id.et_firstName)
        etLastName = view.findViewById<EditText>(R.id.et_lastName)

        btnSaveProfile = view.findViewById<Button>(R.id.btn_save_profile)

        val user = Firebase.auth.currentUser

        firestoreDatabase.collection("users").document(user!!.uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        val firstName = document.getString("firstName")
                        val lastName = document.getString("lastName")
                        etFirstName.setText(firstName)
                        etLastName.setText(lastName)
                    } else {
                        Log.d(ContentValues.TAG, "The document doesn't exist.")
                    }
                } else {
                    task.exception?.message?.let {
                        Log.d(ContentValues.TAG, it)
                    }
                }
            }

        btnSaveProfile.setOnClickListener {
            when {
                TextUtils.isEmpty(etFirstName.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter first name",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etLastName.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter last name",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val firstName: String = etFirstName.text.toString().trim { it <= ' ' }
                    val lastName: String = etLastName.text.toString().trim { it <= ' ' }

                    val userProfile: MutableMap<String, Any> = HashMap()
                    userProfile["firstName"] = firstName
                    userProfile["lastName"] = lastName

                    firestoreDatabase.collection("users").document(user!!.uid)
                        .set(userProfile)
                        .addOnSuccessListener { documentReference ->
                            Log.d(
                                ContentValues.TAG,
                                "DocumentSnapshot added with ID: ${user!!.uid}"
                            )
                            Toast.makeText(
                                requireContext(),
                                "Successfully saved profile.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val fragmentManager = parentFragmentManager
                            val fragmentTransaction: FragmentTransaction =
                                fragmentManager.beginTransaction()
                            fragmentTransaction.replace(
                                R.id.frame_layout,
                                HealthProfileFragment()
                            )
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()

                        }.addOnFailureListener { e ->
                            Toast.makeText(
                                requireContext(),
                                "An error occurred: Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.w(ContentValues.TAG, "Error adding document", e)
                        }
                }
            }
        }

        return view;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}