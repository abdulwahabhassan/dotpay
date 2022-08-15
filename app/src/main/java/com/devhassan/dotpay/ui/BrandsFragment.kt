package com.devhassan.dotpay.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.devhassan.dotpay.model.Brand
import com.devhassan.dotpay.BrandAdapter
import com.devhassan.dotpay.model.Product
import com.devhassan.dotpay.Utils
import com.devhassan.dotpay.databinding.FragmentBrandsBinding
import com.devhassan.dotpay.model.uistate.AppUIState
import com.devhassan.dotpay.vm.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BrandsFragment : Fragment() {

    private var _binding: FragmentBrandsBinding? = null
    private val binding get() = _binding!!
    private lateinit var brandAdapter: BrandAdapter
    private val viewModel: AppViewModel by activityViewModels()

    @Inject
    lateinit var utils: Utils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrandsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBrandAdapter()
        observeViewModel()
    }

    private fun initBrandAdapter() {
        brandAdapter = BrandAdapter(
            utils = utils,
            onSeeMoreClicked = { position: Int, itemAtPosition: Brand ->
                Toast.makeText(
                    requireContext(),
                    "Brand ${itemAtPosition.name} $position clicked",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.updateSelectedBrand(itemAtPosition.name)
                val action = BrandsFragmentDirections.actionBrandsFragmentToProductTypeFragment()
                findNavController().navigate(action)
            },
            onProductClicked = { position: Int, itemAtPosition: Product ->
                Toast.makeText(
                    requireContext(),
                    "Product ${itemAtPosition.name} $position clicked",
                    Toast.LENGTH_LONG
                ).show()
                val action = BrandsFragmentDirections.actionBrandsFragmentToProductDetailsFragment()
                findNavController().navigate(action)
            }
        )
        brandAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.brandRV.adapter = brandAdapter

    }

    private fun observeViewModel() {
        viewModel.appUIState.observe(viewLifecycleOwner) { appUiState ->
            when (appUiState) {
                is AppUIState.Loading -> {
                    showLoadingIcon(true)
                }
                is AppUIState.Error -> {
                    Toast.makeText(requireContext(), appUiState.errorMessage, Toast.LENGTH_LONG)
                        .show()
                    showLoadingIcon(false)
                }
                is AppUIState.Success -> {
                    val brandFragmentUIState = appUiState.brandFragmentUIState
                    if (brandFragmentUIState?.brands.isNullOrEmpty()) {
                        if (!brandFragmentUIState?.errorMessage.isNullOrEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                brandFragmentUIState?.errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        brandAdapter.submitList(appUiState.brandFragmentUIState?.brands
                            ?.sortedBy { it.name })
                    }
                    showLoadingIcon(false)
                }
            }

        }
    }

    private fun showLoadingIcon(bool: Boolean) {
        if (bool) {
            binding.progressIndicator.visibility = VISIBLE
            binding.brandRV.visibility = INVISIBLE
        } else {
            binding.progressIndicator.visibility = INVISIBLE
            binding.brandRV.visibility = VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}