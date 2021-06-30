package com.example.yitimages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.yitimages.databinding.ActivityMainBinding
import com.example.yitimages.preferences.IPreferenceHelper
import com.example.yitimages.preferences.PreferenceManager
import com.example.yitimages.utils.Utils.shareImage
import com.example.yitimages.viewmodels.ImagesViewModel

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    lateinit var mBinding: ActivityMainBinding
    lateinit var mViewModel: ImagesViewModel

    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)
        mViewModel.preferenceHelper = preferenceHelper
        mBinding.imagesViewModel = mViewModel
        setSupportActionBar(mBinding.toolbar)

        initListeners()
    }

    private fun initListeners() {
        mBinding.toolbarBackBtn.setOnClickListener {
            findNavController(R.id.fragment_host).navigateUp()
        }
        mBinding.toolbarSendBtn.setOnClickListener {
            mViewModel.selectedImage?.let {
                shareImage(this, it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        findNavController(R.id.fragment_host).addOnDestinationChangedListener(this)
    }

    override fun onStop() {
        super.onStop()
        findNavController(R.id.fragment_host).removeOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        controller.apply {
            when(graph.startDestination) {
                currentDestination?.id -> {
                    mBinding.toolbarBackBtn.visibility = View.GONE
                    mBinding.toolbarSendBtn.visibility = View.GONE
                }
                else -> {
                    mBinding.toolbarBackBtn.visibility = View.VISIBLE
                    mBinding.toolbarSendBtn.visibility = View.VISIBLE
                }
            }
        }
    }
}