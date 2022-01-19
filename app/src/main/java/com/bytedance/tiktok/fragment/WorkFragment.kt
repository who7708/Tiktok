package com.bytedance.tiktok.fragment

import androidx.recyclerview.widget.GridLayoutManager
import com.bytedance.tiktok.R
import com.bytedance.tiktok.adapter.WorkAdapter
import com.bytedance.tiktok.base.BaseFragment
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.databinding.FragmentWorkBinding

/**
 * create by libo
 * create on 2020-05-19
 * description 个人作品fragment
 */
class WorkFragment : BaseFragment() {
    private var workAdapter: WorkAdapter? = null

    private lateinit var _binding: FragmentWorkBinding

    override fun setLayoutId(): Int {
        return R.layout.fragment_work
    }

    override fun init() {
        _binding.recyclerview!!.layoutManager = GridLayoutManager(activity, 3)
        workAdapter = WorkAdapter(activity, DataCreate.datas)
        _binding.recyclerview!!.adapter = workAdapter
    }
}