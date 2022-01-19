package com.bytedance.tiktok.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytedance.tiktok.R
import com.bytedance.tiktok.adapter.PrivateLetterAdapter
import com.bytedance.tiktok.adapter.ShareAdapter
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.bean.ShareBean
import com.bytedance.tiktok.databinding.DialogShareBinding
import java.util.*

/**
 * create by libo
 * create on 2020-05-25
 * description 分享弹框
 */
open class ShareDialog : BaseBottomSheetDialog() {

    private var privateLetterAdapter: PrivateLetterAdapter? = null
    private var shareAdapter: ShareAdapter? = null
    private val shareBeans = ArrayList<ShareBean>()

    private lateinit var _binding: DialogShareBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_share, container)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        _binding.rvPrivateLetter.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        privateLetterAdapter = PrivateLetterAdapter(context, DataCreate.userList)
        _binding.rvPrivateLetter.adapter = privateLetterAdapter
        _binding.rvShare.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        shareAdapter = ShareAdapter(context, shareBeans)
        _binding.rvShare.adapter = shareAdapter
        setShareDatas()
    }

    private fun setShareDatas() {
        shareBeans.add(ShareBean(R.string.icon_friends, "朋友圈", R.color.color_wechat_iconbg))
        shareBeans.add(ShareBean(R.string.icon_wechat, "微信", R.color.color_wechat_iconbg))
        shareBeans.add(ShareBean(R.string.icon_qq, "QQ", R.color.color_qq_iconbg))
        shareBeans.add(ShareBean(R.string.icon_qq_space, "QQ空间", R.color.color_qqzone_iconbg))
        shareBeans.add(ShareBean(R.string.icon_weibo, "微博", R.color.color_weibo_iconbg))
        shareBeans.add(ShareBean(R.string.icon_comment, "私信好友", R.color.color_FF0041))
        shareAdapter!!.notifyDataSetChanged()
    }

    override val height: Int
        get() = dp2px(context!!, 355f)
}