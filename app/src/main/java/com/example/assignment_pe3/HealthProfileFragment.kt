package com.example.assignment_pe3

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HealthProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HealthProfileFragment : Fragment() {
    lateinit var btnEditHealthProfile: Button
    lateinit var btnHealthScore: Button

    lateinit var tvTemperatureVal: TextView
    lateinit var tvBloodPressureVal: TextView
    lateinit var tvOxygenSaturationVal: TextView
    lateinit var tvBodyWeightVal: TextView
    lateinit var tvBodyHeightVal: TextView
    lateinit var tvGlucoseLevelVal: TextView
    lateinit var tvBpmVal: TextView
    lateinit var tvHealthProfileName: TextView

    private val firestoreDatabase = FirebaseFirestore.getInstance()

    private var param1: String? = null
    private var param2: String? = null

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
        val view: View = inflater.inflate(R.layout.fragment_health_profile, container, false)

        btnEditHealthProfile = view.findViewById<Button>(R.id.btn_edit_health_profile)
        btnHealthScore = view.findViewById<Button>(R.id.btn_calculate_score)

        tvTemperatureVal = view.findViewById<Button>(R.id.tv_temperature_val)
        tvBloodPressureVal = view.findViewById<Button>(R.id.tv_blood_pressure_val)
        tvOxygenSaturationVal = view.findViewById<Button>(R.id.tv_oxygen_saturation_val)
        tvBodyWeightVal = view.findViewById<Button>(R.id.tv_body_weight_val)
        tvBodyHeightVal = view.findViewById<Button>(R.id.tv_body_height_val)
        tvGlucoseLevelVal = view.findViewById<Button>(R.id.tv_glucose_level_val)
        tvBpmVal = view.findViewById<Button>(R.id.tv_bpm_val)

        tvHealthProfileName = view.findViewById<Button>(R.id.tv_health_profile_name)

        val user = Firebase.auth.currentUser

        firestoreDatabase.collection("users").document(user!!.uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        val firstName = document.getString("firstName")
                        val lastName = document.getString("lastName")
                        tvHealthProfileName.text = "$firstName $lastName"
                    } else {
                        Log.d(ContentValues.TAG, "The document doesn't exist.")
                    }
                } else {
                    task.exception?.message?.let {
                        Log.d(ContentValues.TAG, it)
                    }
                }
            }

        firestoreDatabase.collection("healthProfile").document(user!!.uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        val temperature = document.getString("temperature")
                        val bloodPressure = document.getString("bloodPressure")
                        val bodyHeight = document.getString("bodyHeight")
                        val bodyWeight = document.getString("bodyWeight")
                        val bpm = document.getString("bpm")
                        val glucoseLevel = document.getString("glucoseLevel")
                        val oxygenSaturation = document.getString("oxygenSaturation")

                        tvTemperatureVal.text = temperature
                        tvBloodPressureVal.text = bloodPressure
                        tvBodyHeightVal.text = bodyHeight
                        tvBodyWeightVal.text = bodyWeight
                        tvBpmVal.text = bpm
                        tvGlucoseLevelVal.text = glucoseLevel
                        tvOxygenSaturationVal.text = oxygenSaturation

                    } else {
                        Log.d(ContentValues.TAG, "The document doesn't exist.")
                    }
                } else {
                    task.exception?.message?.let {
                        Log.d(ContentValues.TAG, it)
                    }
                }
            }

        btnEditHealthProfile.setOnClickListener(View.OnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                EditHealthProfileFragment()
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        })

        btnHealthScore.setOnClickListener(View.OnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                HealthScoreFragment()
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        })
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HealthProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HealthProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}