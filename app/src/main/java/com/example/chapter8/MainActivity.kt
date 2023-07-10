package com.example.chapter8

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.chapter8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loadImageButton.setOnClickListener{
            checkPermission()
        }
    }

    private fun checkPermission(){
        //외부 storage에서 읽기 권한 가져오기
        when{
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                loadImage()
            }
            shouldShowRequestPermissionRationale (
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                showPermissionInfoDiaglog()
            }
            else -> {
                requestReadExternalStorage()
            }
        }

    }

    private fun showPermissionInfoDiaglog() {
        AlertDialog.Builder(this).apply {
            setMessage("이미지를 가져오기 위해서, 외부 저장소 읽기 권한이 필요합니다.")
            setNegativeButton("취소", null)
            setPositiveButton("동의") { _, _ ->
                requestReadExternalStorage()
            }
        }.show()
    }

    private fun loadImage(){
        Toast.makeText(this,"이미지를 가져올 예정",Toast.LENGTH_SHORT).show()
    }

    private fun requestReadExternalStorage(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_READ_EXTERNAL_STORAGE
        )
    }
    companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 100
    }
}