package com.devhassan.dotpay.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.devhassan.dotpay.R
import com.devhassan.dotpay.Utils
import com.devhassan.dotpay.databinding.FragmentProductDetailsBinding
import com.devhassan.dotpay.glide.GlideImageLoader
import com.devhassan.dotpay.model.entity.Product
import com.devhassan.dotpay.model.uistate.AppUIState
import com.devhassan.dotpay.vm.AppViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToastStyle
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by activityViewModels()

    @Inject
    lateinit var utils: Utils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productDetailsMaterialToolbar.setupWithNavController(findNavController())
        setUpBottomSheetDialog()
        observeViewModel()
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
                    val productDetailsFragmentUIState = appUiState.productDetailsFragmentUIState
                    if (productDetailsFragmentUIState?.product == null) {
                        if (!productDetailsFragmentUIState?.errorMessage.isNullOrEmpty()) {
                            utils.showMotionToast(
                                requireActivity(),
                                productDetailsFragmentUIState?.errorMessage,
                                MotionToastStyle.INFO
                            )
                        }
                    } else {
                        updateUI(productDetailsFragmentUIState.product)
                    }
                    showLoadingIcon(false)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(product: Product) {

        val options = RequestOptions()
            .error(R.drawable.ic_broken_image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

        val photoUrl = product.imageLink ?: product.apiFeaturedImage

        if (photoUrl != null) {
            GlideImageLoader(binding.productDetailsIV, binding.photoProgressBar).load(
                photoUrl,
                options
            )
        } else {
            binding.productDetailsIV.let {
                Glide.with(binding.root.context).load(R.drawable.ic_broken_image)
                    .into(it)
            }
            binding.photoProgressBar.visibility = View.GONE
        }

        binding.productDetailsNameTV.text = product.name?.replace(Regex("\\W"), " ")
            ?.trim()
        binding.productDetailsPriceTV.text = (product.priceSign ?: product.currency ?: "") +
                product.price.let { if (it == null) "-" else utils.formatCurrency(it) }
        binding.productExtraDetailsTV.text = product.description
            ?.replace(Regex("\\W"), " ")?.trim()
            ?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        binding.productDetailsRB.progress =
            product.rating?.toInt() ?: 0
    }

    private fun showLoadingIcon(bool: Boolean) {
        if (bool) {
            binding.progressIndicator.visibility = View.VISIBLE
            binding.productDetailsNameTV.visibility = View.INVISIBLE
            binding.productDetailsPriceTV.visibility = View.INVISIBLE
            binding.productDetailsRB.visibility = View.INVISIBLE
            binding.productExtraDetailsTV.visibility = View.INVISIBLE
        } else {
            binding.progressIndicator.visibility = View.INVISIBLE
            binding.productDetailsNameTV.visibility = View.VISIBLE
            binding.productDetailsPriceTV.visibility = View.VISIBLE
            binding.productDetailsRB.visibility = View.VISIBLE
            binding.productExtraDetailsTV.visibility = View.VISIBLE
        }
    }

    private fun setUpBottomSheetDialog() {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.productDetailsBottomSheetDialog)
        bottomSheetBehavior.peekHeight = height / 2
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.isDraggable = true

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}