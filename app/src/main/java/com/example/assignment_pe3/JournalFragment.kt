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
 * Use the [JournalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JournalFragment : Fragment() {
    lateinit var chipJournalHistory: Chip

    private var param1: String? = null
    private var param2: String? = null

    val firestoreDatabase = FirebaseFirestore.getInstance()

    lateinit var etJournalTitle: EditText;
    lateinit var etJournalContents: EditText;
    lateinit var etJournalTags: EditText;
    lateinit var etJournalFeelings: EditText;
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
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_journal, container, false)
        chipJournalHistory = view.findViewById<Chip>(R.id.chip_journal_history)

        etJournalTitle = view.findViewById<EditText>(R.id.et_journal_title)
        etJournalContents = view.findViewById<EditText>(R.id.et_journal_contents)
        etJournalTags = view.findViewById<EditText>(R.id.et_journal_tags)
        etJournalFeelings = view.findViewById<EditText>(R.id.et_journal_feelings)

        btnCancel = view.findViewById<Button>(R.id.btn_journal_cancel)
        btnSubmit = view.findViewById<Button>(R.id.btn_journal_submit)

        val user = Firebase.auth.currentUser

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

        btnSubmit.setOnClickListener{
            when{
                TextUtils.isEmpty(etJournalTitle.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter journal title",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etJournalContents.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter journal contents",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etJournalTags.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter journal tags",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etJournalFeelings.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter your feelings in the journal",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val title: String = etJournalTitle.text.toString().trim { it <= ' ' }
                    val contents: String = etJournalContents.text.toString().trim { it <= ' ' }
                    val tags: String = etJournalTags.text.toString().trim { it <= ' ' }
                    val feelings: String = etJournalFeelings.text.toString().trim { it <= ' ' }

                    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                    val currentDate = sdf.format(Date())

                    val journal: MutableMap<String, Any> = HashMap()
                    journal["userId"] = user!!.uid
                    journal["title"] = title
                    journal["contents"] = contents
                    journal["tags"] = tags
                    journal["feelings"] = feelings

                    firestoreDatabase.collection("journals").add(journal)
                        .addOnSuccessListener { documentReference ->
                            Log.d(
                                ContentValues.TAG,
                                "DocumentSnapshot added with ID: ${documentReference.id}"
                            )
                            Toast.makeText(
                                requireContext(),
                                "Successfully added journals.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val fragmentManager = parentFragmentManager
                            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(
                                R.id.frame_layout,
                                JournalHistoryFragment()
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

        chipJournalHistory.setOnClickListener(View.OnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                JournalHistoryFragment()
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
         * @return A new instance of fragment JournalFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JournalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}