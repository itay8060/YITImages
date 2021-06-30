package com.example.yitimages.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.yitimages.R
import com.example.yitimages.databinding.ImageItemBinding
import com.example.yitimages.datamodels.ImageResult
import com.example.yitimages.fragments.HomeFragmentDirections


class ImagesAdapter(var screenHeight: Float) : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    private var imagesList: List<ImageResult> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        var binding = DataBindingUtil.inflate<ImageItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.image_item,
            parent,
            false
        )

//        val imageItemView = ImageItemView(parent.context)

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageResult = imagesList[position]
        holder.bindImage(imageResult, screenHeight)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    fun setData(images: List<ImageResult>) {
        imagesList = images
        notifyDataSetChanged()
    }

    class ImageViewHolder(var binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        lateinit var imageResult: ImageResult

        fun bindImage(image: ImageResult, screenHeight: Float) {
            imageResult = image
//            view.imageResult = image
//            view.screenHeight = screenHeight todo
//            view.initImage()
            Glide.with(itemView.context).load(imageResult.previewURL)
                .apply(RequestOptions.placeholderOf(R.color.grey))
                .into(binding.imageItemImage)

            adjustViewSize(screenHeight)

            itemView.setOnClickListener(this)
        }

        private fun adjustViewSize(screenHeight: Float) {
//            var ratio = imageResult.previewHeight.toFloat() / imageResult.previewWidth.toFloat()
            var ratio = if (imageResult.previewHeight > imageResult.previewWidth) {
                imageResult.previewWidth.toFloat() / imageResult.previewHeight.toFloat()
            } else {
                imageResult.previewHeight.toFloat() / imageResult.previewWidth.toFloat()
            }
            val heightRatio = imageResult.previewHeight / (screenHeight / 6)
            if (ratio > 0) {
                itemView.layoutParams.apply {
                    height = (screenHeight / 6).toInt()
                    width = ((height * ratio)).toInt()
//                    width = WRAP_CONTENT
                }
//                binding.imageItemImage.layoutParams.apply {
//                    val heightRatio = imageResult.previewHeight / (screenHeight / 6)
//                    width = (imageResult.previewWidth * heightRatio).toInt()
//                    height = (imageResult.previewHeight * heightRatio).toInt()
//                }
            }
        }

        private fun adjustViewSize2(screenHeight: Float) {
            var ratio = imageResult.previewHeight.toFloat() / imageResult.previewWidth.toFloat()
//            ratio = if (imageResult.previewHeight > imageResult.previewWidth) {
//                imageResult.previewWidth.toFloat() / imageResult.previewHeight.toFloat() todo
//            } else {
//                imageResult.previewHeight.toFloat() / imageResult.previewWidth.toFloat()
//            }

            if (ratio > 0) {
                itemView.layoutParams.apply {
                    height = (screenHeight / 6).toInt()
                    width = (height * ratio).toInt()
//                    width = if ((height * ratio).toInt() > imageResult.previewWidth) imageResult.previewWidth else (height * ratio).toInt()
                }
            }
        }

        override fun onClick(v: View?) {
            itemView.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToImagesPagerDialogFragment(adapterPosition))
        }
    }
}