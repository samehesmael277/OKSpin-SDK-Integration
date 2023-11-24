package com.sameh.task.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.sameh.task.R
import com.sameh.task.data.Companies
import com.sameh.task.data.Company
import com.sameh.task.databinding.FragmentHomeBinding
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private val companiesAdapter: CompaniesAdapter by lazy {
        CompaniesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCompaniesData()
        setActions()
    }

    private fun setActions() {
        companiesAdapter.setOnItemClickListener { company ->
            navigateToCompanyFragment(company)
        }
    }

    private fun setCompaniesData() {
        val companies = ArrayList<Company>()

        val oKSpin = Company(
            1,
            "OKSpin",
            "Gamify your brand.",
            getString(R.string.okspin_overview),
            "https://okspin.tech/",
            R.drawable.okspin_logo,
            R.drawable.okspin_cover,
            Companies.OKSpin
        )
        companies.add(oKSpin)

        val roulax = Company(
            2,
            "Roulax",
            "Global efficient monetization and advertising platform.",
            getString(R.string.roulax_overview),
            "https://www.roulax.io/",
            R.drawable.roulax_logo,
            R.drawable.roulax_cover,
            Companies.Roulax
        )
        companies.add(roulax)

        companiesAdapter.submitList(companies)
        initViewPagerList()
    }

    private fun initViewPagerList() {
        binding.viewPager2.adapter = companiesAdapter
        initIndicators()
        subscribeToViewPager()
    }

    private fun subscribeToViewPager() {
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                binding.indicator.onPageSelected(position)

            }

            override fun onPageScrollStateChanged(state: Int) {
                /*empty*/
            }
        })
    }

    private fun initIndicators() {
        binding.indicator.apply {
            setSliderColor(
                R.color.greyColor,
                R.color.black
            )
            setSliderWidth(40F)
            setSliderHeight(10f)
            setCheckedColor(R.color.black)
            setNormalColor(R.color.greyColor)
            setSlideMode(IndicatorSlideMode.SMOOTH)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setPageSize(binding.viewPager2.adapter!!.itemCount)
            notifyDataChanged()
        }
    }

    private fun navigateToCompanyFragment(company: Company) {
        val id = findNavController().currentDestination?.id

        if (id == R.id.homeFragment) {
            val action = HomeFragmentDirections.actionHomeFragmentToCompanyFragment(company)
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}