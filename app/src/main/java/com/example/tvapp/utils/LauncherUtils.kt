package com.example.tvapp.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

object LauncherUtils {
    fun isDefaultLauncher(context: Context): Boolean {
        // For Android TV, we need to check the actual default launcher
        val homeIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)
        val resolveInfo = context.packageManager.resolveActivity(homeIntent, PackageManager.MATCH_DEFAULT_ONLY)
        val currentDefault = resolveInfo?.activityInfo?.packageName
        
        Log.d("APP_DEBUG", "Current Default Launcher: $currentDefault")
        Log.d("APP_DEBUG", "Our Package: ${context.packageName}")
        
        // On Android TV, if we're the default launcher, we should be able to handle HOME intent
        val canHandleHome = context.packageManager.resolveActivity(
            homeIntent,
            PackageManager.MATCH_DEFAULT_ONLY
        )?.activityInfo?.packageName == context.packageName
        
        Log.d("APP_DEBUG", "Can Handle HOME: $canHandleHome")
        
        // If we can handle HOME intent, we are the default launcher
        return canHandleHome
    }

    fun showSetDefaultLauncherDialog(context: Context) {
        // Only show dialog if we're not the default launcher
        if (!isDefaultLauncher(context)) {
            AlertDialog.Builder(context)
                .setTitle("Set as Default Launcher")
                .setMessage("Would you like to set Kids TV Launcher as your default launcher?")
                .setPositiveButton("Yes") { _, _ ->
                    try {
                        // For Android TV, we need to use the TV-specific settings
                        val intent = Intent(Settings.ACTION_HOME_SETTINGS)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                        
                        // Show additional instructions
                        Toast.makeText(context, 
                            "Please select 'Kids TV Launcher' from the list", 
                            Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Log.e("APP_DEBUG", "Error opening settings: ${e.message}")
                        // Fallback to direct settings
                        val intent = Intent(Settings.ACTION_SETTINGS)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                }.setNegativeButton("Later") { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }
    }
} 