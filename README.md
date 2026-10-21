# Velocity Player

<p align="center">
  <img src="https://raw.githubusercontent.com/tylonmademe-1/velocity-player/main/logo.png" width="220" alt="Velocity Player Logo" />
</p>

<p align="center">
  <strong>A powerful, open-source Android media player built on MPV.</strong>
  <br />
  Engineered for exceptional playback, extensive customization, and a refined Liquid Glass interface.
</p>

<p align="center">
  <a href="https://github.com/tylonmademe-1/velocity-player/releases">
    <img src="https://img.shields.io/github/v/release/tylonmademe-1/velocity-player?style=flat-square&label=Latest%20Release" alt="Latest Release" />
  </a>
  <a href="https://github.com/tylonmademe-1/velocity-player/blob/main/LICENSE">
    <img src="https://img.shields.io/github/license/tylonmademe-1/velocity-player?style=flat-square" alt="License" />
  </a>
  <a href="https://github.com/tylonmademe-1/velocity-player/issues">
    <img src="https://img.shields.io/github/issues/tylonmademe-1/velocity-player?style=flat-square&label=Issues" alt="GitHub Issues" />
  </a>
  <a href="https://github.com/tylonmademe-1/velocity-player">
    <img src="https://img.shields.io/github/stars/tylonmademe-1/velocity-player?style=flat-square&label=Stars" alt="GitHub Stars" />
  </a>
</p>

<p align="center">
  <a href="#features">Features</a> ·
  <a href="#building-from-source">Build from Source</a> ·
  <a href="#build-variants">Build Variants</a> ·
  <a href="#credits">Credits</a>
</p>

---

## Overview

**Velocity Player** is an open-source Android video player powered by the MPV media engine. It combines advanced playback capabilities with a highly configurable interface, comprehensive subtitle support, network streaming, and an extensive set of media management tools.

Designed for users who want more control over their media experience, Velocity Player brings together performance-oriented playback, powerful customization, and a modern **Liquid Glass** visual design.

### Highlights

| Capability | Description |
|---|---|
| Playback Engine | MPV-powered playback with hardware and software decoding |
| Video Enhancement | Anime4K upscaling, video filters, and debanding |
| Subtitle Support | Online subtitle search, styling, and synchronization |
| Network Streaming | SMB, FTP, WebDAV, and M3U/M3U8 playlists |
| Customization | 50+ themes, configurable controls, and playback gestures |
| Modern Interface | Liquid Glass styling, dynamic colors, and AMOLED mode |
| Advanced Controls | Lua scripts, configuration editing, and playback statistics |

---

## Features

### 01 · Media Playback

- **MPV engine** for broad video and audio format compatibility.
- Hardware and software decoding with `gpu-next` and Vulkan rendering.
- **Anime4K upscaling** with A, B, C, A+, B+, and C+ presets and Fast, Balanced, and High quality modes.
- Debanding, YUV420P pixel format, and precise seeking.
- Frame-by-frame navigation with snapshot saving.
- Adjustable playback speed with pitch correction.
- Volume normalization and configurable volume boost limits.
- Background playback with media notification controls.
- Picture-in-Picture with automatic PiP support.
- Sleep timer, autoplay next video, and playback position restoration.
- Recently played history and playback progress tracking.

### 02 · Subtitles

- Automatic loading of external subtitles by filename.
- Online subtitle search and downloads through SubDL and Wyzie.
- Customizable font, size, color, outline, shadow, position, and scale.
- ASS/SSA subtitle rendering overrides.
- Subtitle delay adjustment and custom font directory support.

### 03 · Audio

- Multiple audio track selection with preferred language settings.
- Audio channel modes: Auto, Mono, Stereo, and Reversed Stereo.
- Adjustable audio delay.
- External audio track loading.

### 04 · Network Streaming

- Connect to remote media through **SMB, FTP, and WebDAV**.
- Browse network directories with breadcrumb navigation.
- Play M3U and M3U8 playlists from local files or URLs.
- Experimental network video thumbnail support.

### 05 · File Browser

- Browse local files, directories, and video collections.
- Scan videos and cache metadata and thumbnails.
- Sort, rename, delete, copy, and paste files.
- Multi-selection for efficient file management.
- Folder blacklisting and video or directory search.

### 06 · Playlist Management

- Create, rename, and delete playlists.
- Organize and manage video collections.
- Import M3U/M3U8 playlists from local files or URLs.
- Refresh supported remote playlists.

### 07 · Playback Gestures

