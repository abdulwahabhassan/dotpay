package com.devhassan.dotpay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.devhassan.dotpay.databinding.FragmentProductDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

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
    }


    private fun setUpBottomSheetDialog() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.productDetailsBottomSheetDialog)
        //bottomSheetBehavior.peekHeight = 550
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.state = STATE_HALF_EXPANDED
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}