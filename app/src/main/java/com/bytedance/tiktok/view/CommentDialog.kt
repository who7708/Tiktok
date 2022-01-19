package com.bytedance.tiktok.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytedance.tiktok.R
import com.bytedance.tiktok.adapter.CommentAdapter
import com.bytedance.tiktok.bean.CommentBean
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.databinding.DialogCommentBinding
import java.util.*

/**
 * create by libo
 * create on 2020-05-24
 * description 评论弹框
 */
class CommentDialog : BaseBottomSheetDialog() {

    private var commentAdapter: CommentAdapter? = null
    private val datas = ArrayList<CommentBean>()
    private val likeArray = intArrayOf(4919, 334, 121, 423, 221, 23)
    private val commentArray = arrayOf("我就说左脚踩右脚可以上天你们还不信！", "全是评论点赞，没人关注吗", "哈哈哈哈", "像谁，没看出来", "你这西安话真好听")

    private lateinit var _binding: DialogCommentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_comment, container)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        _binding.recyclerView.layoutManager = LinearLayoutManager(context)
        commentAdapter = CommentAdapter(context, datas)
        _binding.recyclerView.adapter = commentAdapter
        loadData()
    }

    private fun loadData() {
        for (i in DataCreate.userList.indices) {
            val commentBean = CommentBean()
            commentBean.userBean = DataCreate.userList[i]
            commentBean.content = commentArray[(Math.random() * commentArray.size).toInt()]
            commentBean.likeCount = likeArray[(Math.random() * likeArray.size).toInt()]
            datas.add(commentBean)
        }
        commentAdapter!!.notifyDataSetChanged()
    }

    override val height: Int
        get() = resources.displayMetrics.heightPixels - 600
}