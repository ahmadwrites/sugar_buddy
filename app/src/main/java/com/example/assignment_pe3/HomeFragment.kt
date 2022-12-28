package com.example.assignment_pe3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    lateinit var btnCheckIn: Button
    lateinit var cardHistory: CardView
    lateinit var cardJournal: CardView
    lateinit var cardReport: CardView
    lateinit var contactReport: CardView
    lateinit var tvHomeIntro: TextView

    // TODO: Rename and change types of parameters
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
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        btnCheckIn = view.findViewById<Chip>(R.id.btn_check_in)
        cardHistory = view.findViewById<CardView>(R.id.card_history)
        cardJournal = view.findViewById<CardView>(R.id.card_journal)
        cardReport = view.findViewById<CardView>(R.id.card_report)
        contactReport = view.findViewById<CardView>(R.id.card_contact)
        tvHomeIntro = view.findViewById<TextView>(R.id.tv_home_intro);

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
        }

        tvHomeIntro.setText("Hi, " + user?.email)

        btnCheckIn.setOnClickListener(View.OnClickListener {
//            val fragmentManager = parentFragmentManager
//            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(
//                R.id.frame_layout,
//                CheckInFragment()
//            )
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()

            (activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView))?.selectedItemId =
                R.id.checkIn;

        })

        contactReport.setOnClickListener(View.OnClickListener {
            (activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView))?.selectedItemId =
                R.id.checkIn;

            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                ContactProfessionalFragment()
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        })

        cardReport.setOnClickListener(View.OnClickListener {
            (activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView))?.selectedItemId =
                R.id.checkIn;

            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                ReportSymptomsFragment()
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        })

        cardHistory.setOnClickListener(View.OnClickListener {
            (activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView))?.selectedItemId =
                R.id.checkIn;

            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                CheckInHistoryFragment()
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        })

        cardJournal.setOnClickListener(View.OnClickListener {
            (activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView))?.selectedItemId =
                R.id.home;

            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                JournalFragment()
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}