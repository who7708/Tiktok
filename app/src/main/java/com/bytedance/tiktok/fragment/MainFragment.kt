package com.bytedance.tiktok.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.bytedance.tiktok.R
import com.bytedance.tiktok.base.BaseFragment
import com.bytedance.tiktok.base.CommPagerAdapter
import com.bytedance.tiktok.bean.PauseVideoEvent
import com.bytedance.tiktok.databinding.FragmentMainBinding
import com.bytedance.tiktok.utils.RxBus
import java.util.*

/**
 * create by libo
 * create on 2020-05-19
 * description 主页fragment
 */
class MainFragment : BaseFragment() {
    private var currentLocationFragment: CurrentLocationFragment? = null
    private var recommendFragment: RecommendFragment? = null

    private val fragments = ArrayList<Fragment>()
    private var pagerAdapter: CommPagerAdapter? = null

    private lateinit var _binding: FragmentMainBinding

    override fun setLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun init() {
        setFragments()
        setMainMenu()
    }

    private fun setFragments() {
        currentLocationFragment = CurrentLocationFragment()
        recommendFragment = RecommendFragment()
        fragments.add(currentLocationFragment!!)
        fragments.add(recommendFragment!!)
        _binding.tabTitle.addTab(_binding.tabTitle.newTab().setText("海淀"))
        _binding.tabTitle.addTab(_binding.tabTitle.newTab().setText("推荐"))
        pagerAdapter = CommPagerAdapter(childFragmentManager, fragments, arrayOf("海淀", "推荐"))
        _binding.viewPager.adapter = pagerAdapter
        _binding.tabTitle.setupWithViewPager(_binding.viewPager)
        _binding.tabTitle.getTabAt(1)!!.select()
        _binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                curPage = position
                if (position == 1) {
                    //继续播放
                    RxBus.getDefault().post(PauseVideoEvent(true))
                } else {
                    //切换到其他页面，需要暂停视频
                    RxBus.getDefault().post(PauseVideoEvent(false))
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setMainMenu() {
        _binding.tabMainMenu.addTab(_binding.tabMainMenu.newTab().setText("首页"))
        _binding.tabMainMenu.addTab(_binding.tabMainMenu.newTab().setText("好友"))
        _binding.tabMainMenu.addTab(_binding.tabMainMenu.newTab().setText(""))
        _binding.tabMainMenu.addTab(_binding.tabMainMenu.newTab().setText("消息"))
        _binding.tabMainMenu.addTab(_binding.tabMainMenu.newTab().setText("我"))
    }

    companion object {
        /** 默认显示第一页推荐页  */
        var curPage = 1
    }
}