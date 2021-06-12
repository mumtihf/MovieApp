package com.mumti.mycore.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mumti.mycore.domain.model.TvShow
import com.mumti.mycore.R
import com.mumti.mycore.databinding.ItemsFilmBinding

class TvAdapter(private val callback: TvFragmentCallback) : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {

    private var listTv = ArrayList<TvShow>()
    var onItemClick: ((TvShow) -> Unit)? = null

    fun setTvShow(tv: List<TvShow>?) {
        if (tv == null) return
        this.listTv.clear()
        this.listTv.addAll(tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemsTvBinding = ItemsFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemsTvBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = listTv[position]
        holder.bind(tv)
    }

    override fun getItemCount(): Int {
        return listTv.size
    }

    inner class TvViewHolder(private val binding: ItemsFilmBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvShow) {
            with(binding) {
                txtTitle.text = tv.title
                tvItemDate.text = tv.date
                /*
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFilmActivity::class.java)
                    intent.putExtra(DetailFilmActivity.EXTRA_TV, tv)
                    itemView.context.startActivity(intent)
                }

                 */
                imgShare.setOnClickListener { callback.onShareClick(tv) }
                Glide.with(itemView.context)
                    .load(tv.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imagePoster)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listTv[absoluteAdapterPosition])
            }
        }
    }

    interface TvFragmentCallback {
        fun onShareClick(tv: TvShow)
    }
}