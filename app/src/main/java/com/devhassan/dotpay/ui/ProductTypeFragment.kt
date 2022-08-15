package com.devhassan.dotpay.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.devhassan.dotpay.model.Product
import com.devhassan.dotpay.model.ProductType
import com.devhassan.dotpay.adapter.ProductTypeAdapter
import com.devhassan.dotpay.Utils
import com.devhassan.dotpay.databinding.FragmentProductTypeBinding
import com.devhassan.dotpay.model.uistate.AppUIState
import com.devhassan.dotpay.vm.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToastStyle
import javax.inject.Inject

@AndroidEntryPoint
class ProductTypeFragment : Fragment() {

    private var _binding: FragmentProductTypeBinding? = null
    private val binding get() = _binding!!
    private lateinit var productTypeAdapter: ProductTypeAdapter
    private val viewModel: AppViewModel by activityViewModels()

    @Inject
    lateinit var utils: Utils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productTypeMaterialToolbar.setupWithNavController(findNavController())
        initProductTypeAdapter()
        observeViewModel()
    }

    private fun initProductTypeAdapter() {
        productTypeAdapter = ProductTypeAdapter(
            utils = utils,
            onSeeMoreClicked = { position: Int, itemAtPosition: ProductType ->
                viewModel.updateSelectedProductType(itemAtPosition.name)
                val action =
                    ProductTypeFragmentDirections.actionProductTypeFragmentToProductsFragment()
                findNavController().navigate(action)
            },
            onProductClicked = { position: Int, itemAtPosition: Product ->
                viewModel.updateCurrentProduct(itemAtPosition)
                val action =
                    ProductTypeFragmentDirections.actionProductTypeFragmentToProductDetailsFragment()
                findNavController().navigate(action)
            }
        )
        productTypeAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.productTypeRV.adapter = productTypeAdapter
    }

    private fun observeViewModel() {
        viewModel.appUIState.observe(viewLifecycleOwner) { appUiState ->
            when (appUiState) {
                is AppUIState.Loading -> {
                    showLoadingIcon(true)
                }
                is AppUIState.Error -> {
                    showLoadingIcon(false)
                    utils.showMotionToast(
                        requireActivity(),
                        appUiState.errorMessage,
                        MotionToastStyle.ERROR
                    )
                }
                is AppUIState.Success -> {
                    val productTypeFragmentUIState = appUiState.productTypeFragmentUIState
                    if (productTypeFragmentUIState?.productTypes.isNullOrEmpty()) {
                        if (!productTypeFragmentUIState?.errorMessage.isNullOrEmpty()) {
                            utils.showMotionToast(
                                requireActivity(),
                                productTypeFragmentUIState?.errorMessage,
                                MotionToastStyle.INFO
                            )
                        }
                    } else {
                        productTypeAdapter.submitList(productTypeFragmentUIState?.productTypes)

                    }
                    showLoadingIcon(false)
                }
            }

        }
    }

    private fun showLoadingIcon(bool: Boolean) {
        if (bool) {
            binding.progressIndicator.visibility = View.VISIBLE
            binding.productTypeRV.visibility = View.INVISIBLE
        } else {
            binding.progressIndicator.visibility = View.INVISIBLE
            binding.productTypeRV.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}