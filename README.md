# Velocity Player

<p align="center">
  <img src="https://raw.githubusercontent.com/tylonmademe-1/velocity-player/main/i.png" width="120" alt="Velocity Player Logo"/>
</p>

<p align="center">
  <b>The ultimate open-source Android video player — powered by MPV, wrapped in Liquid Glass.</b>
</p>

<p align="center">
  <a href="https://github.com/tylonmademe-1/velocity-player/releases"><img src="https://img.shields.io/github/v/release/tylonmademe-1/velocity-player?style=flat-square" alt="Release"/></a>
  <a href="LICENSE"><img src="https://img.shields.io/github/license/tylonmademe-1/velocity-player?style=flat-square" alt="License"/></a>
  <a href="https://github.com/tylonmademe-1/velocity-player/issues"><img src="https://img.shields.io/github/issues/tylonmademe-1/velocity-player?style=flat-square" alt="Issues"/></a>
</p>

---

## Features

### Playback
- Powered by **MPV** — supports virtually all video and audio formats
- Hardware & software decoding with **gpu-next** and **Vulkan** rendering
- **Anime4K upscaling** with presets (A, B, C, A+, B+, C+) and quality modes (Fast, Balanced, High)
- **Debanding**, **YUV420P** pixel format, and **precise seeking**
- **Frame-by-frame navigation** with snapshot saving
- **Playback speed control** with pitch correction
- **Volume normalization** and boost cap
- **Background playback** with media notification controls
- **Picture-in-Picture (PiP)** with auto-PiP support
- **Sleep timer**, autoplay next video, and save position on quit
- **Recently played** history tracking

### Subtitles
- Auto-load external subtitles by filename
- **Online subtitle search & download** (SubDL / Wyzie)
- Full subtitle customization — font, size, color, border, shadow, position, scale
- ASS/SSA subtitle override, delay adjustment, custom fonts directory

### Audio
- Multiple audio track selection with preferred language setting
- Audio channel modes: Auto, Mono, Stereo, Reversed Stereo
- **Audio delay** adjustment and external audio track loading

### Network Streaming
- **SMB**, **FTP**, and **WebDAV** network connections
- Network file browser with breadcrumb navigation
- **M3U / M3U8** playlist support via local file or URL
- Network video thumbnails (experimental)

### File Browser
- Local file system and folder list browser
- Video scanning, metadata caching, and thumbnail generation
- Sort, rename, delete, copy/paste, multi-selection
- Folder blacklist and video/folder search

### Playlists
- Create, rename, delete, and manage playlists
- Import M3U/M3U8 playlists from URL or local file with refresh support

### Gestures
- Double tap left/center/right — seek, play/pause, or custom action
- Horizontal swipe to seek with adjustable sensitivity
- Vertical swipe for brightness & volume
- Pinch to zoom, hold for multi-speed playback
- Swappable volume/brightness sliders

### UI & Appearance
- **Liquid Glass** aesthetic with glass card opacity and corner radius control
- **120fps UI** support for 120Hz displays with motion blur transitions
- **50+ built-in themes** — Dracula, Tokyo Night, Catppuccin, Nord, Mocha, Rose Piné, and more
- **Material You** dynamic colors (Android 12+), AMOLED black mode
- Custom color picker, nav bar opacity, dark/light/system theme modes
- "New" label for recently added unplayed videos, watched threshold setting

### Player Controls
- Fully **customizable button layout** (top-right, bottom-right, bottom-left, portrait)
- Aspect ratio modes: Fit, Crop, Stretch, Zoom
- Video filters: Brightness, Contrast, Gamma, Saturation, Hue, Sharpness
- Stats overlay (CPU/GPU, frame timing, cache, keybindings, track list)
- Chapters, decoder selection, ambient mode
- **Lua scripts** support with custom player buttons

### Advanced
- Edit **mpv.conf** and **input.conf** directly in-app
- Export/import settings as XML
- Verbose logging, log dump, in-app update checker
- Clear playback history, thumbnail cache, font cache, config cache
- Crash screen with database reset option

---

## Credits

Built on top of [mpvEx](https://github.com/marlboro-advance/mpvEx) by abdallahmehiz.  
Powered by [mpv-android](https://github.com/mpv-android/mpv-android).

---

## License

This project is licensed under the terms of the [LICENSE](LICENSE) file.
