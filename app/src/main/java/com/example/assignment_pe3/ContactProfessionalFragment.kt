package com.example.assignment_pe3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactProfessionalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactProfessionalFragment : Fragment() {

    lateinit var etDrName: EditText
    lateinit var etReason: EditText
    lateinit var etDrMessage: EditText
    lateinit var etDrEmail: EditText
    lateinit var cbLatestResults: CheckBox
    lateinit var cbBookAppointment: CheckBox
    lateinit var btnCancel: Button
    lateinit var btnSubmitContact: Button

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
        val view: View = inflater.inflate(R.layout.fragment_contact_professional, container, false);

        etDrName = view.findViewById<EditText>(R.id.et_drName)
        etReason = view.findViewById<EditText>(R.id.et_reason)
        etDrMessage = view.findViewById<EditText>(R.id.et_drMessage)
        etDrEmail = view.findViewById<EditText>(R.id.et_drEmail)
        cbLatestResults = view.findViewById<CheckBox>(R.id.cb_latest_results)
        cbBookAppointment = view.findViewById<CheckBox>(R.id.cb_book_appointment)
        btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        btnSubmitContact = view.findViewById<Button>(R.id.btn_submit_contact)

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

        btnSubmitContact.setOnClickListener {
            when {
                TextUtils.isEmpty(etDrName.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter doctor's name",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etReason.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter doctor's name",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etDrMessage.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter reason for contact",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etDrEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter doctor's email address",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val drName: String = etDrName.text.toString().trim { it <= ' ' }
                    val drMessage: String = etDrMessage.text.toString()
                    val drEmail: String = etDrEmail.text.toString().trim { it <= ' ' }
                    val reason: String = etReason.text.toString().trim { it <= ' ' }

                    val latestResults: Boolean = cbLatestResults.isChecked();
                    val bookAppointment: Boolean = cbBookAppointment.isChecked();

                    val message: String =
                        "Hello Dr $drName,\n$drMessage\nBest Regards"

                    sendEmail(drEmail, reason, message)
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
         * @return A new instance of fragment ContactProfessionalFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactProfessionalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        //put the Subject in the intent
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put the message in the intent
        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        } catch (e: Exception) {
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }

    }
}