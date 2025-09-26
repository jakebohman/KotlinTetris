# SwiftTetris

A (somewhat) faithful Android recreation of classic NES Tetris built in Kotlin. I also created an [iOS version of this app](https://github.com/jakebohman) using Swift.

<img width="324" height="715" alt="KotlinTetrisCapture" src="https://github.com/user-attachments/assets/4aba4568-9f88-4005-afc2-4fd5652504ee" />

## Features

The game recreates the authentic NES Tetris experience with accurate scoring, level progression, and gameplay mechanics. Players can control pieces using touch controls that mimic the original NES controller, complete with a realistic D-pad and A/B buttons.

**Authentic NES Mechanics:**
- Original scoring system (40/100/300/1200 points for 1/2/3/4 lines)
- Exact speed progression matching NES levels 00-29+
- Level increases every 10 lines cleared
- Classic piece rotation and movement physics

**Visual Design:**
- NES controller-inspired interface
- Retro color palette with cream and red accents
- Game area with proper proportions

## Controls

**NES-style**
- **D-pad:** Move pieces left/right/down
- **A Button:** Rotate clockwise
- **B Button:** Rotate counter-clockwise
- **Pause Button:** Pause/resume game

**Touchscreen**
- **Tap anywhere on game area:** Rotate piece clockwise
- **Swipe left/right:** Move piece horizontally
- **Swipe down:** Soft drop (accelerated fall)
- **Swipe up:** Rotate piece (alternative method)
- **Long press on game area:** Continuous soft drop


## Technical Details

Built with Kotlin 2.1.10-release-473 and Android Jetpack Compose, utilizing Gradle 8.13.

This app was tested with Android Studio's Medium Phone API 36.1 emulator (412dp x 935dp).
