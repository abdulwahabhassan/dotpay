package com.devhassan.dotpay.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.devhassan.dotpay.R
import com.devhassan.dotpay.model.entity.Brand
import com.devhassan.dotpay.adapter.BrandAdapter
import com.devhassan.dotpay.model.entity.Product
import com.devhassan.dotpay.Utils
import com.devhassan.dotpay.databinding.FragmentBrandsBinding
import com.devhassan.dotpay.model.uistate.AppUIState
import com.devhassan.dotpay.vm.AppViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToastStyle
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
                viewModel.updateSelectedBrand(itemAtPosition.name)
                val action = BrandsFragmentDirections.actionBrandsFragmentToProductTypeFragment()
                findNavController().navigate(action)
            },
            onProductClicked = { position: Int, itemAtPosition: Product ->
                viewModel.updateCurrentProduct(itemAtPosition)
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
                    showErrorText(false)
                }
                is AppUIState.Error -> {
                    showLoadingIcon(false)
                    utils.showMotionToast(
                        requireActivity(),
                        appUiState.errorMessage,
                        MotionToastStyle.ERROR
                    )
                    showErrorText(true)
                    showSnackBar()
                }
                is AppUIState.Success -> {
                    val brandFragmentUIState = appUiState.brandFragmentUIState
                    if (brandFragmentUIState?.brands.isNullOrEmpty()) {
                        if (!brandFragmentUIState?.errorMessage.isNullOrEmpty()) {
                            utils.showMotionToast(
                                requireActivity(),
                                brandFragmentUIState?.errorMessage,
                                MotionToastStyle.INFO
                            )
                        }
                    } else {
                        brandAdapter.submitList(appUiState.brandFragmentUIState?.brands
                            ?.sortedBy { it.name })
                    }
                    showLoadingIcon(false)
                    showErrorText(false)
                }
            }
        }
    }

    private fun showErrorText(bool: Boolean) {
        if (bool) {
            binding.errorTV.visibility = VISIBLE
        } else {
            binding.errorTV.visibility = INVISIBLE
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

    private fun showSnackBar() {
        Snackbar.make(
            binding.root,
            "Error in communication!",
            Snackbar.LENGTH_LONG
        ).setTextColor(requireContext().getColor(R.color.white))
            .setActionTextColor(requireContext().getColor(R.color.white))
            .setBackgroundTint(requireContext().getColor(R.color.black))
            .setDuration(Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                showErrorText(false)
                showLoadingIcon(true)
                viewModel.retrieveProducts()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}