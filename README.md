# Simple Clock App

A minimalist clock, timer, and alarm application built with modern Android development practices.

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

### Alarm
- Set multiple alarms
- Repeat options (daily, weekdays, weekends, custom)
- Customizable alarm sound
- Vibration support
- Snooze functionality
- Background alarm service
- Persistent notification for active alarms
- Auto-start on device boot

## Technical Details

### Architecture
- Clean Architecture with MVVM pattern
- Unidirectional data flow using Kotlin StateFlow
- Dependency injection using Koin
- Single activity with Jetpack Compose navigation
- Room database for alarm storage

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
- Alarm scheduling logic

#### Data Layer
- Repository implementations
- Room database for alarms
- Time formatting utilities
- System service integrations
- Alarm state persistence

### Technologies Used
- Kotlin
- Jetpack Compose
- Coroutines & Flow
- Android Services
- BroadcastReceivers
- Room Database
- Material3 Design
- Koin Dependency Injection
- WorkManager for alarms

## Design

### UI/UX Features
- Dark theme with gradient background
- Semi-transparent components
- Responsive typography
- Intuitive controls
- Consistent visual language
- Smooth animations
- Interactive alarm management

### Color Scheme
- Primary gradient: Dark blue (#2B2D42) to darker blue (#1A1B2E)
- Semi-transparent overlays: #3A3D50 with varying alpha
- White text with different opacity levels for hierarchy
- Accent colors for alarm states

## Project Structure
