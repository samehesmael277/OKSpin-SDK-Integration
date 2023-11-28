package com.sameh.task.ui.company

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gamify.space.Gamify
import com.gamify.space.GamifyError
import com.rad.RXError
import com.rad.RXSDK
import com.rad.out.RXAdInfo
import com.rad.out.RXSdkAd
import com.rad.out.banner.RXBannerAd
import com.rad.out.banner.RXBannerEventListener
import com.rad.out.nativeicon.RXNativeIconAd
import com.rad.out.ow.nativead.RXOWNativeAd
import com.rad.out.ow.nativead.RXOWNativeEventListener
import com.rad.out.ow.nativeicon.RXOWNativeIcon
import com.rad.out.ow.nativeicon.RXOWNativeIconEventListener
import com.rad.ow.api.RXWallApi
import com.rad.rcommonlib.glide.Glide
import com.rad.rcommonlib.utils.RXLogUtil
import com.sameh.task.R
import com.sameh.task.data.Companies
import com.sameh.task.databinding.FragmentCompanyBinding
import com.sameh.task.utils.Const.OKSPIN_APP_KEY
import com.sameh.task.utils.Const.OKSPIN_PLACEMENT
import com.sameh.task.utils.Const.ROULAX_APP_ID
import com.sameh.task.utils.Const.ROULAX_UNIT_ID

class CompanyFragment : Fragment() {

    private var _binding: FragmentCompanyBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<CompanyFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCompanyData()
        setActions()
        when (args.company.company) {
            Companies.OKSpin -> {
                setListenerToOkspinSDK()
                initOkspinSDK()
            }

            Companies.Roulax -> {
                initRoulaxSDK()
            }
        }
    }

    private fun setCompanyData() {
        binding.apply {
            tvCompanyName.text = args.company.name
            imgCompany.setImageResource(args.company.logo)
            imgCoverCompany.setImageResource(args.company.cover)
            tvCompanySubtitle.text = args.company.subTitle
            tvCompanyOverview.text = args.company.overview
        }
    }

    private fun setActions() {
        binding.apply {
            tvVisitCompanyWebsite.setOnClickListener {
                openLink(args.company.website)
            }
            btnBack.setOnClickListener {
                backToHomeFragment()
            }
        }
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
            binding.linearCompanyIcon.addView(iconView)
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

    private fun initRoulaxSDK() {
        RXSDK.init(ROULAX_APP_ID, object : RXSDK.RXSDKInitListener {
            override fun onSDKInitSuccess() {
                RXLogUtil.d("initRoulaxSDK onSDKInitSuccess")
                RXWallApi.setUserId(ROULAX_APP_ID)
                loadRXOWNativeAd()
            }

            override fun onSDKInitFailure(error: RXError) {
                RXLogUtil.d("onSDKInitFailure error: ${error.msg}")
            }
        })
    }

    private fun loadRXOWNativeAd() {
        RXSDK.createRXSdkAd()
            .loadOWNative(
                requireContext(),
                ROULAX_UNIT_ID,
                1,
                object : RXSdkAd.RXOWNativeAdListener {
                    override fun failure(adInfo: RXAdInfo, errorList: List<RXError>) {
                        RXLogUtil.d("createRXSdkAd failure: adInfo: $adInfo, error: $errorList")
                    }

                    override fun success(adInfo: RXAdInfo, nativeAdList: List<RXOWNativeAd>) {
                        RXLogUtil.d(nativeAdList)
                        nativeAdList[0].setRXOWNativeListener(object : RXOWNativeEventListener {

                            override fun onAdClick(pAdInfo: RXAdInfo) {
                                RXLogUtil.d("createRXSdkAd success onAdClick: pAdInfo: $pAdInfo")
                            }

                            override fun onAdClose(pAdInfo: RXAdInfo) {
                                RXLogUtil.d("createRXSdkAd success onAdClose: pAdInfo: $pAdInfo")
                            }

                            override fun onAdShow(pAdInfo: RXAdInfo) {
                                RXLogUtil.d("createRXSdkAd success onAdShow: pAdInfo: $pAdInfo")
                            }

                            override fun onRenderFail(pAdInfo: RXAdInfo, pError: RXError) {
                                RXLogUtil.d("createRXSdkAd success onRenderFail: pAdInfo: $pAdInfo, pError: $pError")
                            }

                            override fun onRenderSuccess(pView: View) {
                                RXLogUtil.d("createRXSdkAd success onRenderSuccess: adInfo: $adInfo")
                                binding.linearCompanyIcon.addView(pView)
                            }
                        })
                        nativeAdList[0].render()
                    }
                })
    }

    private fun String.toLogD(tag: String = "debuggingTAG") {
        Log.d(tag, this)
    }

    private fun String.toToast() {
        Toast.makeText(requireContext(), this, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(boolean: Boolean) {
        if (boolean)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

    private fun backToHomeFragment() {
        val id = findNavController().currentDestination?.id

        if (id == R.id.companyFragment) {
            findNavController().popBackStack()
        }
    }

    private fun openLink(url: String) {
        try {
            var mUrl = url
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                mUrl = "http://$url"
            }
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mUrl))
            startActivity(browserIntent)
        } catch (e: Exception) {
            e.message.toString().toLogD()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}