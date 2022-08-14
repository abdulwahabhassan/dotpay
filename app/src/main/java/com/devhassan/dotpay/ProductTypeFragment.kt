package com.devhassan.dotpay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.devhassan.dotpay.databinding.FragmentBrandsBinding
import com.devhassan.dotpay.databinding.FragmentProductTypeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductTypeFragment : Fragment() {

    private var _binding: FragmentProductTypeBinding? = null
    private val binding get() = _binding!!
    private lateinit var productTypeAdapter: ProductTypeAdapter

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
        initProductTypeAdapter()
    }

    private fun initProductTypeAdapter() {
        productTypeAdapter = ProductTypeAdapter(
            utils = utils,
            onProductTypeClicked = { position: Int, itemAtPosition: ProductType ->
                Toast.makeText(
                    requireContext(),
                    "Brand ${itemAtPosition.name} $position clicked",
                    Toast.LENGTH_LONG
                ).show()
                val action = ProductTypeFragmentDirections.actionProductTypeFragmentToProductsFragment()
                findNavController().navigate(action)
            },
            onProductClicked = { position: Int, itemAtPosition: Product ->
                Toast.makeText(
                    requireContext(),
                    "Product ${itemAtPosition.name} $position clicked",
                    Toast.LENGTH_LONG
                ).show()
                val action = ProductTypeFragmentDirections.actionProductTypeFragmentToProductDetailsFragment()
                findNavController().navigate(action)
            }
        )
        productTypeAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.productTypeRV.adapter = productTypeAdapter
        productTypeAdapter.submitList(
            listOf(
                ProductType("My brand", Product.products),
                ProductType("Our brand", Product.products.dropLast(2)),
                ProductType("Your brand", Product.products),
                ProductType("Her brand", Product.products.dropLast(1))
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}