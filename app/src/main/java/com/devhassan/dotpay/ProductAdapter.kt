package com.devhassan.dotpay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devhassan.dotpay.databinding.LayoutGridProductItemBinding
import com.devhassan.dotpay.databinding.LayoutHorizontalProductItemBinding
import timber.log.Timber

class ProductAdapter(
    private val viewType: ProductAdapterViewType,
    private val utils: Utils,
    private val onProductClicked: (position: Int, itemAtPosition: Product) -> Unit
) : ListAdapter<Product, RecyclerView.ViewHolder>(object :
    DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            ProductAdapterViewType.HORIZONTAL_VIEW_TYPE.ordinal -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_horizontal_product_item,
                    parent,
                    false
                )
                val binding = LayoutHorizontalProductItemBinding.bind(view)
                viewHolder = ProductHorizontalVH(binding, onItemClick = { position ->
                    try {
                        val itemAtPosition = currentList[position]
                        this.onProductClicked(position, itemAtPosition)
                    } catch (e: Exception) { }
                })
            }
            ProductAdapterViewType.GRID_VIEW_TYPE.ordinal -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_grid_product_item,
                    parent,
                    false
                )
                val binding = LayoutGridProductItemBinding.bind(view)
                viewHolder = ProductGridVH(
                    binding,
                    onItemClick = { position ->
                        try {
                            val itemAtPosition = currentList[position]
                            this.onProductClicked(position, itemAtPosition)
                        } catch (e: Exception) { }
                    }
                )
            }
        }
        return viewHolder!!
    }

    override fun getItemViewType(position: Int): Int {
        return viewType.ordinal
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemAtPosition = currentList[position]
        when (holder) {
            is ProductHorizontalVH -> {
                holder.bind(itemAtPosition)
            }
            is ProductGridVH -> {
                holder.bind(itemAtPosition)
            }
        }
    }

    inner class ProductHorizontalVH(
        private val binding: LayoutHorizontalProductItemBinding,
        onItemClick: (position: Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }

        fun bind(product: Product) {
            Timber.d("$product")
            with(binding) {
                productIV.setImageResource(R.drawable.ic_launcher_background)
                productNameTV.text = product.name
            }
        }
    }

    inner class ProductGridVH(
        private val binding: LayoutGridProductItemBinding,
        onItemClick: (position: Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }

        fun bind(product: Product) {
            Timber.d("$product")
            with(binding) {
                productIV.setImageResource(R.drawable.ic_launcher_background)
                productNameTV.text = product.name
            }
        }
    }

    enum class ProductAdapterViewType {
        HORIZONTAL_VIEW_TYPE,
        GRID_VIEW_TYPE
    }
}