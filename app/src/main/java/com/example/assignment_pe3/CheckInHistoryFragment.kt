package com.example.assignment_pe3

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CheckInHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckInHistoryFragment : Fragment() {
    lateinit var chipCheckIn: Chip

    lateinit var checkInRecyclerView: RecyclerView
    lateinit var checkinAdapter: CheckInAdapter
    lateinit var checkinArrayList: ArrayList<CheckInDTO>

    val firestoreDatabase = FirebaseFirestore.getInstance()

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
        val view = inflater.inflate(R.layout.fragment_check_in_history, container, false)

        chipCheckIn = view.findViewById<Chip>(R.id.chip_check_in)

        checkInRecyclerView = view.findViewById(R.id.rv_checkin)
        checkInRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        checkInRecyclerView.setHasFixedSize(true)

        checkinArrayList = arrayListOf<CheckInDTO>()
        checkinAdapter = CheckInAdapter(checkinArrayList)
        checkInRecyclerView.adapter = checkinAdapter
        getCheckinData()

        chipCheckIn.setOnClickListener(View.OnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                CheckInFragment()
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        })
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CheckInHistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckInHistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getCheckinData() {
        val user = Firebase.auth.currentUser

        firestoreDatabase.collection("checkIns").whereEqualTo("userId", user!!.uid)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            checkinArrayList.add(dc.document.toObject(CheckInDTO::class.java))
                        }
                    }
                    checkinAdapter.notifyDataSetChanged()
                }

            })

    }
}