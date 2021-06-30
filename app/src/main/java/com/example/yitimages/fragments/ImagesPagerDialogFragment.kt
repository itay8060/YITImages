package com.example.yitimages.fragments

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.yitimages.R
import com.example.yitimages.adapters.ImagesSliderAdapter
import com.example.yitimages.databinding.ImagePagerFragmentDialogBinding
import com.example.yitimages.datamodels.ImageResult
import com.example.yitimages.viewmodels.ImagesViewModel

class ImagesPagerDialogFragment() : DialogFragment(R.layout.image_pager_fragment_dialog), ImagesSliderAdapter.ImageScrolledListener {

    private lateinit var mBinding: ImagePagerFragmentDialogBinding
    private lateinit var mViewModel: ImagesViewModel

    private var mImagesList: List<ImageResult>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = ImagePagerFragmentDialogBinding.bind(view)
        mViewModel = ViewModelProvider(requireActivity()).get(ImagesViewModel::class.java)

        mImagesList = mViewModel.mRawImagesList
        initViews()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
    }

    private fun initViews() {
        var currentPosition = 0
        arguments?.let {
            currentPosition = it.getInt(CURRENT_POSITION_KEY)
        }
        val adapter = ImagesSliderAdapter(requireContext(), this)
        mImagesList?.let {
            if (it.isNotEmpty()) {
                mBinding.imagePagerDialogPager.apply {
                    offscreenPageLimit = 1
                    adapter.setData(it)
                    setAdapter(adapter)
                    setCurrentItem(currentPosition, false)
                }
            }
        }
    }

    override fun onImageScrolled(bitmap: Bitmap) {
        mViewModel.selectedImage = bitmap
    }

    companion object{
        const val CURRENT_POSITION_KEY = "currentImagePosition"
    }
}