package com.example.testmobile.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmobile.R
import com.example.testmobile.presentation.adapters.ArticleAdapterListener
import com.example.testmobile.data.dto.ArticleDTO
import com.example.testmobile.databinding.FragmentHomeBinding
import com.example.testmobile.presentation.adapters.ArticleAdapter
import com.example.testmobile.utils.Failure
import com.example.testmobile.utils.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    // For RecyclerView
    private val listener = object : ArticleAdapterListener {
        override fun onArticleSelected(articleDTO: ArticleDTO) {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(articleDTO))
        }
    }
    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialise adapter
        adapter = ArticleAdapter(requireContext(), listener)

        // Fill recyclerView
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.setLayoutManager(LinearLayoutManager(getActivity()))
        binding.recyclerview.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        // Show load Progress
        binding.progressEmpty.visibility = View.VISIBLE

        if (Utility.checkForInternet(requireContext())) {
            // Get article List
            viewModel.getArticleList()
        } else {
            Toast.makeText(requireContext(), getString(R.string.no_internet__connexion), Toast.LENGTH_LONG).show()
            binding.progressEmpty.visibility = View.GONE
        }

        // Response viewModels
        initViewModels()
    }

    private fun initViewModels() {
        // Listen mode choice
        viewModel.articles.observe(viewLifecycleOwner){
            if (it != null) {
                binding.progressEmpty.visibility = View.GONE
                adapter.differ.submitList(it)
            }
        }

        viewModel.error.observe(viewLifecycleOwner){
            if (it != null) {
                when(it) {
                    is Failure.NetworkFailure -> Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                    is Failure.FeatureFailure -> getString(R.string.feature_failure)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}