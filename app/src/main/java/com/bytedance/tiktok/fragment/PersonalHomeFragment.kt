package com.bytedance.tiktok.fragment

import android.content.Intent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.bytedance.tiktok.R
import com.bytedance.tiktok.activity.FocusActivity
import com.bytedance.tiktok.activity.ShowImageActivity
import com.bytedance.tiktok.base.BaseFragment
import com.bytedance.tiktok.base.CommPagerAdapter
import com.bytedance.tiktok.bean.CurUserBean
import com.bytedance.tiktok.bean.MainPageChangeEvent
import com.bytedance.tiktok.bean.VideoBean.UserBean
import com.bytedance.tiktok.databinding.FragmentPersonalHomeBinding
import com.bytedance.tiktok.databinding.PersonalHomeHeaderBinding
import com.bytedance.tiktok.utils.NumUtils
import com.bytedance.tiktok.utils.RxBus
import com.google.android.material.appbar.AppBarLayout
import rx.Subscription
import rx.functions.Action1
import java.util.*

/**
 * create by libo
 * create on 2020-05-19
 * description 个人主页fragment
 */
class PersonalHomeFragment : BaseFragment(), View.OnClickListener {
    private val fragments = ArrayList<Fragment>()
    private var pagerAdapter: CommPagerAdapter? = null
    private var curUserBean: UserBean? = null
    private var subscription: Subscription? = null

    private lateinit var _bindingHome: FragmentPersonalHomeBinding
    private lateinit var _bindingHeader: PersonalHomeHeaderBinding

    override fun setLayoutId(): Int {
        return R.layout.fragment_personal_home
    }

    override fun init() {
        //解决toolbar左边距问题
        _bindingHome.toolbar.setContentInsetsAbsolute(0, 0)
        setappbarlayoutPercent()
        _bindingHome.ivReturn.setOnClickListener(this)
        _bindingHome.ivBg.setOnClickListener(this)
        _bindingHeader.ivHead.setOnClickListener(this)
        _bindingHeader.llFocus.setOnClickListener(this)
        _bindingHeader.llFans.setOnClickListener(this)
        setUserInfo()
    }

    /**
     * 过渡动画跳转页面
     *
     * @param view
     */
    fun transitionAnim(view: View?, res: Int) {
        val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, view!!, getString(R.string.trans))
        val intent = Intent(activity, ShowImageActivity::class.java)
        intent.putExtra("res", res)
        ActivityCompat.startActivity(activity!!, intent, compat.toBundle())
    }

    fun setUserInfo() {
        subscription =
            RxBus.getDefault().toObservable(CurUserBean::class.java).subscribe(Action1 { curUserBean: CurUserBean ->
                coordinatorLayoutBackTop()
                this.curUserBean = curUserBean.userBean
                _bindingHome.ivBg.setImageResource(curUserBean.userBean.head)
                _bindingHeader.ivHead.setImageResource(curUserBean.userBean.head)
                _bindingHeader.tvNickname.text = curUserBean.userBean.nickName
                _bindingHeader.tvSign.text = curUserBean.userBean.sign
                _bindingHome.tvTitle.text = curUserBean.userBean.nickName
                val subCount = NumUtils.numberFilter(curUserBean.userBean.subCount)
                val focusCount = NumUtils.numberFilter(curUserBean.userBean.focusCount)
                val fansCount = NumUtils.numberFilter(curUserBean.userBean.fansCount)

                //获赞 关注 粉丝
                _bindingHeader.tvGetLikeCount.text = subCount
                _bindingHeader.tvFocusCount.text = focusCount
                _bindingHeader.tvFansCount.text = fansCount

                //关注状态
                if (curUserBean.userBean.isFocused) {
                    _bindingHeader.tvAddfocus.text = "已关注"
                    _bindingHeader.tvAddfocus.setBackgroundResource(R.drawable.shape_round_halfwhite)
                } else {
                    _bindingHeader.tvAddfocus.text = "关注"
                    _bindingHeader.tvAddfocus.setBackgroundResource(R.drawable.shape_round_red)
                }
                setTabLayout()
            } as Action1<CurUserBean>)
    }

    private fun setTabLayout() {
        val titles = arrayOf(
            "作品 " + curUserBean!!.workCount,
            "动态 " + curUserBean!!.dynamicCount,
            "喜欢 " + curUserBean!!.likeCount
        )
        fragments.clear()
        for (i in titles.indices) {
            fragments.add(WorkFragment())
            _bindingHome.tabLayout.addTab(_bindingHome.tabLayout.newTab().setText(titles[i]))
        }
        pagerAdapter = CommPagerAdapter(childFragmentManager, fragments, titles)
        _bindingHome.viewPager.adapter = pagerAdapter
        _bindingHome.tabLayout.setupWithViewPager(_bindingHome.viewPager)
    }

    /**
     * 根据滚动比例渐变view
     */
    private fun setappbarlayoutPercent() {
        _bindingHome.appbarlayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appbarlayout, verticalOffset ->
            val percent = Math.abs(verticalOffset * 1.0f) / appbarlayout.totalScrollRange //滑动比例
            if (percent > 0.8) {
                _bindingHome.tvTitle.visibility = View.VISIBLE
                _bindingHome.tvFocus.visibility = View.VISIBLE
                val alpha = 1 - (1 - percent) * 5 //渐变变换
                _bindingHome.tvTitle.alpha = alpha
                _bindingHome.tvFocus.alpha = alpha
            } else {
                _bindingHome.tvTitle.visibility = View.GONE
                _bindingHome.tvFocus.visibility = View.GONE
            }
        })
    }

    /**
     * 自动回顶部
     */
    private fun coordinatorLayoutBackTop() {
        val behavior = (_bindingHome.appbarlayout.layoutParams as CoordinatorLayout.LayoutParams).behavior
        if (behavior is AppBarLayout.Behavior) {
            val topAndBottomOffset = behavior.topAndBottomOffset
            if (topAndBottomOffset != 0) {
                behavior.topAndBottomOffset = 0
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivReturn -> RxBus.getDefault().post(MainPageChangeEvent(0))
            R.id.ivHead -> transitionAnim(_bindingHeader.ivHead, curUserBean!!.head)
            R.id.ivBg -> {
            }
            R.id.llFocus -> startActivity(Intent(activity, FocusActivity::class.java))
            R.id.llFans -> startActivity(Intent(activity, FocusActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (subscription != null) {
            subscription!!.unsubscribe()
        }
    }
}