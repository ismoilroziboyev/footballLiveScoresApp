package uz.ismoilroziboyev.footballlivescores.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.ismoilroziboyev.footballlivescores.R
import uz.ismoilroziboyev.footballlivescores.adapters.ViewPagerAdapter
import uz.ismoilroziboyev.footballlivescores.database.AppDatabase
import uz.ismoilroziboyev.footballlivescores.databinding.FragmentLeagueBinding
import uz.ismoilroziboyev.footballlivescores.databinding.ItemTabBinding
import uz.ismoilroziboyev.footballlivescores.utils.*

private const val ARG_PARAM1 = "id"

class LeagueFragment : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    private lateinit var binding: FragmentLeagueBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLeagueBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())

        val league = appDatabase.leaguesDao().getLeagueById(param1!!)
        viewPagerAdapter = ViewPagerAdapter(this, param1!!)



        binding.apply {
            viewPager.adapter = viewPagerAdapter

            viewPager.currentItem = 1
            imageView.setImageWithUrl(league.image)
            backButton.setOnClickListener { findNavController().popBackStack() }
            titleTv.text = league.name

            TabLayoutMediator(tabLayout,
                viewPager
            ) { tab, position ->

                val tabBinding = ItemTabBinding.inflate(inflater)

                if (position == 0) {
                    tabBinding.title.text = "Matches"
                    tabBinding.title.setTextColor(Color.parseColor("#E1FFFFFF"))
                } else {
                    tabBinding.title.text = "Table"
                    tabBinding.title.setTextColor(Color.WHITE)
                }
                tab.customView = tabBinding.root

            }.attach()


            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val tabBinding = ItemTabBinding.bind(tab?.customView!!)

                    tabBinding.title.setTextColor(Color.parseColor("#E1FFFFFF"))
                }


                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val tabBinding = ItemTabBinding.bind(tab?.customView!!)
                    tabBinding.title.setTextColor(Color.WHITE)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }

        return binding.root
    }

}