# Contributing to KindSpark 🌟

Thank you for your interest in contributing to KindSpark! We're excited to have you join our mission of spreading kindness through technology. This guide will help you get started with contributing to the project.

## 🤝 Code of Conduct

By participating in this project, you agree to abide by our values of kindness, respect, and inclusivity. We're building an app about spreading positivity, so let's maintain that spirit in our development community too!

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio**: Arctic Fox (2020.3.1) or newer
- **JDK**: OpenJDK 11 or newer
- **Git**: For version control
- **Kotlin**: 1.9.0+ (included with Android Studio)

### Setting Up Your Development Environment

1. **Fork the Repository**
   
   First, you need to create your own copy of the KindSpark repository:
   - Go to the KindSpark repository on GitHub
   - Click the "Fork" button in the top-right corner
   - This creates a copy under your GitHub account
   
   Then clone YOUR fork (not the original repository):
   ```bash
   # Replace YOUR_USERNAME with your actual GitHub username
   git clone https://github.com/YOUR_USERNAME/KindSpark.git
   cd KindSpark
   ```

2. **Add Upstream Remote**
   
   Add a connection to the original repository so you can get updates:
   ```bash
   # This connects to the original KindSpark repository
   git remote add upstream https://github.com/ORIGINAL_OWNER/KindSpark.git
   
   # Verify your remotes are set up correctly
   git remote -v
   # Should show:
   # origin    https://github.com/YOUR_USERNAME/KindSpark.git (fetch)
   # origin    https://github.com/YOUR_USERNAME/KindSpark.git (push)
   # upstream  https://github.com/ORIGINAL_OWNER/KindSpark.git (fetch)
   # upstream  https://github.com/ORIGINAL_OWNER/KindSpark.git (push)
   ```

3. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to your cloned directory

4. **Build the Project**
   ```bash
   ./gradlew assembleDebug
   ```

5. **Run Tests**
   ```bash
   ./gradlew test
   ./gradlew connectedAndroidTest
   ```

## 📋 How to Contribute

### Types of Contributions We Welcome

- 🐛 **Bug Fixes**: Help us squash bugs and improve stability
- ✨ **New Features**: Add new kindness prompts, UI improvements, or functionality
- 📚 **Documentation**: Improve README, code comments, or create tutorials
- 🎨 **Design**: Enhance UI/UX, animations, or accessibility
- 🧪 **Testing**: Write unit tests, UI tests, or improve test coverage
- 🌍 **Localization**: Translate the app to new languages
- ♿ **Accessibility**: Make the app more inclusive for all users

### Finding Issues to Work On

1. **Good First Issue**: Look for issues labeled `good-first-issue`
2. **Help Wanted**: Check issues labeled `help-wanted`
3. **Feature Requests**: Browse issues labeled `enhancement`
4. **Bug Reports**: Fix issues labeled `bug`

## 🔄 Development Workflow

### 1. Create a Feature Branch

```bash
# Update your main branch
git checkout main
git pull upstream main

# Create a new feature branch
git checkout -b feature/your-feature-name
# or for bug fixes
git checkout -b fix/bug-description
```

### 2. Make Your Changes

