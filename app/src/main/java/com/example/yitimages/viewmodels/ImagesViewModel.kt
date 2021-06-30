package com.example.yitimages.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yitimages.data.ImagesRepository
import com.example.yitimages.datamodels.ImageResult
import com.example.yitimages.datamodels.ImagesResponse
import com.example.yitimages.preferences.IPreferenceHelper
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch

class ImagesViewModel : ViewModel() {

    lateinit var preferenceHelper: IPreferenceHelper
    private lateinit var mLastQuerySearch: String
    private val mRepository = ImagesRepository()
    var mRawImagesList: ArrayList<ImageResult> = ArrayList()
    var mFetchingInProcess: Boolean = false
    var mCurrentSearchTotalItems: Int = 0
    var selectedImage: Bitmap? = null

    private val images = MutableLiveData<ArrayList<ImageResult>>()
    val imagesList: LiveData<ArrayList<ImageResult>>
        get() = images

    val progress = MutableLiveData<Boolean>()

    fun searchImages(query: String) = viewModelScope.launch {
        preferenceHelper.setLastSearch(query)
        mLastQuerySearch = query
        mFetchingInProcess = true
        val response = mRepository.searchImages(query)
            .catch {
                progress.value = false
            }
            .onStart {
                progress.value = true
            }
            .onCompletion {
                progress.value = false
            }
            .singleOrNull()
        response?.let { it ->
            mFetchingInProcess = false
            it.body()?.let { response ->
                mCurrentSearchTotalItems = response.total
                mRawImagesList = ArrayList(response.hits)
                images.value = mRawImagesList
            }
        }
    }

    fun fetchNextPage() = viewModelScope.launch {
        mFetchingInProcess = true
        var pageNum = (mRawImagesList.size / BATCH_ITEMS_COUNT) + 1
        val response = mRepository.searchImages(mLastQuerySearch, pageNum)
            .catch {
                progress.value = false
            }
            .onStart {
                progress.value = true
            }
            .onCompletion {
                progress.value = false
            }
            .singleOrNull()
        response?.let {
            it.body()?.let { response ->
                mFetchingInProcess = false
                mRawImagesList.addAll(response.hits)
                images.value = mRawImagesList
            }
        }
    }

    companion object {
        const val BATCH_ITEMS_COUNT = 20
    }
}