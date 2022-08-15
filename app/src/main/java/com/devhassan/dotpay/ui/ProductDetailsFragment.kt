package com.devhassan.dotpay.ui

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.devhassan.dotpay.databinding.FragmentProductDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint


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
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.productDetailsBottomSheetDialog)
        bottomSheetBehavior.peekHeight = height / 2
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.isDraggable = false

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}