- Follow our [coding standards](#-coding-standards)
- Write meaningful commit messages
- Add tests for new functionality
- Update documentation as needed

### 3. Test Your Changes

```bash
# Run unit tests
./gradlew test

# Run Android instrumentation tests
./gradlew connectedAndroidTest

# Run lint checks
./gradlew lint

# Build the app to ensure no compilation errors
./gradlew assembleDebug
```

### 4. Commit Your Changes

```bash
# Stage your changes
git add .

# Commit with a descriptive message
git commit -m "feat: add new environmental kindness prompts

- Added 5 new eco-friendly kindness activities
- Updated prompt selection algorithm
- Added tests for new prompt categories"
```

### 5. Push and Create Pull Request

```bash
# Push to your fork
git push origin feature/your-feature-name

# Create a Pull Request on GitHub
```

## 📝 Coding Standards

### Kotlin Style Guide

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Prefer `val` over `var` when possible
- Use trailing commas in multi-line declarations

### Android Specific Guidelines

- **Architecture**: Follow MVVM pattern with Repository
- **Compose**: Use Jetpack Compose best practices
- **Naming Conventions**:
  - Activities: `ExampleActivity`
  - Fragments: `ExampleFragment`
  - ViewModels: `ExampleViewModel`
  - Composables: `ExampleScreen`, `ExampleCard`

### Code Documentation

```kotlin
/**
 * Generates a daily kindness prompt based on user preferences and history.
 *
 * @param userId The unique identifier for the user
 * @param excludeCategories Categories to exclude from selection
 * @return A KindnessPrompt for today's activity, or null if none available
 */
suspend fun generateDailyPrompt(
    userId: String,
    excludeCategories: Set<PromptCategory> = emptySet()
): KindnessPrompt?
```

### UI/UX Guidelines

- **Accessibility**: Always include content descriptions
- **Material Design**: Follow Material Design 3 principles
- **Dark Mode**: Ensure all UI works in both light and dark themes
- **Responsive**: Test on different screen sizes

```kotlin
// Good: Accessible composable
@Composable
fun KindnessPromptCard(
    prompt: KindnessPrompt,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.semantics { 
            contentDescription = "Kindness prompt: ${prompt.description}"
        }
    ) {
        // Card content
    }
}
```

## 🧪 Testing Guidelines

### Unit Tests

- Write tests for ViewModels and Repository classes
- Use MockK for mocking dependencies
- Aim for high test coverage on business logic

```kotlin
@Test
fun `generateDailyPrompt returns prompt when available`() = runTest {
    // Arrange
    val mockRepository = mockk<KindnessRepository>()
    val expectedPrompt = KindnessPrompt(id = 1, description = "Help a neighbor")
    
    // Act & Assert
    coEvery { mockRepository.getRandomPrompt() } returns expectedPrompt
    
    val result = useCase.generateDailyPrompt("user123")
    assertEquals(expectedPrompt, result)
}
```

### UI Tests

- Write UI tests for critical user flows
- Use Compose Testing for UI component tests
- Test accessibility features

```kotlin
@Test
fun homeScreen_displaysPromptCorrectly() {
    composeTestRule.setContent {
        HomeScreen(
            uiState = HomeUiState(
                todaysPrompt = samplePrompt,
                currentStreak = 5
            )
        )
    }
    
    composeTestRule
        .onNodeWithText("Help a neighbor")
        .assertIsDisplayed()
}
```

## 🎯 Adding New Kindness Prompts

We're always looking for fresh, meaningful kindness activities! Here's how to add them:

### Prompt Categories

- **Social**: Connecting with friends, family, and community
- **Environmental**: Eco-friendly and sustainability actions
- **Workplace**: Professional kindness and support
- **Digital**: Online positivity and digital wellness
- **Self-Care**: Personal kindness and mental health
- **Community**: Local community involvement and service

### Writing Great Prompts

1. **Be Specific**: "Text a friend you haven't spoken to in a while" vs "Be nice to someone"
2. **Make it Actionable**: Clear, simple actions anyone can do
3. **Consider Accessibility**: Ensure prompts work for different abilities and situations
4. **Keep it Positive**: Focus on what TO do, not what NOT to do

### Adding Prompts to Database

```kotlin
// Add to KindnessDatabase.kt prepopulation
val newPrompts = listOf(
    KindnessPrompt(
        id = 21,
        description = "Leave a positive review for a local business you love",
        category = PromptCategory.COMMUNITY,
        difficulty = PromptDifficulty.EASY,
        estimatedTimeMinutes = 5
    )
)
```

## 🌍 Localization

Help make KindSpark accessible to more people by adding translations:

1. **Add String Resources**: Create `values-{language}/strings.xml`
2. **Translate Prompts**: Adapt kindness prompts for cultural contexts
3. **Test Layout**: Ensure UI works with longer/shorter text
4. **Cultural Sensitivity**: Adapt prompts to be culturally appropriate

## 📋 Pull Request Guidelines

### Before Submitting

- [ ] Code follows project style guidelines
- [ ] Self-review of code completed
- [ ] Tests added for new functionality
- [ ] All tests pass locally
- [ ] Documentation updated if needed
- [ ] No merge conflicts with main branch

### PR Description Template

```markdown
## Description
Brief description of changes made.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Performance improvement
- [ ] Code refactoring

## Testing
- [ ] Unit tests added/updated
- [ ] UI tests added/updated
- [ ] Manual testing completed

## Screenshots (if UI changes)
[Add screenshots or screen recordings]

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Tests pass
- [ ] Documentation updated
```

## 🐛 Reporting Bugs

### Before Reporting

1. **Search Existing Issues**: Check if the bug is already reported
2. **Try Latest Version**: Ensure you're using the most recent release
3. **Reproduce Consistently**: Can you make the bug happen reliably?

### Bug Report Template

```markdown
**Bug Description**
A clear description of what the bug is.

**Steps to Reproduce**
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

**Expected Behavior**
What you expected to happen.

**Screenshots**
If applicable, add screenshots.

**Device Information:**
- Device: [e.g. Pixel 6]
- OS Version: [e.g. Android 13]
- App Version: [e.g. 1.2.0]

**Additional Context**
Any other context about the problem.
```

## 💡 Suggesting Features

We love hearing your ideas! When suggesting features:

1. **Check Existing Issues**: Someone might have already suggested it
2. **Describe the Problem**: What need does this feature address?
3. **Propose a Solution**: How should it work?
4. **Consider Alternatives**: Are there other ways to solve this?


## 📞 Getting Help

- **GitHub Issues**: For bugs and feature requests
- **GitHub Discussions**: For questions and general discussion
- **Code Review**: Maintainers will provide feedback on PRs

## 📄 License

By contributing to KindSpark, you agree that your contributions will be licensed under the same license as the project.

---

Thank you for helping make the world a kinder place, one commit at a time! 🌟

*"No act of kindness, no matter how small, is ever wasted." - Aesop*
