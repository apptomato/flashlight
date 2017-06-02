package com.johndoe.flashlight

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_flashlight.*


class FlashlightActivity : AppCompatActivity() {

    private var isOn = false
    private var camera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashlight)
        getPermit()
        btn_switch.setOnClickListener {
            if (isOn) {
                turnOffFlash()
            } else {
                turnOnFlash()
            }

        }
    }

    private fun getPermit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 101)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size < 1 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            finish()
        }
    }


    fun getCameraInstance(): Camera? {
        if (camera == null) {
            try {
                camera = Camera.open()
            } catch (e: Exception) {

            }
        }
        return camera
    }



    private fun turnOnFlash() {
        val params = getCameraInstance()?.parameters
        params?.flashMode = Camera.Parameters.FLASH_MODE_TORCH
        getCameraInstance()?.parameters = params
        getCameraInstance()?.startPreview()
        isOn = true
        btn_switch.text = "TURN OFF"
    }

    private fun turnOffFlash() {
        val params = getCameraInstance()?.parameters
        params?.flashMode = Camera.Parameters.FLASH_MODE_OFF
        getCameraInstance()?.parameters = params
        getCameraInstance()?.stopPreview()
        getCameraInstance()?.release()
        camera = null
        isOn = false
        btn_switch.text = "TURN ON"
    }


}
