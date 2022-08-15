package com.devhassan.dotpay.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.devhassan.dotpay.model.Product
import com.devhassan.dotpay.ProductAdapter
import com.devhassan.dotpay.Utils
import com.devhassan.dotpay.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: ProductAdapter

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
        binding.productsMaterialToolbar.setupWithNavController(findNavController())
        initProductAdapter()
    }

    private fun initProductAdapter() {
        productAdapter = ProductAdapter(
            viewType = ProductAdapter.ProductAdapterViewType.GRID_VIEW_TYPE,
            utils = utils,
            onProductClicked = { position: Int, itemAtPosition: Product ->
                Toast.makeText(
                    requireContext(),
                    "Product ${itemAtPosition.name} $position clicked",
                    Toast.LENGTH_LONG
                ).show()
                val action = ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment()
                findNavController().navigate(action)
            }
        )
        productAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.productsRV.adapter = productAdapter
        productAdapter.submitList(Product.products)
    }
}