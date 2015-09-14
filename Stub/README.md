# jRAT Stub

## Features

- Runs on Windows, OS X, Linux, Solaris, *BSD. Full list [here](../README.md)
- Runs on headless systems (servers with no desktop)
  - Will disable graphical tasks such as Remote Desktop, thumbnails
- Plugin API [here](https://github.com/java-rat/stub-api)
- Anti-virtualization
- File melting
- Mutex (only allow one instance running)
- Only run on selected operating systems
  - Windows
  - Mac OS X
  - Linux
  - Solaris
  - *BSD
- Suppress dock icon on Mac OS X
- Persistance (make sure installation isn't removed)
- Tray Icon with custom icon and text

## Installation

### Windows

Creates registry value in ```HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Run``` pointing to the current Java installation ```javaw.exe``` followed by a ```-jar``` argument and finally the path to the JAR on disk

### Mac OS X

Creates launch agent in ```~/Library/LaunchAgents/``` (assumes Java is in $PATH)

### Linux, Solaris and *BSD (other systems too)

Creates [desktop entry](https://wiki.archlinux.org/index.php/Desktop_entries) in ```~/.config/autostart/```

### Headless Systems

No automatic startup method yet

## Dependencies

- [Commons](../Commons/)
- [oslib](https://github.com/redpois0n/oslib)
- [startuplib](https://github.com/redpois0n/startuplib)