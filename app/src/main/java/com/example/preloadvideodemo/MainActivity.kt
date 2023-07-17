package com.example.preloadvideodemo

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Consumer
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.preloadvideodemo.databinding.ActivityMainBinding
import com.myq.preloadmanager.cache.PreloadManager


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    private var videoUrl="https://oss.xkweichen.com/release/videos/admin/617a40ecdc80648b8260297646ae9eb2.mp4"

    private val NEEDED_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        req()
        binding.btnPreload.setOnClickListener {
            PreloadManager.getInstance(this).addPreloadTask(videoUrl, 0)
        }

        binding.btnPlay.setOnClickListener {
            var videoPath = PreloadManager.getInstance(this).getPlayUrl(videoUrl)
            LogUtils.d("视频缓存地址：${videoPath}")
            binding.player.setUp(videoPath,true,"标题")
            binding.player.startPlayLogic()
        }
    }

     fun req() {
         PermissionUtils.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
             .callback(object : PermissionUtils.SimpleCallback {
                 override fun onGranted() {

                 }

                 override fun onDenied() {
                     ToastUtils.showLong("你拒绝了权限")
                 }
             }).request()
    }
}