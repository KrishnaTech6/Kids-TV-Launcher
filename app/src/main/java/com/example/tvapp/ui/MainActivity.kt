package com.example.tvapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tvapp.R
import com.example.tvapp.model.AppInfo
import com.example.tvapp.utils.LauncherUtils

class MainActivity : AppCompatActivity() {
    private lateinit var appGrid: GridView
    private lateinit var adapter: AppAdapter
    private lateinit var approvedApps: List<AppInfo>
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if app is default launcher
        if (!LauncherUtils.isDefaultLauncher(this)) {
            Log.d("APP_DEBUG", "App is not default launcher")
            LauncherUtils.showSetDefaultLauncherDialog(this)
        }

        setContentView(R.layout.activity_main)

        appGrid = findViewById(R.id.appGrid)
        approvedApps = getApprovedApps()

        if (approvedApps.isNotEmpty()) {
            adapter = AppAdapter(this, approvedApps)
            appGrid.adapter = adapter
        } else {
            Toast.makeText(this, "No Approved Apps Found!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime > 2000) {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
            backPressedTime = currentTime
        } else {
            // Show PIN protection screen for exit
            val exitPinIntent = Intent(this, PinProtectionActivity::class.java).apply {
                putExtra("EXIT_PROTECTION", true)
            }
            startActivity(exitPinIntent)
        }
    }

    private fun getApprovedApps(): List<AppInfo> {
        val appList: MutableList<AppInfo> = ArrayList()
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val resolvedApps = packageManager.queryIntentActivities(intent, 0)

        for (info in resolvedApps) {
            val packageName = info.activityInfo.packageName
            val appName = info.loadLabel(packageManager).toString()
            Log.d("APP_DEBUG", "Installed: $appName - $packageName")

            //if (isAppApproved(packageName)) {
                appList.add(AppInfo(appName, packageName))
            //}

        }
        return appList
    }

    private fun isAppApproved(packageName: String): Boolean {
        return packageName in listOf(
            "com.google.android.videos",
            "com.google.android.youtube.tv",
            "com.netflix.mediaclient",
            "com.disney.plus",
            "com.google.android.apps.messaging"
        )
    }
}