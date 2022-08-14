package com.devhassan.dotpay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.devhassan.dotpay.databinding.FragmentBrandsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BrandsFragment : Fragment() {

    private var _binding: FragmentBrandsBinding? = null
    private val binding get() = _binding!!
    private lateinit var brandAdapter: BrandAdapter

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
    }

    private fun initBrandAdapter() {
        brandAdapter = BrandAdapter(
            utils = utils,
            onBrandClicked = { position: Int, itemAtPosition: Brand ->
                Toast.makeText(
                    requireContext(),
                    "Brand ${itemAtPosition.name} $position clicked",
                    Toast.LENGTH_LONG
                ).show()
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
        brandAdapter.submitList(
            listOf(
                Brand("My brand", Product.products),
                Brand("Our brand", Product.products.dropLast(2)),
                Brand("Your brand", Product.products),
                Brand("Her brand", Product.products.dropLast(1))
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}