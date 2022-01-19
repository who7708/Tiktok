package com.bytedance.tiktok.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.RelativeLayout
import butterknife.ButterKnife
import com.bytedance.tiktok.R
import com.bytedance.tiktok.bean.VideoBean
import com.bytedance.tiktok.databinding.ViewControllerBinding
import com.bytedance.tiktok.utils.AutoLinkHerfManager
import com.bytedance.tiktok.utils.NumUtils
import com.bytedance.tiktok.utils.OnVideoControllerListener

/**
 * create by libo
 * create on 2020-05-20
 * description
 */
class ControllerView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs), View.OnClickListener {
    private var listener: OnVideoControllerListener? = null
    private var videoData: VideoBean? = null

    private lateinit var _binding: ViewControllerBinding

    private fun init() {
        val rootView = LayoutInflater.from(context).inflate(R.layout.view_controller, this)
        ButterKnife.bind(this, rootView)
        _binding.ivHead.setOnClickListener(this)
        _binding.ivComment.setOnClickListener(this)
        _binding.ivShare.setOnClickListener(this)
        _binding.rlLike.setOnClickListener(this)
        _binding.ivFocus.setOnClickListener(this)
        setRotateAnim()
    }

    fun setVideoData(videoData: VideoBean) {
        this.videoData = videoData
        _binding.ivHead.setImageResource(videoData.userBean!!.head)
        _binding.tvNickname.text = "@" + videoData.userBean!!.nickName
        AutoLinkHerfManager.setContent(videoData.content, _binding.autoLinkTextView)
        _binding.ivHeadAnim.setImageResource(videoData.userBean!!.head)
        _binding.tvLikecount.text = NumUtils.numberFilter(videoData.likeCount)
        _binding.tvCommentcount.text = NumUtils.numberFilter(videoData.commentCount)
        _binding.tvSharecount.text = NumUtils.numberFilter(videoData.shareCount)
        _binding.animationView.setAnimation("like.json")

        //点赞状态
        if (videoData.isLiked) {
            _binding.ivLike.setTextColor(resources.getColor(R.color.color_FF0041))
        } else {
            _binding.ivLike.setTextColor(resources.getColor(R.color.white))
        }

        //关注状态
        if (videoData.isFocused) {
            _binding.ivFocus.visibility = GONE
        } else {
            _binding.ivFocus.visibility = VISIBLE
        }
    }

    fun setListener(listener: OnVideoControllerListener?) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        if (listener == null) {
            return
        }
        when (v.id) {
            R.id.ivHead -> listener!!.onHeadClick()
            R.id.rlLike -> {
                listener!!.onLikeClick()
                like()
            }
            R.id.ivComment -> listener!!.onCommentClick()
            R.id.ivShare -> listener!!.onShareClick()
            R.id.ivFocus -> if (!videoData!!.isFocused) {
                videoData!!.isLiked = true
                _binding.ivFocus.visibility = GONE
            }
        }
    }

    /**
     * 点赞动作
     */
    fun like() {
        if (!videoData!!.isLiked) {
            //点赞
            _binding.animationView.visibility = VISIBLE
            _binding.animationView.playAnimation()
            _binding.ivLike.setTextColor(resources.getColor(R.color.color_FF0041))
        } else {
            //取消点赞
            _binding.animationView.visibility = INVISIBLE
            _binding.ivLike.setTextColor(resources.getColor(R.color.white))
        }
        videoData!!.isLiked = !videoData!!.isLiked
    }

    /**
     * 循环旋转动画
     */
    private fun setRotateAnim() {
        val rotateAnimation = RotateAnimation(
            0f, 359f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.duration = 8000
        rotateAnimation.interpolator = LinearInterpolator()
        _binding.rlRecord.startAnimation(rotateAnimation)
    }

    init {
        init()
    }
}