# Scroll Control v2

A **minimalist digital wellness Android app** that intelligently detects and redirects attention away from short-form video content on YouTube Shorts and Instagram Reels. Built with modern Android development practices using Kotlin and Jetpack Compose.

## 🎯 Vision

**"Less UI, More Impact"** - An invisible guardian that works silently in the background to help maintain digital wellness and focus.

## ✨ Key Features

### **Minimalist Design**
- **3 Screens Only**: Main toggle, Stats view, Essential settings
- **Material You**: Dynamic theming with system color adaptation
- **Clean Architecture**: MVVM with Hilt dependency injection

### **Smart Detection**
- **Multi-layer Detection**: Accessibility service + URL patterns + ML-based recognition
- **Self-improving**: Adapts to app UI changes automatically
- **Privacy-focused**: Completely offline operation

### **Gentle Interventions**
- **Graduated Response**: 4-stage intervention system
- **Contextual**: Different responses based on usage patterns
- **Non-intrusive**: Subtle guidance rather than harsh blocking

## 🏗 Architecture

### **Modern Android Stack**
- **Language**: Kotlin with coroutines
- **UI**: Jetpack Compose with Material Design 3
- **Architecture**: Clean Architecture + MVVM
- **DI**: Hilt for dependency injection
- **Database**: Room with encrypted storage
- **Target**: Android 7.0+ (API 24+)

### **Project Structure**
```
app/
├── src/main/java/com/vishal/scrollcontrol/
│   ├── ui/                 # Compose UI components
│   ├── service/            # Accessibility service
│   ├── data/               # Repository & data layer
│   ├── domain/             # Business logic
│   └── di/                 # Dependency injection
```

## 🚀 Development Status

**Current Phase**: Foundation Setup ✅
- [x] Project structure created
- [x] Modern Gradle configuration with version catalogs
- [x] Accessibility service framework
- [x] Basic UI theme and components setup
- [x] Hilt dependency injection configured

**Next Phase**: Core Minimalism
- [ ] 3-screen navigation system
- [ ] Main toggle with status indicator
- [ ] Enhanced detection algorithms
- [ ] Graduated intervention system

## 🛠 Build Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/Devilv85/Scroll-Control-v2.git
   cd Scroll-Control-v2
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the project folder

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Install on device**
   ```bash
   ./gradlew installDebug
   ```

## 📱 Requirements

### **Device Requirements**
- Android 7.0+ (API 24)
- ~5MB storage space
- Accessibility service support

### **Development Requirements**
- Android Studio Hedgehog or newer
- JDK 17
- Android SDK 35
- Gradle 8.9+

## 🔒 Privacy

- **Completely offline**: No internet permission required
- **Local storage only**: All data encrypted on device
- **No analytics**: Zero data collection or tracking
- **Open source**: Full transparency of all operations

## 🤝 Contributing

This is a focused, minimalist project. Contributions should align with the core philosophy of simplicity and effectiveness.

1. Fork the repository
2. Create a feature branch
3. Follow existing code style
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

Built with modern Android development tools:
- Jetpack Compose for declarative UI
- Hilt for dependency injection  
- Material Design 3 for theming
- Room for local data persistence
- Android Accessibility Services for content detection

---

**Version 2.0.0** - Complete rewrite focused on minimalism and effectiveness