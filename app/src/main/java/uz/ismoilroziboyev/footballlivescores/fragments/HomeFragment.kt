package uz.ismoilroziboyev.footballlivescores.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import uz.ismoilroziboyev.footballlivescores.R
import uz.ismoilroziboyev.footballlivescores.adapters.LeagueDatasRvAdapter
import uz.ismoilroziboyev.footballlivescores.database.AppDatabase
import uz.ismoilroziboyev.footballlivescores.database.entities.LeaguesEntity
import uz.ismoilroziboyev.footballlivescores.databinding.FragmentHomeBinding
import uz.ismoilroziboyev.footballlivescores.utils.NetworkHelper
import uz.ismoilroziboyev.footballlivescores.viewmodels.ViewModel
import uz.ismoilroziboyev.footballlivescores.viewmodels.ViewModelFactory
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: ViewModel
    private val job = Job()
    private lateinit var appDatabase: AppDatabase
    private lateinit var leagueDatasRvAdapter: LeagueDatasRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        viewModel = ViewModelProvider(this,
            ViewModelFactory(appDatabase, NetworkHelper(requireContext())))[ViewModel::class.java]

        leagueDatasRvAdapter =
            LeagueDatasRvAdapter(object : LeagueDatasRvAdapter.OnItemClickListener {
                override fun onItemClickListener(item: LeaguesEntity) {
                    val bundle = Bundle()
                    bundle.putString("id", item.id)
                    findNavController().navigate(R.id.leagueFragment, bundle)
                }

            }, appDatabase)

        leagueDatasRvAdapter.submitList(appDatabase.leaguesDao().getAllLeaguesData())

        binding.recyclerView.adapter = leagueDatasRvAdapter

        return binding.root

    }

    override val coroutineContext: CoroutineContext
        get() = job


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}