- Double-tap left, center, or right zones for seeking, playback, or custom actions.
- Horizontal swipes with adjustable seeking sensitivity.
- Vertical gestures for brightness and volume.
- Pinch-to-zoom support.
- Hold gestures for multi-speed playback.
- Configurable volume and brightness slider behavior.

### 08 · Interface & Appearance

- **Liquid Glass** interface with adjustable glass opacity and corner radius.
- Support for 120 Hz displays and high-refresh-rate UI rendering.
- **50+ built-in themes**, including Dracula, Tokyo Night, Catppuccin, Nord, Mocha, and Rosé Pine.
- Material You dynamic colors on Android 12 and newer.
- AMOLED black mode and custom color selection.
- Navigation bar opacity customization.
- Dark, light, and system appearance modes.
- New-item indicators for recently added, unplayed videos.
- Configurable watched-progress threshold.

### 09 · Player Controls

- Customizable button layouts for landscape, portrait, and multiple screen positions.
- Aspect ratio modes: Fit, Crop, Stretch, and Zoom.
- Video adjustments for brightness, contrast, gamma, saturation, hue, and sharpness.
- Playback statistics overlay with CPU/GPU information, frame timing, cache, keybindings, and track details.
- Chapter navigation and decoder selection.
- Ambient mode.
- Lua script support with custom player buttons.

### 10 · Advanced Tools

- Edit `mpv.conf` and `input.conf` directly in the app.
- Export and import settings in XML format.
- Verbose logging and diagnostic log export.
- In-app update checker.
- Clear playback history and thumbnail, font, and configuration caches.
- Crash screen with a database reset option.

---

## Building from Source

### Prerequisites

Ensure the following tools are available before building:

- [Android Studio](https://developer.android.com/studio) — latest stable version.
- **JDK 17** — the bundled Android Studio JDK is recommended.
- Android SDK with **API 36**.
- Git for cloning the repository.

### Installation

**1. Clone the repository**

```bash
git clone https://github.com/tylonmademe-1/velocity-player.git
cd velocity-player
```

**2. Open the project**

Launch Android Studio and select **File → Open**, then choose the cloned repository directory.

**3. Sync Gradle**

Allow Gradle synchronization to finish. The repository includes the MPV Android library at:

```text
app/libs/mpv-android-lib-v0.0.1.aar
```

**4. Build and run**

Connect an Android device or configure an emulator, select the appropriate build variant, and click **Run** in Android Studio.

> **Note:** Build requirements and included native libraries may vary between revisions. Consult the project configuration if you encounter build or SDK compatibility issues.

---

## Build Variants

Velocity Player provides multiple build configurations for development and distribution.

| Variant | Purpose |
|---|---|
| `standardDebug` | Development build with full storage access |
| `standardRelease` | Release build with the update checker |
| `playstoreRelease` | Google Play distribution with scoped storage |
| `fdroidRelease` | F-Droid distribution targeting arm64 |

Select the desired variant through Android Studio's **Build Variants** panel.

---

## Project Structure

The project is organized around the Android application and its integrated MPV playback engine.

```text
velocity-player/
├── app/
│   └── libs/
│       └── mpv-android-lib-v0.0.1.aar
├── gradle/
├── README.md
└── LICENSE
```

*Illustrative overview of the repository's key files; additional application source directories and configuration files are omitted.*

---

## Credits & Acknowledgments

Velocity Player builds upon existing open-source media playback projects.

- **[mpvEx](https://github.com/marlboro-advance/mpvEx)** — upstream project by abdallahmehiz.
- **[mpv-android](https://github.com/mpv-android/mpv-android)** — Android integration for the MPV media player.

Thank you to the maintainers and contributors whose work helps make advanced media playback accessible to the open-source community.

---

## Contributing

Contributions, bug reports, and thoughtful suggestions are welcome.

- **Report a bug:** [Open an issue](https://github.com/tylonmademe-1/velocity-player/issues).
- **Explore releases:** [View releases](https://github.com/tylonmademe-1/velocity-player/releases).
- **Contribute code:** Fork the repository, create a focused branch, and submit a pull request with a clear description of your changes.

Please include relevant device information, Android version, reproduction steps, and diagnostic logs when reporting playback or compatibility issues.

---

## License

Velocity Player is distributed under the terms specified in the repository's [LICENSE](LICENSE) file.

Please review the license before redistributing or modifying the project.

---

<p align="center">
  <strong>Velocity Player</strong>
  <br />
  <sub>Powerful playback. Thoughtful design. Open-source freedom.</sub>
  <br /><br />
  <a href="https://github.com/tylonmademe-1/velocity-player">GitHub Repository</a>
  ·
  <a href="https://github.com/tylonmademe-1/velocity-player/issues">Issues & Feedback</a>
</p>
