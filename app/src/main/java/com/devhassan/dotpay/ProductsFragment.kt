package com.devhassan.dotpay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.devhassan.dotpay.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var brandAdapter: BrandAdapter

    @Inject
    lateinit var utils: Utils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
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
            },
            onProductClicked = { position: Int, itemAtPosition: Product ->
                Toast.makeText(
                    requireContext(),
                    "Product ${itemAtPosition.name} $position clicked",
                    Toast.LENGTH_LONG
                ).show()
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