package com.devhassan.dotpay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.devhassan.dotpay.Utils
import com.devhassan.dotpay.databinding.LayoutBrandItemBinding
import com.devhassan.dotpay.model.entity.Brand
import com.devhassan.dotpay.model.entity.Product
import timber.log.Timber
import java.util.*


class BrandAdapter(
    private val utils: Utils,
    private val onSeeMoreClicked: (position: Int, itemAtPosition: Brand) -> Unit,
    private val onProductClicked: (position: Int, itemAtPosition: Product) -> Unit
) : ListAdapter<Brand, BrandAdapter.ProductTypeVH>(object :
    DiffUtil.ItemCallback<Brand>() {

    override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductTypeVH {
        val binding =
            LayoutBrandItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        private val binding: LayoutBrandItemBinding,
        onItemClick: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.brandSeeMoreTV.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }

        fun bind(brand: Brand) {
            Timber.d("$brand")
            with(binding) {
                brandNameTV.text = brand.name.replace(Regex("\\W"), " ")
                    .trim()
                    .replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }

                val layoutManager = LinearLayoutManager(root.context, HORIZONTAL, false)
                layoutManager.initialPrefetchItemCount = brand.products.size
                brandProductsRV.layoutManager = layoutManager

                val productAdapter = ProductAdapter(
                    ProductAdapter.ProductAdapterViewType.HORIZONTAL_VIEW_TYPE,
                    utils
                ) { position, itemAtPosition ->
                    onProductClicked(position, itemAtPosition)
                }
                brandProductsRV.adapter = productAdapter
                productAdapter.submitList(brand.products)

            }
        }

    }
}