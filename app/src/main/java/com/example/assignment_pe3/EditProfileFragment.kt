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

//    To change: This was copied from EDIT HEALTH PROFILE
    val firestoreDatabase = FirebaseFirestore.getInstance()

    lateinit var etTemperature: EditText;
    lateinit var etBloodPressure: EditText;
    lateinit var etOxygenSaturation: EditText;
    lateinit var etBodyWeight: EditText;
    lateinit var etBodyHeight: EditText;
    lateinit var etGlucoseLevel: EditText;
    lateinit var etBpm: EditText;
    lateinit var btnSave: Button;

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

        etTemperature = view.findViewById<EditText>(R.id.et_temperature)
        etBloodPressure = view.findViewById<EditText>(R.id.et_blood_pressure)
        etOxygenSaturation = view.findViewById<EditText>(R.id.et_oxygen_saturation)
        etBodyWeight = view.findViewById<EditText>(R.id.et_body_weight)
        etBodyHeight = view.findViewById<EditText>(R.id.et_body_height)
        etGlucoseLevel = view.findViewById<EditText>(R.id.et_glucose_level)
        etBpm = view.findViewById<EditText>(R.id.et_bpm)
        btnSave = view.findViewById<Button>(R.id.btn_save_health_profile)

        val user = Firebase.auth.currentUser

        btnSave.setOnClickListener {
            when {
                TextUtils.isEmpty(etTemperature.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter temperature",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etBloodPressure.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter blood pressure",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etOxygenSaturation.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter oxygen saturation",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etBodyWeight.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter body weight",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etBodyHeight.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter body height",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etGlucoseLevel.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter glucose level",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etBpm.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter heart rate",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val temperature: String = etTemperature.text.toString().trim { it <= ' ' }
                    val bloodPressure: String = etBloodPressure.text.toString().trim { it <= ' ' }
                    val oxygenSaturation: String =
                        etOxygenSaturation.text.toString().trim { it <= ' ' }
                    val bodyWeight: String = etBodyWeight.text.toString().trim { it <= ' ' }
                    val bodyHeight: String = etBodyHeight.text.toString().trim { it <= ' ' }
                    val glucoseLevel: String = etGlucoseLevel.text.toString().trim { it <= ' ' }
                    val bpm: String = etBpm.text.toString().trim { it <= ' ' }

                    val healthProfile: MutableMap<String, Any> = HashMap()
                    healthProfile["userId"] = user!!.uid;
                    healthProfile["temperature"] = temperature;
                    healthProfile["bloodPressure"] = bloodPressure;
                    healthProfile["oxygenSaturation"] = oxygenSaturation;
                    healthProfile["bodyWeight"] = bodyWeight;
                    healthProfile["bodyHeight"] = bodyHeight;
                    healthProfile["glucoseLevel"] = glucoseLevel;
                    healthProfile["bpm"] = bpm;

                    firestoreDatabase.collection("healthProfile").add(healthProfile)
                        .addOnSuccessListener { documentReference ->
                            Log.d(
                                ContentValues.TAG,
                                "DocumentSnapshot added with ID: ${documentReference.id}"
                            )

                        }.addOnFailureListener { e ->
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