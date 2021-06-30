package com.example.yitimages.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.yitimages.R
import com.example.yitimages.databinding.ImageItemBinding
import com.example.yitimages.datamodels.ImageResult


class ImageItemView(context: Context) : FrameLayout(context) {

    var binding: ImageItemBinding =
        ImageItemBinding.inflate(LayoutInflater.from(context), this, true)

    lateinit var imageResult: ImageResult
    var screenHeight: Float = 0f

    init {
        removeAllViews()
        addView(binding.root)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        binding.imageItemImage.layoutParams.apply {
            var ratio = imageResult.webformatHeight.toFloat() / imageResult.webformatWidth.toFloat()

            height = (screenHeight / 6).toInt()
            width = (imageResult.webformatWidth * ratio).toInt()
        }
        super.onMeasure(height, width)
    }

    fun initImage() {
        Glide.with(context).load(imageResult.webformatURL)
            .apply(RequestOptions.placeholderOf(R.color.grey))
            .into(binding.imageItemImage)
    }
}