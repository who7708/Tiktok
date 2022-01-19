package com.bytedance.tiktok.fragment

import android.os.CountDownTimer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bytedance.tiktok.R
import com.bytedance.tiktok.adapter.GridVideoAdapter
import com.bytedance.tiktok.base.BaseFragment
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.databinding.FragmentCurrentLocationBinding

/**
 * create by libo
 * create on 2020-05-19
 * description 附近的人fragment
 */
class CurrentLocationFragment : BaseFragment() {
    private var adapter: GridVideoAdapter? = null

    private lateinit var _binding: FragmentCurrentLocationBinding

    override fun setLayoutId(): Int {
        return R.layout.fragment_current_location
    }

    override fun init() {
        DataCreate().initData()
        _binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = GridVideoAdapter(activity, DataCreate.datas)
        _binding.recyclerView.adapter = adapter
        _binding.refreshLayout.setColorSchemeResources(R.color.color_link)
        _binding.refreshLayout.setOnRefreshListener {
            object : CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    _binding.refreshLayout.isRefreshing = false
                }
            }.start()
        }
    }
}