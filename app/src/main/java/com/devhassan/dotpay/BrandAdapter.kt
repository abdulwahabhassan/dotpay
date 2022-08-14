package com.devhassan.dotpay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.devhassan.dotpay.databinding.LayoutBrandItemBinding
import timber.log.Timber


class BrandAdapter(
    private val utils: Utils,
    private val onBrandClicked: (position: Int, itemAtPosition: Brand) -> Unit,
    private val onProductClicked: (position: Int, itemAtPosition: Product) -> Unit
) : ListAdapter<Brand, BrandAdapter.BrandVH>(object :
    DiffUtil.ItemCallback<Brand>() {

    override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean {
        return oldItem == newItem
    }

}) {

    private val viewPool = RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandVH {
        val binding =
            LayoutBrandItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrandVH(
            binding,
            onItemClick = { position ->
                try {
                    val itemAtPosition = currentList[position]
                    this.onBrandClicked(position, itemAtPosition)
                } catch (e: Exception) { }

            }
        )

    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: BrandVH, position: Int) {
        val itemAtPosition = currentList[position]
        holder.bind(itemAtPosition)
    }


    inner class BrandVH(
        private val binding: LayoutBrandItemBinding,
        onItemClick: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }

        fun bind(brand: Brand) {
            Timber.d("$brand")
            with(binding) {
                brandNameTV.text = brand.name

                val layoutManager = LinearLayoutManager(root.context, HORIZONTAL, false)
                layoutManager.initialPrefetchItemCount = brand.products.size
                brandProductsRV.layoutManager =  layoutManager

                val productAdapter = ProductAdapter(utils) { position, itemAtPosition ->
                    onProductClicked(position, itemAtPosition)
                }
                brandProductsRV.adapter = productAdapter
                brandProductsRV.setRecycledViewPool(viewPool)
                productAdapter.submitList(brand.products)

            }
        }

    }
}