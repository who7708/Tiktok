package com.bytedance.tiktok.activity

import android.view.View
import com.bytedance.tiktok.R
import com.bytedance.tiktok.base.BaseActivity
import com.bytedance.tiktok.databinding.ActivityShowImageBinding

class ShowImageActivity : BaseActivity() {

    private lateinit var _binding: ActivityShowImageBinding

    override fun setLayoutId(): Int {
        return R.layout.activity_show_image
    }

    override fun init() {
        _binding.ivHead.setOnClickListener { v: View? -> finish() }
        val headRes = intent.getIntExtra("res", 0)
        _binding.ivHead.setImageResource(headRes)
    }
}