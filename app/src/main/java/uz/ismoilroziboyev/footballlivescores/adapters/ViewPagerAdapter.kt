package uz.ismoilroziboyev.footballlivescores.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.ismoilroziboyev.footballlivescores.fragments.MatchesFragment
import uz.ismoilroziboyev.footballlivescores.fragments.TableFragment

class ViewPagerAdapter(fragment: Fragment, val id: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MatchesFragment.newInstance(id)
            else -> TableFragment.newInstance(id)
        }
    }


}