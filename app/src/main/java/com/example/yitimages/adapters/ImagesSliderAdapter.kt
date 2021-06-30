package com.example.yitimages.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.yitimages.R
import com.example.yitimages.databinding.ItemGalleryImageBinding
import com.example.yitimages.datamodels.ImageResult

class ImagesSliderAdapter(var context: Context, var imageScrolledListener: ImageScrolledListener) :
    RecyclerView.Adapter<ImagesSliderAdapter.ViewHolder>() {

    private var mImagesList: List<ImageResult> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemGalleryImageBinding>(
            LayoutInflater.from(context),
            R.layout.item_gallery_image,
            parent,
            false
        )

        return ViewHolder(binding, context, imageScrolledListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setImage(mImagesList[position].webformatURL)
    }

    override fun getItemCount(): Int {
        return mImagesList.size
    }

    fun setData(images: List<ImageResult>) {
        mImagesList = images
        notifyDataSetChanged()
    }

    class ViewHolder(
        var binding: ItemGalleryImageBinding,
        var context: Context,
        var imageScrolledListener: ImageScrolledListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun setImage(url: String?) {
            val reqOpt = RequestOptions
                .fitCenterTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(reqOpt)
                .dontAnimate()
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        binding.imageSliderItem.setImageBitmap(resource)
                        imageScrolledListener.let {
                            imageScrolledListener.onImageScrolled(resource)
                        }
                    }
                })
        }
    }

    interface ImageScrolledListener {
        fun onImageScrolled(bitmap: Bitmap)
    }
}