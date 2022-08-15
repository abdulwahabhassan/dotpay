package com.devhassan.dotpay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devhassan.dotpay.Utils
import com.devhassan.dotpay.databinding.LayoutProductTypeItemBinding
import com.devhassan.dotpay.model.entity.Product
import com.devhassan.dotpay.model.entity.ProductType
import timber.log.Timber

class ProductTypeAdapter(
    private val utils: Utils,
    private val onSeeMoreClicked: (position: Int, itemAtPosition: ProductType) -> Unit,
    private val onProductClicked: (position: Int, itemAtPosition: Product) -> Unit
) : ListAdapter<ProductType, ProductTypeAdapter.ProductTypeVH>(object :
    DiffUtil.ItemCallback<ProductType>() {

    override fun areItemsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
        return oldItem == newItem
    }

}) {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductTypeVH {
        val binding =
            LayoutProductTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductTypeVH(
            binding,
            onItemClick = { position ->
                try {
                    val itemAtPosition = currentList[position]
                    this.onSeeMoreClicked(position, itemAtPosition)
                } catch (e: Exception) {
                }

            }
        )
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: ProductTypeVH, position: Int) {
        val itemAtPosition = currentList[position]
        holder.bind(itemAtPosition)
    }

    inner class ProductTypeVH(
        private val binding: LayoutProductTypeItemBinding,
        onItemClick: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.productTypeSeeMoreTV.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }

        fun bind(productType: ProductType) {
            Timber.d("$productType")
            with(binding) {
                productTypeNameTV.text = productType.name

                val layoutManager =
                    LinearLayoutManager(root.context, RecyclerView.HORIZONTAL, false)
                layoutManager.initialPrefetchItemCount = productType.products.size
                productTypeProductsRV.layoutManager = layoutManager

                val productAdapter = ProductAdapter(
                    ProductAdapter.ProductAdapterViewType.HORIZONTAL_VIEW_TYPE,
                    utils
                ) { position, itemAtPosition ->
                    onProductClicked(position, itemAtPosition)
                }
                productTypeProductsRV.adapter = productAdapter
                productTypeProductsRV.setRecycledViewPool(viewPool)
                productAdapter.submitList(productType.products)

            }
        }

    }
}