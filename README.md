# Simple Clock App

A minimalist clock and timer application built with modern Android development practices.

## Features

### Clock
- Real-time digital clock display
- 12-hour format with AM/PM indicator
- Current date display
- Auto-updating every second

### Timer
- Customizable timer with hours, minutes, and seconds
- Interactive number picker for time selection
- Start, pause, and reset functionality
- Background timer service with notification
- Persistent notification with timer controls
- Continues running when app is in background

## Technical Details

### Architecture
- Clean Architecture with MVVM pattern
- Unidirectional data flow using Kotlin StateFlow
- Dependency injection using Koin
- Single activity with Jetpack Compose navigation

### Key Components

#### Presentation Layer
- Jetpack Compose for UI
- Material3 design components
- ViewModels for state management
- Responsive design that adapts to different screen sizes

#### Domain Layer
- Use cases for business logic
- Repository interfaces
- Domain models

#### Data Layer
- Repository implementations
- Time formatting utilities
- System service integrations

### Technologies Used
- Kotlin
- Jetpack Compose
- Coroutines & Flow
- Android Services
- BroadcastReceivers
- Material3 Design
- Koin Dependency Injection

## Project Structure

app/
├── src/
│ ├── main/
│ │ ├── java/com/example/simpleclock/
│ │ │ ├── data/
│ │ │ │ └── repository/ # Repository implementations
│ │ │ ├── domain/
│ │ │ │ ├── model/ # Domain models
│ │ │ │ ├── repository/ # Repository interfaces
│ │ │ │ ├── timer/ # Timer-related domain logic
│ │ │ │ └── usecase/ # Business logic use cases
│ │ │ ├── presentation/
│ │ │ │ ├── navigation/ # Navigation components
│ │ │ │ └── screen/
│ │ │ │ ├── clock/ # Clock feature
│ │ │ │ ├── main/ # Main container screen
│ │ │ │ └── timer/ # Timer feature
│ │ │ ├── service/ # Background services
│ │ │ └── ui/
│ │ │ └── theme/ # App theming
│ │ └── res/ # Resources
│ └── test/ # Unit tests
└── build.gradle # App level build config

## Design

### UI/UX Features
- Dark theme with gradient background
- Semi-transparent components
- Responsive typography
- Intuitive controls
- Consistent visual language

### Color Scheme
- Primary gradient: Dark blue (#2B2D42) to darker blue (#1A1B2E)
- Semi-transparent overlays: #3A3D50 with varying alpha
- White text with different opacity levels for hierarchy

## Future Improvements
- Light theme support
- Multiple timers
- Timer presets
- Alarm functionality
- Widget support
- Settings screen
- More customization options

## Contributing
Feel free to submit issues and enhancement requests.