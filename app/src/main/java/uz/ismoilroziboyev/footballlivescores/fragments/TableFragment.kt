package uz.ismoilroziboyev.footballlivescores.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.ismoilroziboyev.footballlivescores.R
import uz.ismoilroziboyev.footballlivescores.adapters.TeamsRvAdapter
import uz.ismoilroziboyev.footballlivescores.database.AppDatabase
import uz.ismoilroziboyev.footballlivescores.databinding.FragmentTableBinding
import uz.ismoilroziboyev.footballlivescores.utils.NetworkHelper
import uz.ismoilroziboyev.footballlivescores.utils.StandingsResource
import uz.ismoilroziboyev.footballlivescores.utils.hide
import uz.ismoilroziboyev.footballlivescores.utils.show
import uz.ismoilroziboyev.footballlivescores.viewmodels.ViewModel
import uz.ismoilroziboyev.footballlivescores.viewmodels.ViewModelFactory

private const val ARG_PARAM1 = "param1"

class TableFragment : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    private lateinit var binding: FragmentTableBinding
    private lateinit var viewModel: ViewModel
    private lateinit var appDatabase: AppDatabase
    private lateinit var teamsRvAdapter: TeamsRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTableBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())

        viewModel = ViewModelProvider(this,
            ViewModelFactory(appDatabase, NetworkHelper(requireContext())))[ViewModel::class.java]

        viewModel.fetchStandingsData(param1!!)

        teamsRvAdapter = TeamsRvAdapter()


        binding.apply { recyclerView.adapter = teamsRvAdapter }


        lifecycleScope.launch {
            viewModel.getStandingsData().collect {
                when (it) {
                    is StandingsResource.Loading -> {
                        binding.loading.show()
                        binding.recyclerView.hide()
                    }
                    is StandingsResource.Error -> {
                        binding.recyclerView.hide()
                        binding.loading.hide()
                    }

                    is StandingsResource.Succes -> {
                        teamsRvAdapter.submitList(it.data.data.standings)
                        binding.recyclerView.show()
                        binding.loading.hide()
                    }
                }
            }
        }




        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            TableFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}