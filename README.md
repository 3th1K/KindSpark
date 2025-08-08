# KindSpark 🌟

A daily kindness companion app that inspires users to perform acts of kindness and track their positive impact on the world.

## 📱 About

KindSpark is an Android application built with Jetpack Compose that encourages users to practice daily acts of kindness. The app provides daily kindness prompts, tracks completion streaks, and helps users build a habit of spreading positivity in their communities.

## ✨ Features

### 🎯 Daily Kindness Prompts
- **Personalized Daily Challenges**: Get a unique kindness prompt every day
- **Smart Selection**: Prompts adapt based on previously skipped activities
- **Diverse Categories**: Activities spanning social, family, community, workplace, environmental, and digital kindness

### 📊 Progress Tracking
- **Streak Counter**: Track consecutive days of completed kindness acts
- **Achievement System**: Celebrate milestones (3, 7, 14, 21, 30, 50, 100 day streaks)
- **Activity History**: View past completed kindness activities
- **Favorites**: Mark and revisit your favorite kindness experiences

### 🔔 Smart Notifications
- **Daily Reminders**: Customizable notification scheduling
- **Motivation Messages**: Encouraging prompts to maintain your kindness streak
- **Background Scheduling**: WorkManager ensures reliable notification delivery

### 🎨 Personalization
- **Multiple Themes**: Light, Dark, and custom color schemes
- **Calming Backgrounds**: Optional serene visual experiences
- **Customizable Settings**: Tailor the app to your preferences

### 📝 Journal Features
- **Personal Notes**: Add reflections to completed kindness activities
- **Emotional Tracking**: Record how acts of kindness made you feel
- **Memory Preservation**: Build a personal archive of positive moments

## 🏗️ Technical Architecture

### Built With
- **Kotlin**: Modern, expressive programming language
- **Jetpack Compose**: Declarative UI toolkit for native Android
- **Material Design 3**: Google's latest design system
- **MVVM Architecture**: Clean separation of concerns

### Key Libraries & Frameworks
- **Room Database**: Local data persistence with SQLite
- **Navigation Compose**: Type-safe navigation between screens
- **DataStore Preferences**: Modern preference storage
- **WorkManager**: Reliable background task scheduling
- **Lottie**: Smooth animations and micro-interactions

### Database Schema
- **KindnessPrompt**: Core kindness activity templates
- **KindnessCompletion**: User completion records with notes
- **UserProgress**: Streak tracking and achievement data
- **DailyPromptSelection**: Daily prompt selection history
- **SkippedPrompt**: Tracking of skipped activities for smart suggestions

## 📋 Requirements

### System Requirements
- **Android Version**: API 24+ (Android 7.0)
- **Target SDK**: Android 14 (API 35)
- **Storage**: ~50MB for app and user data
- **Permissions**: 
  - `POST_NOTIFICATIONS` - Daily reminder notifications
  - `WAKE_LOCK` - Reliable notification delivery
  - `RECEIVE_BOOT_COMPLETED` - Notification persistence after device restart

### Development Requirements
- **Android Studio**: Arctic Fox or newer
- **Kotlin**: 1.9.0+
- **Java**: JDK 11
- **Gradle**: 8.0+

## 🚀 Installation

### For Users
1. Download the APK from the [Releases](../../releases) page
2. Enable "Install from Unknown Sources" in Android settings
3. Install the APK file
4. Launch KindSpark and start your kindness journey!

### For Developers
```bash
# Clone the repository
git clone https://github.com/yourusername/kindspark.git

# Open in Android Studio
cd kindspark

# Build and run
./gradlew assembleDebug
```

## 🎯 How to Use

### Getting Started
1. **First Launch**: The app initializes with 20 diverse kindness prompts
2. **Daily Prompt**: Each day, receive a new kindness challenge
3. **Complete Activities**: Mark prompts as completed and add personal notes
4. **Build Streaks**: Maintain consecutive days of kindness for achievements
5. **Explore History**: Review past activities and revisit favorites

### Navigation
- **🏠 Home**: View today's kindness prompt and current streak
- **📚 History**: Browse completed activities and favorites
- **⚙️ Settings**: Customize notifications, themes, and preferences

