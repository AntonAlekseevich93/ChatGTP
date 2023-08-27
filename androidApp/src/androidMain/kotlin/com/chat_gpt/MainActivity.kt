package com.chat_gpt

import Application
import di.PlatformSDK
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import platform.PlatformConfiguration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlatformSDK.init(PlatformConfiguration(this))
        setContent {
            Application {}
        }
    }
}