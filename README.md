# 📺 Kids TV Launcher App

**Developed by:** *Krishna Rana*

---

## 🧸 Overview

The **Kids TV Launcher App** is a secure and kid-friendly Android launcher tailored to provide a safe and enjoyable experience for children. It offers a curated interface where only approved video applications are accessible, ensuring children don’t navigate outside designated apps. Designed with simplicity, this launcher is compatible with Android TV and supports D-Pad navigation.

Tech Stack: `Kotlin , XML for UI ` 

---

<p align="center">
  <img src="https://github.com/user-attachments/assets/cb646c18-de42-4f51-a571-85d207339e13" alt="Screenshot 1" width="30%"/>
  <img src="https://github.com/user-attachments/assets/42478ba5-1787-4500-95bc-a69bea20214a" alt="Screenshot 2" width="30%"/>
  <img src="https://github.com/user-attachments/assets/09d9796a-4301-4a22-bcda-b1bbdd9566b9" alt="Screenshot 3" width="30%"/>
</p>
> Apps shown for demo purpose only 

## ✨ Features

- 🔒 **PIN Protection**  
  Secure the launcher with a PIN so only guardians can exit or make changes.

- 🎨 **Kid-Friendly UI**  
  Clean, vibrant grid layout optimized for young users and easy D-Pad navigation.

- ✅ **Restricted App Access**  
  Only pre-approved applications like **YouTube Kids** and **Disney Kids** are accessible.

- 🔧 **Easily Customizable**  
  Developers can easily modify the list of approved applications via code.

- 🖼️ **Custom Icons & Layouts**  
  Supports UI customization through layout files and app icons.

- 📺 **Remote & D-Pad Support**  
  Full compatibility with Android TV remotes and D-Pad-based input.

---

## 🚀 How to Use

1. **Launch the App**  
   Open the **Kids TV Launcher App** from your app drawer or home screen.

2. **Select an App**  
   Tap or navigate with your TV remote to launch one of the approved apps.

3. **Exit Launcher**  
   Press the **Back** button and enter the correct PIN to exit the launcher and return to the device's home screen.

> 🔐 **Default PIN:** `1234`

---

## 🛠️ Development

### 📋 Prerequisites

- Android Studio (Electric Eel or newer)
- Android SDK (API 21 or higher)
- Basic knowledge of Kotlin and Android development

 Manifest
- ``` xml
    <manifest ...>

    <!-- Add necessary permissions -->
    <uses-permission android:name="android.permission.QUERY_ALL_PACKAGES" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    
    <!-- Declare that this is a TV app -->
    <uses-feature
        android:name="android.software.leanback"
        android:required="true" />
    <uses-feature
        android:name="android.hardware.touchscreen"
        android:required="false" />

    <application
        android:icon="@mipmap/ic_launcher"
        android:banner="@mipmap/ic_launcher"
        tools:targetApi="31"
        ..... >
        
        <activity
            android:name=".ui.MainActivity"
            ....>
            <!-- For TV Launcher -->
            <intent-filter android:priority="999">
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LEANBACK_LAUNCHER" />
            </intent-filter>

            <!-- For Default Home -->
            <intent-filter android:priority="999">
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.HOME" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>

        </activity>
        
        <activity 
            android:name=".ui.PinProtectionActivity"
            .../>
            
    </application>
    </manifest>
    ```

Getting approved apps from all apps in the TV
- ``` kotlin
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

            if (isAppApproved(packageName)) {
                appList.add(AppInfo(appName, packageName))
            }

        }
        return appList
    }
    // List of approved apps pkgs 
    private fun isAppApproved(packageName: String): Boolean {
        return packageName in listOf(
            "com.google.android.videos",
            "com.google.android.youtube.tv",
            "com.netflix.mediaclient",
            "com.disney.plus",
            "com.google.android.apps.messaging"
        )
    }
```
