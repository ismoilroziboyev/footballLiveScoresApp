package uz.ismoilroziboyev.footballlivescores.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.ismoilroziboyev.footballlivescores.R
import uz.ismoilroziboyev.footballlivescores.database.AppDatabase
import uz.ismoilroziboyev.footballlivescores.databinding.FragmentSplashBinding
import uz.ismoilroziboyev.footballlivescores.models.responce.standins.ResStandingsData
import uz.ismoilroziboyev.footballlivescores.utils.LeaguesResource
import uz.ismoilroziboyev.footballlivescores.utils.NetworkHelper
import uz.ismoilroziboyev.footballlivescores.utils.StandingsResource
import uz.ismoilroziboyev.footballlivescores.viewmodels.ViewModel
import uz.ismoilroziboyev.footballlivescores.viewmodels.ViewModelFactory

class SplashFragment : Fragment() {


    private lateinit var binding: FragmentSplashBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        appDatabase = AppDatabase.getInstance(requireContext())

        viewModel = ViewModelProvider(this,
            ViewModelFactory(appDatabase, NetworkHelper(requireContext())))[ViewModel::class.java]

        viewModel.fetchLeaguesData()

        lifecycleScope.launch {
            viewModel.getLeagueData().collect {
                when (it) {
                    is LeaguesResource.Error -> {
                        binding.root.setBackgroundColor(Color.RED)
                    }

                    is LeaguesResource.Success -> {
                        findNavController().popBackStack()
                        findNavController().navigate(R.id.homeFragment)
                    }
                }
            }
        }



        return binding.root
    }

    private fun writeToDbStandings(index: Int) {
        val list = appDatabase.leaguesDao().getAllLeaguesData()

        if (index == list.size - 1) {
        } else {
            viewModel.fetchStandingsData(list[index].id)
            lifecycleScope.launch {
                withContext(Dispatchers.Main) {
                    viewModel.getStandingsData().collect {

                        when (it) {
                            is StandingsResource.Loading -> {

                            }

                            is StandingsResource.Error -> {

                            }

                            is StandingsResource.Succes -> {
                                writeToDbStandings(index + 1)
                            }
                        }
                    }
                }
            }
        }

    }

}