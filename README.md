## Overview

Simple Android application that detects the user's current location and displays it on the screen. The app utilizes modern Android development tools such as Jetpack Compose for the UI, FusedLocationProviderClient for location services, and Dagger Hilt for dependency injection.

## Key Libraries

- **Jetpack Compose**: UI toolkit for building native Android interfaces with declarative code.
- **FusedLocationProviderClient**: Provides location services using Google Play Services.
- **Dagger Hilt**: Manages dependency injection and reduces boilerplate code.

## How It Works

The app detects the user’s location using the `FusedLocationProviderClient` from Google Play Services. The location service is injected into the relevant classes using Dagger Hilt, making it easy to manage dependencies and keep the codebase clean. The location data is then displayed in a simple Compose UI.

### Code Structure

- **LocationModule**: Provides `FusedLocationProviderClient` using Dagger Hilt.
- **MainActivity**: Contains the main logic to get and display the user's location.
- **Compose UI**: Displays the user's location (latitude, longitude) in real-time.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
