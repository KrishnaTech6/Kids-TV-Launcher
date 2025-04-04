# Kids TV Launcher App

**Company:** The Sales Studio

## Overview

This Android application, "Kids TV Launcher App," is designed to provide a safe and curated environment for children to access approved video content. It acts as a custom launcher, limiting access to only pre-selected applications (currently YouTube Kids and Disney Kids).

## Features

* **Curated Content:** Only approved applications (YouTube Kids and Disney Kids) are accessible.
* **Simplified Interface:** A grid-based layout for easy navigation.
* **PIN Protection:** A PIN-protected screen to open the app.
* **Customizable:** The list of approved applications can be modified in the code.

## Usage

1.  Launch the "Kids TV App" from your app drawer.
2.  Select an approved application (YouTube Kids or Disney Kids) from the grid.
3.  To exit the app, press the back button.
4.  Enter the correct PIN to access the device's default launcher.

### PIN = 1234

## Development

### Prerequisites

* Android Studio
* Android SDK

### Building the App

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Build the project.

### Modifying Approved Applications

1.  Open the `MainActivity.kt` file.
2.  Modify the `isAppApproved()` function to include or exclude applications based on their package names.

    ```kotlin
    private fun isAppApproved(packageName: String): Boolean {
        return packageName in listOf("com.youtube.kids","com.disney.kids", "your.package.name") 
    }
    ```

### Customization

* **Layout:** Modify the `activity_main.xml` and `item.xml` files to change the app's appearance.
* **PIN Protection:** Customize the `PinProtectionActivity.kt` and its associated layout.
* **Icons:** Replace the default app icon (`ic_launcher`) with your own.

## Contributing

Contributions are welcome! Please submit a pull request with your proposed changes.

## Contact

[krishna060702@gmail.com]

**The Sales Studio**
