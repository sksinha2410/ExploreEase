# ExploreEase

An Android application for travel enthusiasts to explore destinations, share travel experiences, connect with guides, and plan trips with ease.

## Features

- 🗺️ **Destination Exploration**: Browse and search for travel destinations with map integration
- 📝 **Travel Blogs**: Share and read travel experiences and blog posts
- 🎒 **Trip Planning**: Create and manage your travel itineraries
- 👥 **Guide Connection**: Connect with local travel guides
- 💬 **AI Chatbot**: Get travel recommendations and assistance using Google's Generative AI
- 📍 **Location Services**: Real-time location tracking and place suggestions
- 🔐 **User Authentication**: Secure login and signup with Firebase Authentication
- 👤 **User Profiles**: Manage your personal travel profile

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose & XML Layouts
- **Architecture**: MVVM with Android Architecture Components
- **Database**: Firebase Realtime Database
- **Authentication**: Firebase Authentication
- **Storage**: Firebase Storage
- **Maps**: Google Maps Android API
- **Places**: Google Places API
- **AI**: Google Generative AI (Gemini)
- **Messaging**: Firebase Cloud Messaging
- **Image Loading**: Glide
- **Build System**: Gradle with Kotlin DSL

## Prerequisites

- Android Studio (latest version recommended)
- JDK 8 or higher
- Android SDK with minimum SDK 24 (Android 7.0 Nougat)
- Google account for Firebase setup

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/sksinha2410/ExploreEase.git
cd ExploreEase
```

### 2. Firebase Configuration

1. Create a new project in [Firebase Console](https://console.firebase.google.com/)
2. Add an Android app to your Firebase project
3. Download the `google-services.json` file
4. Place it in the `app/` directory
5. Enable the following Firebase services in your Firebase Console:
   - Authentication (Email/Password)
   - Realtime Database
   - Storage
   - Cloud Messaging

### 3. Google Maps API Setup

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable the following APIs:
   - Maps SDK for Android
   - Places API
4. Create API credentials (API Key)
5. Update the API key in `app/src/main/AndroidManifest.xml`:
   ```xml
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="YOUR_API_KEY_HERE" />
   ```
6. Also update in `app/src/main/res/values/strings.xml`:
   ```xml
   <string name="map_api">YOUR_API_KEY_HERE</string>
   ```

### 4. Build and Run

1. Open the project in Android Studio
2. Sync Gradle files
3. Connect an Android device or start an emulator
4. Click Run or use:
   ```bash
   ./gradlew assembleDebug
   ```

## Dependencies

Key dependencies include:

- **AndroidX Libraries**: Core KTX, AppCompat, Material Design, ConstraintLayout
- **Jetpack Compose**: UI toolkit with Material 3
- **Firebase**: Authentication, Database, Storage, Messaging
- **Google Services**: Maps, Places, Location
- **Google AI**: Generative AI SDK
- **Navigation Component**: Fragment navigation
- **Glide**: Image loading and caching
- **CircleImageView**: Circular image views
- **Coil**: Image loading for Compose

For complete dependencies, see `app/build.gradle.kts`

## Project Structure

```
app/src/main/java/com/sksinha2410/exploreease/
├── Activities/          # Activity classes
│   ├── MainActivity.kt
│   ├── Login_Activity.kt
│   ├── SignUp_Activity.kt
│   ├── AddTripActivity.kt
│   ├── AddBlogActivity.kt
│   ├── BeGuideActivity.kt
│   └── ...
├── Fragment/           # Fragment classes
│   ├── Home_Fragment.kt
│   ├── ChatBot_Fragment.kt
│   ├── Profile_Fragment.kt
│   ├── GuideFragment.kt
│   └── BagPackers_Fragment.kt
├── Adapter/            # RecyclerView adapters
├── DataClass/          # Data models
├── Chatbot/            # AI chatbot implementation
└── FirebaseService.kt  # Firebase messaging service
```

## Permissions

The app requires the following permissions:

- `ACCESS_FINE_LOCATION` - For precise location tracking
- `ACCESS_COARSE_LOCATION` - For approximate location
- `INTERNET` - For network operations
- `ACCESS_NETWORK_STATE` - For checking network connectivity

## Security Note

⚠️ **Important**: The current repository contains API keys in the manifest and configuration files. For production use:

1. Remove hardcoded API keys from version control
2. Use environment variables or secure key management
3. Add sensitive files to `.gitignore`
4. Implement API key restrictions in Google Cloud Console

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is available under the MIT License.

## Author

[sksinha2410](https://github.com/sksinha2410)

## Support

For issues and questions, please open an issue in the GitHub repository.
