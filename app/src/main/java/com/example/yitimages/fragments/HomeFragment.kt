package com.example.yitimages.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yitimages.R
import com.example.yitimages.adapters.ImagesAdapter
import com.example.yitimages.databinding.FragmentHomeBinding
import com.example.yitimages.datamodels.ImageResult
import com.example.yitimages.utils.UIUtils
import com.example.yitimages.viewmodels.ImagesViewModel

class HomeFragment: Fragment(R.layout.fragment_home) {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mViewModel: ImagesViewModel

    private lateinit var imagesAdapter: ImagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentHomeBinding.bind(view)
        mViewModel = ViewModelProvider(requireActivity()).get(ImagesViewModel::class.java)
        mBinding.imagesViewModel = mViewModel

        mBinding.homeSearchBtn.setOnClickListener {
            mBinding.homeSearchEt.text.toString().apply {
                if (!TextUtils.isEmpty(this)) {
                    mViewModel.searchImages(this)
                }
            }
        }

        initObservers()
        initViewContent()
    }

    override fun onResume() {
        super.onResume()
        if (mViewModel.mRawImagesList.size == 0 && !TextUtils.isEmpty(mViewModel.preferenceHelper.getLastSearch())) {
            mViewModel.searchImages(mViewModel.preferenceHelper.getLastSearch())
        }
    }

    private fun initObservers() {
        mViewModel.apply {
            imagesList.observe(viewLifecycleOwner) {
                imagesAdapter.setData(it)
            }
            progress.observe(viewLifecycleOwner) { show ->
                mBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initViewContent() {
        imagesAdapter = ImagesAdapter(UIUtils.getHeightDp(requireActivity()))
        mBinding.homeRecyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(), NUM_OF_COLUMNS)
            adapter = imagesAdapter

            setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if((layoutManager as GridLayoutManager).findLastVisibleItemPosition() == ((adapter as ImagesAdapter).itemCount - 1)
                    && (adapter as ImagesAdapter).itemCount < mViewModel.mCurrentSearchTotalItems
                    && !mViewModel.mFetchingInProcess) {
                    mViewModel.fetchNextPage()
                }
            }
        }
    }

    companion object {
        const val NUM_OF_COLUMNS = 4
    }
}