### Pro Tips
- **Skip Wisely**: Skipped prompts won't appear again the same day
- **Add Notes**: Personal reflections enhance the experience
- **Set Reminders**: Daily notifications help maintain consistency
- **Explore Themes**: Customize the visual experience to your preference

## 🗂️ Project Structure

```
app/src/main/java/com/example/kindspark/
├── data/                          # Data layer
│   ├── KindnessDatabase.kt       # Room database configuration
│   ├── KindnessRepository.kt     # Data repository pattern
│   ├── KindnessPromptDao.kt      # Database access objects
│   └── preferences/              # DataStore preferences
├── ui/                           # Presentation layer
│   ├── home/                     # Home screen & ViewModel
│   ├── history/                  # History screen & ViewModel
│   ├── settings/                 # Settings screen & ViewModel
│   ├── theme/                    # App theming & design system
│   └── icons/                    # Custom icon definitions
├── notifications/                # Background notification system
├── MainActivity.kt               # Main application entry point
└── SplashActivity.kt            # App launch screen
```

## 🎨 Design Philosophy

### Kindness-First Approach
- **Gentle Encouragement**: No guilt or pressure, only positive motivation
- **Accessibility**: Simple, intuitive design for all users
- **Mindful Interactions**: Every UI element promotes positive feelings

### Visual Design
- **Calming Colors**: Soft, warm color palettes that inspire tranquility
- **Clean Typography**: Easy-to-read fonts with proper contrast
- **Smooth Animations**: Subtle transitions that feel natural and responsive

## 🔧 Configuration

### Notification Settings
- **Frequency**: Daily reminders at your preferred time
- **Custom Messages**: Personalized motivation text
- **Quiet Hours**: Automatic silence during specified periods

### Theme Customization
- **Light/Dark Mode**: System-based or manual selection
- **Color Schemes**: Multiple pre-defined palettes
- **Background Options**: Minimalist or calming nature themes

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details.

### Development Setup
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

### Code Style
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Add comments for complex business logic
- Write unit tests for new features

## 📊 Metrics & Analytics

### User Engagement
- Daily active users and retention rates
- Completion rates by kindness category
- Streak length distributions
- Most popular kindness activities

### Performance Monitoring
- App launch time optimization
- Database query performance
- Notification delivery reliability
- Crash reporting and resolution

## 🔒 Privacy & Data

### Data Collection
- **Local Storage Only**: All user data remains on device
- **No Analytics Tracking**: No personal information sent to servers
- **Transparent Permissions**: Clear explanation of required permissions

### Data Security
- **Encrypted Storage**: User notes and progress securely stored
- **No Cloud Sync**: Complete privacy with local-only data
- **User Control**: Easy data export and deletion options

## 🛣️ Roadmap

### Version 2.0 (Planned)
- [ ] **Social Features**: Share achievements with friends
- [ ] **Custom Prompts**: Create personalized kindness activities
- [ ] **Community Challenges**: Participate in global kindness events
- [ ] **Advanced Analytics**: Detailed impact tracking and insights

### Version 2.5 (Future)
- [ ] **Widget Support**: Home screen widgets for quick access
- [ ] **Voice Commands**: Accessibility improvements
- [ ] **Multi-language**: Internationalization support
- [ ] **Wearable Integration**: Smartwatch companion app

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Community Contributors**: Thank you to all developers who've contributed
- **Inspiration**: Built to promote mental health and community connection
- **Open Source Libraries**: Grateful for the amazing Android development ecosystem

## 📞 Support

### Getting Help
- **Issues**: Report bugs via [GitHub Issues](../../issues)
- **Discussions**: Join community conversations in [Discussions](../../discussions)
- **Email**: Contact us at support@kindspark.app

### Frequently Asked Questions

**Q: How are daily prompts selected?**
A: The app uses a smart algorithm that considers your completion history and ensures variety across different kindness categories.

**Q: What happens to my data if I uninstall the app?**
A: All data is stored locally and will be permanently deleted when the app is uninstalled. Consider exporting your history first.

**Q: Can I suggest new kindness activities?**
A: Yes! Please open an issue with the "enhancement" label to suggest new prompts.

---

*Made with ❤️ to spread kindness in the world*

**Version**: 1.0.0  
**Last Updated**: August 2025  
**Minimum Android Version**: 7.0 (API 24)  
**Package**: com.example.kindspark
