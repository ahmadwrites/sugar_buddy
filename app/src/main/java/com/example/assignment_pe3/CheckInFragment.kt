package com.example.assignment_pe3

import android.content.ContentValues
import android.content.Intent
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
import com.google.android.material.chip.Chip
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CheckInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckInFragment : Fragment() {
    val firestoreDatabase = FirebaseFirestore.getInstance()

    lateinit var chipHistory: Chip

    lateinit var etGlucoseLevel: EditText
    lateinit var etCarbohydratesConsumed: EditText
    lateinit var etMealDescription: EditText
    lateinit var etInjuries: EditText
    lateinit var etInsulinInjected: EditText
    lateinit var etNoInsulin: EditText

    lateinit var btnCancel: Button
    lateinit var btnSubmit: Button

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
        val view: View = inflater.inflate(R.layout.fragment_check_in, container, false)

        chipHistory = view.findViewById<Chip>(R.id.chip_history);

        etGlucoseLevel = view.findViewById<EditText>(R.id.et_glucose_level);
        etCarbohydratesConsumed = view.findViewById<EditText>(R.id.et_carbohydrates_consumed);
        etMealDescription = view.findViewById<EditText>(R.id.et_meal_description);
        etInjuries = view.findViewById<EditText>(R.id.et_injuries);
        etInsulinInjected = view.findViewById<EditText>(R.id.et_insulin_injected);
        etNoInsulin = view.findViewById<EditText>(R.id.et_no_insulin);

        btnCancel = view.findViewById<Button>(R.id.btn_cancel);
        btnSubmit = view.findViewById<Button>(R.id.btn_submit);

        val user = Firebase.auth.currentUser

        chipHistory.setOnClickListener(View.OnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                CheckInHistoryFragment()
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        })

        btnCancel.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction =
                fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                HomeFragment()
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        btnSubmit.setOnClickListener {
            when {
                TextUtils.isEmpty(etGlucoseLevel.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter glucose level",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etCarbohydratesConsumed.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter carbohydrates consumed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etMealDescription.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter meal description",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etInsulinInjected.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter insulin injected",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etNoInsulin.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter number of times insulin injected",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val glucoseLevel: String = etGlucoseLevel.text.toString().trim { it <= ' ' }
                    val carbohydratesConsumed: String =
                        etCarbohydratesConsumed.text.toString().trim { it <= ' ' }
                    val mealDescription: String =
                        etMealDescription.text.toString().trim { it <= ' ' }
                    val insulinInjected: String =
                        etInsulinInjected.text.toString().trim { it <= ' ' }
                    val noInsulin: String = etNoInsulin.text.toString().trim { it <= ' ' }
                    val injuries: String = etInjuries.text.toString().trim { it <= ' ' }

                    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                    val currentDate = sdf.format(Date())

                    val checkIn: MutableMap<String, Any> = HashMap()
                    checkIn["userId"] = user!!.uid;
                    checkIn["glucoseLevel"] = glucoseLevel;
                    checkIn["carbohydratesConsumed"] = carbohydratesConsumed;
                    checkIn["mealDescription"] = mealDescription;
                    checkIn["insulinInjected"] = insulinInjected;
                    checkIn["noInsulin"] = noInsulin;
                    checkIn["injuries"] = injuries;
                    checkIn["date"] = currentDate;

                    firestoreDatabase.collection("checkIns").add(checkIn)
                        .addOnSuccessListener { documentReference ->
                            Log.d(
                                ContentValues.TAG,
                                "DocumentSnapshot added with ID: ${documentReference.id}"
                            )
                            Toast.makeText(
                                requireContext(),
                                "Successfully added check-in.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val fragmentManager = parentFragmentManager
                            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(
                                R.id.frame_layout,
                                CheckInHistoryFragment()
                            )
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        }.addOnFailureListener { e ->
                            Toast.makeText(
                                requireContext(),
                                e.toString(),
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
         * @return A new instance of fragment CheckInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}