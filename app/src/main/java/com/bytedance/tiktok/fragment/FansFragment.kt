package com.bytedance.tiktok.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.bytedance.tiktok.R
import com.bytedance.tiktok.adapter.FansAdapter
import com.bytedance.tiktok.base.BaseFragment
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.databinding.FragmentFansBinding

class FansFragment : BaseFragment() {
    private var fansAdapter: FansAdapter? = null

    private lateinit var _binding: FragmentFansBinding

    override fun setLayoutId(): Int {
        return R.layout.fragment_fans
    }

    override fun init() {
        _binding.recyclerview.layoutManager = LinearLayoutManager(context)
        fansAdapter = FansAdapter(context, DataCreate.userList)
        _binding.recyclerview.adapter = fansAdapter
    }
}