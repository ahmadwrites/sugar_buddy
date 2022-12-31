package com.example.assignment_pe3

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
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
    lateinit var dashboard: CardView
    lateinit var news1: CardView
    lateinit var news2: CardView
    lateinit var news3: CardView
    lateinit var news4: CardView
    lateinit var search: TextView
    lateinit var tvHomeIntro: TextView
    val firestoreDatabase = FirebaseFirestore.getInstance()

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
        dashboard = view.findViewById<CardView>(R.id.card_dashboard)
        cardHistory = view.findViewById<CardView>(R.id.card_history)
        cardJournal = view.findViewById<CardView>(R.id.card_journal)
        cardReport = view.findViewById<CardView>(R.id.card_report)
        contactReport = view.findViewById<CardView>(R.id.card_contact)
        search = view.findViewById<TextView>(R.id.tv_search)
        news1 = view.findViewById<CardView>(R.id.card_news1)
        news2 = view.findViewById<CardView>(R.id.card_news2)
        news3 = view.findViewById<CardView>(R.id.card_news3)
        news4 = view.findViewById<CardView>(R.id.card_news4)
        tvHomeIntro = view.findViewById<TextView>(R.id.tv_home_intro);

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
        }

        firestoreDatabase.collection("users").document(user!!.uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        val firstName = document.getString("firstName")
                        val lastName = document.getString("lastName")
                        val username = document.getString("username")
                        tvHomeIntro.setText("Hi, " + firstName + " " + lastName)
                    } else {
                        Log.d(TAG, "The document doesn't exist.")
                    }
                } else {
                    task.exception?.message?.let {
                        Log.d(TAG, it)
                    }
                }
            }

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

        search.setOnClickListener(View.OnClickListener {
            (activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView))?.selectedItemId =
                R.id.home;

            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                SearchFragment()
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

        dashboard.setOnClickListener(View.OnClickListener {
            (activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView))?.selectedItemId =
                R.id.dashboard;

            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                DashboardFragment()
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

        news1.setOnClickListener(View.OnClickListener {
            val url = "https://www.cdc.gov/diabetes/managing/managing-blood-sugar/bloodglucosemonitoring.html"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        })

        news2.setOnClickListener(View.OnClickListener {
            val url = "https://www.helpguide.org/articles/diets/the-diabetes-diet.htm"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        })

        news3.setOnClickListener(View.OnClickListener {
            val url = "https://www.cdc.gov/diabetes/library/features/living-well-with-diabetes.html"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        })

        news4.setOnClickListener(View.OnClickListener {
            val url = "https://www.everydayhealth.com/type-2-diabetes/living-with/great-exercises-for-people-with-diabetes/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
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