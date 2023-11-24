package com.sameh.task

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gamify.space.Gamify
import com.gamify.space.GamifyError
import com.sameh.task.Const.OKSPIN_APP_KEY
import com.sameh.task.Const.OKSPIN_PLACEMENT
import com.sameh.task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListenerToOkspinSDK()
        initOkspinSDK()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initOkspinSDK() {
        showProgress(true)
        Gamify.initSDK(OKSPIN_APP_KEY)
    }

    private fun showPlacementCreative(placement: String) {
        val iconView = Gamify.showIcon(placement)
        if (iconView != null) {
            if (iconView.parent != null) {
                val viewGroup = iconView.parent as ViewGroup
                viewGroup.removeView(iconView)
            }
            iconView.layoutParams = ViewGroup.LayoutParams(300, 300)
            binding.linearLayout.addView(iconView)
        }
    }

    private fun setListenerToOkspinSDK() {
        Gamify.setListener(object : Gamify.GamifyListener {
            override fun onInitSuccess() {
                // Invoked when SDK init success
                "onInitSuccess".toLogD()
                Gamify.loadIcon(OKSPIN_PLACEMENT)
            }

            override fun onInitFailed(error: GamifyError) {
                // Invoked when SDK init failed
                "onInitFailed error: ${error.msg}".toLogD()
                "onInitFailed error: ${error.msg}".toToast()
            }

            override fun onIconReady(placement: String) {
                // Placement load success
                "onIconReady: $placement".toLogD()
                showPlacementCreative(placement)
                showProgress(false)
            }

            override fun onIconLoadFailed(placement: String, error: GamifyError) {
                // Placement load failed
                "onIconLoadFailed placement: $placement".toLogD()
                "onIconLoadFailed error: ${error.msg}".toLogD()
            }

            override fun onIconShowFailed(placementId: String, error: GamifyError) {
                // Placement show failed
                "onIconShowFailed placementId: $placementId".toLogD()
                "onIconShowFailed error: ${error.msg}".toLogD()
                "onIconShowFailed error: ${error.msg}".toToast()
                showProgress(false)
            }

            override fun onIconClick(placement: String) {
                // Invoked when user clicks Placement
                "onIconClick: $placement".toLogD()
            }

            override fun onInteractiveOpen(placement: String) {
                // Invoked when GSpace - Interactive Ads page opened
                "onInteractiveOpen: $placement".toLogD()
            }

            override fun onInteractiveOpenFailed(placementId: String, error: GamifyError) {
                // Invoked when GSpace - Interactive Ads page open failed
                "onInteractiveOpenFailed placementId: $placementId".toLogD()
                "onInteractiveOpenFailed error: ${error.msg}".toLogD()
                "onInteractiveOpenFailed error: ${error.msg}".toToast()
            }

            override fun onInteractiveClose(placement: String) {
                // Invoked when GSpace - Interactive Ads page closed
                "onInteractiveClose placement: $placement".toLogD()
            }

            override fun onOfferWallOpen(placementId: String) {
                // Invoked when GSpace - Interactive Wall page opened
                "onOfferWallOpen placementId: $placementId".toLogD()
            }

            override fun onOfferWallOpenFailed(placementId: String, error: GamifyError) {
                // Invoked when GSpace - Interactive Wall page open failed
                "onOfferWallOpenFailed placementId: $placementId".toLogD()
                "onOfferWallOpenFailed error: ${error.msg}".toLogD()
                "onOfferWallOpenFailed error: ${error.msg}".toToast()
            }

            override fun onOfferWallClose(placementId: String) {
                // Invoked when GSpace - Interactive Wall page closed
                "onOfferWallClose placementId: $placementId".toLogD()
            }

            override fun onGSpaceOpen(placementId: String) {
                // Invoked when GSpace opened
                "onGSpaceOpen placementId: $placementId".toLogD()
            }

            override fun onGSpaceOpenFailed(placementId: String, error: GamifyError) {
                // Invoked when GSpace open failed
                "onGSpaceOpenFailed placementId: $placementId".toLogD()
                "onGSpaceOpenFailed error: ${error.msg}".toLogD()
                "onGSpaceOpenFailed error: ${error.msg}".toToast()
            }

            override fun onGSpaceClose(placementId: String) {
                // Invoked when GSpace closed
                "onGSpaceClose placementId: $placementId".toLogD()
            }

            override fun onUserInteraction(placementId: String, interaction: String) {
                // Handle user interaction
                "onUserInteraction placementId: $placementId".toLogD()
                "onUserInteraction interaction: $interaction".toLogD()
            }
        })
    }

    private fun String.toLogD(tag: String = "debuggingTAG") {
        Log.d(tag, this)
    }

    private fun String.toToast() {
        Toast.makeText(this@MainActivity, this, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(boolean: Boolean) {
        if (boolean)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }
}