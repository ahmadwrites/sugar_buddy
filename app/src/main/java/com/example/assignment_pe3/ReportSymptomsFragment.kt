package com.example.assignment_pe3

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReportSymptomsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportSymptomsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var etSymptoms: EditText
    lateinit var etStartDate: EditText
    lateinit var etAdditionalInfo: EditText

    lateinit var btnCancel: Button
    lateinit var btnSubmit: Button

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
        val view: View = inflater.inflate(R.layout.fragment_report_symptoms, container, false);

        etSymptoms = view.findViewById<EditText>(R.id.et_symptoms);
        etStartDate = view.findViewById<EditText>(R.id.et_symptoms);
        etAdditionalInfo = view.findViewById<EditText>(R.id.et_symptoms);

        btnCancel = view.findViewById<Button>(R.id.btn_cancel);
        btnSubmit = view.findViewById<Button>(R.id.btn_submit);

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
                TextUtils.isEmpty(etSymptoms.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter list of symptoms",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etStartDate.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter start date of symptoms",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etAdditionalInfo.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter additional information",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val symptoms: String = etSymptoms.text.toString().trim { it <= ' ' }
                    val startDate: String = etStartDate.text.toString().trim { it <= ' ' }
                    val additionalInfo: String = etAdditionalInfo.text.toString().trim { it <= ' ' }

                    val message: String =
                        "Hello, I have been having $symptoms since $startDate and $additionalInfo."

                    sendSMS("+999", message);
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
         * @return A new instance of fragment ReportSymptomsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReportSymptomsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun sendSMS(mblNumVar: String?, smsMsgVar: String?) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val smsMgrVar = SmsManager.getDefault()
                smsMgrVar.sendTextMessage(mblNumVar, null, smsMsgVar, null, null)
                Toast.makeText(
                    requireContext(), "Message Sent",
                    Toast.LENGTH_LONG
                ).show()
            } catch (ErrVar: Exception) {
                Toast.makeText(
                    requireContext(), ErrVar.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
                ErrVar.printStackTrace()
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.SEND_SMS), 10)
            }
        }
    }
}