package com.example.assignment_pe3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    lateinit var cardCheckInHistory: CardView
    lateinit var cardJournalHistory: CardView
    lateinit var news1: CardView
    lateinit var news2: CardView
    lateinit var news3: CardView
    lateinit var news4: CardView

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
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        cardCheckInHistory = view.findViewById<CardView>(R.id.card_checkIn_history)
        cardJournalHistory = view.findViewById<CardView>(R.id.card_journal_history)
        news1 = view.findViewById<CardView>(R.id.card_news1)
        news2 = view.findViewById<CardView>(R.id.card_news2)
        news3 = view.findViewById<CardView>(R.id.card_news3)
        news4 = view.findViewById<CardView>(R.id.card_news4)

        cardCheckInHistory.setOnClickListener(View.OnClickListener {
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

        cardJournalHistory.setOnClickListener(View.OnClickListener {
            (activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView))?.selectedItemId =
                R.id.home;

            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_layout,
                JournalHistoryFragment()
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

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}