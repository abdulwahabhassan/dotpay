package com.devhassan.dotpay.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.devhassan.dotpay.R
import com.devhassan.dotpay.Utils
import com.devhassan.dotpay.databinding.LayoutGridProductItemBinding
import com.devhassan.dotpay.databinding.LayoutHorizontalProductItemBinding
import com.devhassan.dotpay.glide.GlideImageLoader
import com.devhassan.dotpay.model.entity.Product
import timber.log.Timber
import java.util.*

@SuppressLint("SetTextI18n")
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
                    } catch (e: Exception) {
                    }
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
                        } catch (e: Exception) {
                        }
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
                val options = RequestOptions()
                    .error(R.drawable.ic_broken_image)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

                //using custom glide image loader to indicate progress in time
                val photoUrl = product.imageLink ?: product.apiFeaturedImage

                if (photoUrl != null) {
                    GlideImageLoader(productIV, photoProgressBar).load(photoUrl, options)
                } else {
                    productIV.let {
                        Glide.with(root.context).load(R.drawable.ic_broken_image)
                            .into(it)
                    }
                    photoProgressBar.visibility = View.GONE
                }
                productNameTV.text = product.name?.replace(Regex("\\W"), " ")
                    ?.trim()
                    ?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }
                productDescriptionTV.text = product.description
                    ?.replace(Regex("\\W"), " ")?.trim()
                    ?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }
                productPriceTV.text =
                    (product.priceSign ?: product.currency ?: "") + product.price?.let {
                        utils.formatCurrency(it)
                    }
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
                val options = RequestOptions()
                    .error(R.drawable.ic_broken_image)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

                //using custom glide image loader to indicate progress in time
                val photoUrl = product.imageLink ?: product.apiFeaturedImage

                if (photoUrl != null) {
                    GlideImageLoader(productIV, photoProgressBar).load(photoUrl, options)
                } else {
                   productIV.let {
                        Glide.with(root.context).load(R.drawable.ic_broken_image)
                            .into(it)
                    }
                    photoProgressBar.visibility = View.GONE
                }
                productNameTV.text = product.name?.replace(Regex("\\W"), " ")
                    ?.trim()
                    ?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }
                productDescriptionTV.text = product.description
                    ?.replace(Regex("\\W"), " ")?.trim()
                    ?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }
                productPriceTV.text =
                    (product.priceSign ?: product.currency ?: "") + product.price?.let {
                        utils.formatCurrency(it)
                    }
            }
        }
    }

    enum class ProductAdapterViewType {
        HORIZONTAL_VIEW_TYPE,
        GRID_VIEW_TYPE
    }
}