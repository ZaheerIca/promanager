# Pro-manager: A Book Tracking App

Pro-manager is an Android application designed for personal project management.

## Technologies Used

- **Android Studio**: The official IDE for Android development.
- **Kotlin**: A modern language optimized for Android development.
- **Jetpack Compose**: A UI toolkit for building native Android interfaces.
- **Firebase**: A comprehensive mobile backend platform.
- **Firestore**: A scalable NoSQL cloud database for storing app data.
- **Material Design**: Ensures a consistent and visually appealing UI design.

## Key Features

- **Authentication**: Sign up and sign in via Firebase Authentication.
- **Book Reviews**: Write, read, and rate book reviews.
- **Reading Progress**: Track the books you're currently reading and monitor your progress.
- **Firestore Integration**: User and book review data is securely stored in Firestore.

## Prerequisites

Make sure you have the following before running the app:

- **Android Studio**: The latest stable version installed.
- **Java Development Kit (JDK)** 8 or newer.
- **Firebase Project**: Set up a Firebase project and integrate it with the app (detailed steps below).

## Setup Instructions

### Step 1: Clone the Repository

To get started, clone the repository to your local machine:

```bash
git clone https://github.com/ZaheerIca/promanager.git

### Step 1: Open the Project

Next, open Android Studio and choose **Open an existing project**. Navigate to the directory where the repository was cloned and open it.

### Step 2: Set Up Firebase

To configure Firebase for the app, follow these steps:

1. Open the **Firebase Console**.
2. Either create a new Firebase project or select an existing one.
3. Navigate to **Project Settings > General**.
4. Add the Android app’s package name in the **Your apps** section.
5. Download the `google-services.json` file and place it inside the `app/` folder of your project.

In Android Studio, ensure Firebase is set up:

- **Authentication**: Enable email/password authentication in the Firebase console.
- **Firestore**: Enable Firestore and configure read/write permissions.

### Step 3: Sync Gradle

After adding the `google-services.json` file, sync the project with Gradle:

1. Go to **File > Sync Project with Gradle Files** in Android Studio.

### Step 4: Run the App

To run the app:

1. Connect an Android device or start an emulator.
2. Click the **Run** button in Android Studio.
3. The app should build and launch on the connected device or emulator.
