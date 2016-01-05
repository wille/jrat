# jRAT 5.1.3


### Released on 2016-01-05

- Fix hidden files on -nix not working correctly
- More safe file dropping (shell executing executables, running JARs with Java)
- graphslib updated
- Lite/Limited version removed
- Tray Icon is default in minimal builder
- Plugins transferred on connect will not be written to disk
- Do not try to load plugin main class from manifest
- Invokes onConnect() correctly on plugin transfer
- Fix headless errors (Do not show EULA frame if headless)
- If download fails, error will show correctly***


# jRAT 5.1.2


### Released on 2015-10-19

- Fixed flag issue that would cause the client to crash if no flag was found
- Return correct flag in sample mode
- Some country character fixes
- Only show monitor dialog if there is more than one monitor on the remote system
- Safer client detection in API
- Constant authentication domain
- Stub uses startuplib
- Do not update graphs if running headless
- Do not update sockets view if running headless
- Prevent loading of tray icon if running headless
- Do not update network graph if running headless
- Update 'help' command
- 'save' command added
- 'socket' command added
- ZKM automation removed
- Lite version tray messages changed
- Wipes encryption keys on start
***


# jRAT 5.1.1


### Released on 2015-07-31

Views
	
	- Can have more than one view (default is Table)
	- New view introduced, Boxes


- New version of swing-terminal, supports colors
- Listing processes now shows icons (Windows Vista and above only)
- Fix minimal builder making advanced builder not open
- Anti virtualization now detects disk with space less than 50 GB
- C/C++ shellcode exporting fixed
- Binding fixed
- Install message will not block execution of client
- Multiline install message
- Client will not break if any property contains a newline character
- .app properties dialog fixed
- EULA countdown removed
- --nomenubar argument, can be used to run jRAT if it can't use the OS X native menu bar
- Fix frames not showing all components on OS X***


# jRAT 5.1


### Released on 2015-07-06

- Fix socket closing
- Remove sound and editor setting panels
- Fix settings about tree item
- No cells can be edited (if they shouldn't)
- Bigger packet space
- Tray icon fixed
- Tray icon tooltip displays network usage
- Sends time running to web panel
- Computer name is now called hostname
- Fix cursor drawing
- Remove log expanding
- Some work on running headless
- Doesn't stop loading plugin if one fails
- Fix bug where config would not be decrypted resulting in a crash on systems where the absolute path to the stub contains a space (Affected Windows XP)
- Network Usage column
- Fix network counter
- Fix network download speed***


# jRAT 5.1rc2


### Released on 2015-05-17

- Can run on headless systems (servers or computers without monitor, keyboard, mouse)
- Network monitor values correct
- Registry actions fixed & improved
- No duplicates in registry tree
- No errors if flag doesn't exist
- Shows country name in system info
- About dialog changed
- Stressing removed
- Removed unused progressbar in file transfer frame
- Stub V is renamed to Version
- Registry menu item only enabled if client is running Windows
- Uses pluginlib
- DNS rotator not switching to latest address in list fixed
- Obfuscated Key Exchanging
- Stops file searching if connection closes
- Can choose to not install stub

Detects the following operating systems
	
	- Lunar Linux
	- NixOS
	- Crux
	- BLAG
	- gNewSense
	- Dragora
	- Chapeau
***


# jRAT 5.1rc1


### Released on 2015-04-09

- Network usage panel working
- Updater updated
- Removes control panel from memory when closing
- Double click fixed in local file table
- SteamOS icon fixed
- Shows cleaner memory usage in remote processes
- Remote Shell fixed
- Sound listening and recording fixed
- Advanced Builder icon fix
- Viewing of environment variables fixed
- Restarting client is safer
- Assumes Java is in $PATH on OS X
- Main Frame now has better icon resolution
- New info icon
- Basic labels added to FrameAbout
- Picking monitor and viewing monitors thumbnails works correctly on multi monitor systems
- Remote Screen multiple monitor problem fixed
- Monitors in control panel now is in scale 1:10
- Can lower remote screen size without crashing
- Performance Frame removed
- Startup changes related to melting and "run at next boot"
- Run at next boot works on other systems than Windows only
- Melting installer fixed
- Runs uninstall script in correct shell
- Debug frame now contains more detailed operating system information
- Plugins frame is nicer
- Root node not visible in registry frame
- pathtree.jar updateed
- Uses iconlib.jar
- Not spawning multiple memory monitor threads
- Selecting ping icons removed
- Fixes to weird crashes on Arch Linux based distros (Maybe OpenBSD)

File Manager changes
	
	- Open current file in File Manager in search panel
	- Thumbnail preview is now a tab in File Manager
	- Search files is now a tab in File Manager


Transfers changes
	
	- Shows current file transfer speed
	- Fixed cancelling downloads
	- Transfers plugins concurrently
	- Fixed pausing downloads
	- Shows time remaining on transfers
	- Only progressbar is colored in transfers
	- Status column
	- Tab icons
	- Marks downloads as completed correctly
	- Selects transfer tab automatically when downloading/uploading
	- Can download multiple files at once
***


# jRAT 5.1b2


### Released on 2015-04-03

- Remote Screen bug fixed
- Removed settings warning on upgrade
- OS stats partially working again
- Anything related to FileZilla removed
- System Info frame icon
- Map in Trace tab in Control Panel, will automatically download image from Google Maps
- Remote File manager rewritten, tab for active transfers
- Can pause/cancel ongoing transfers
- Can transfer several files at once
- Safer concurrency
- Error message removed from opening Remote Screen
- Clients table drawing bug fixed***


# jRAT 5.1b


### Released on 2015-03-27

- Web panel compatible
- Remote Screen resizing fixed
- Network Usage meter
- Shows correct data units
- Will not crash if can't reach website
- Plugins in menu bar shows information if clicked with no handler
- Saves custom row height
- Shows hostname and username as Host\User on Windows
- Library loading changed
- Language name instead of 2 characters
- Instead of unique and all connections, will track statistic of operating systems and all connections
- File transfer crash fix
- Saves offline clients (For one week maximum, for use with Web Panel)
- Gets correct hostname on -nix
- Toggling encryption removed
- Supports Russian, Hebrew or any other similar language without problems
- Will only ping if 2500 MS since last activity
- Copying password in sockets panel working
- Can install in root drive
- Encrypts password in socket settings with static key
- Can listen to two types of ports, normal connections and web panel
- File melting will not hang forever if fails to delete file
- New terminal component in Remote Shell
- /bin/tcsh default shell on -BSD
- Major improvements to shutdown, lock and reboot commands
- Remote Process improved on -nix
- Remote Registry now has tree for browsing folders
- Only enables supported actions in Control Panel
- Default textbox values in builder
- One option to include all BSDs when building
- Load local hosts file on OS X and Linux
- Fixed critical socket crash error
- Connect and disconnect sounds removed
- About frame background changed
- EULA updated
- New selection color on trees and tables
- Remote screen cursors fixed
- Unused images removed
- Donate menu item removed
- Can no longer use "get systeminfo.exe" or refresh system information
- Shows real memory usage
- New memory usage graph
- Shows dialog picker when opening remote screen from control panel
- Better installation date detection
- Can execute Python script (Only if python is detected)
- Can only execute shell script on -nix and batch only on Windows
- Fixes to previewing of images and thumbnail viewing
- On connect panel fixed
- Can now download files not only from http


Main Frame changes
	
		- Toolbar removed
		- Toggle what columns that should displayed 
		- Main frame has no border
		- No visible grid
		- Sample Mode improved
		- 


Operating System changes
	
		- Uses new oslib.jar
		- Detects desktop environment
		- Detects CPU (-nix only)
		- Will show OS X in format "Mac OS X Yosemite 10.10"
		- Detects more Windows versions
		- DragonFlyBSD detected
		- Detects Linux distros Antergos, Chakra, Crunchbang, KaOS, Evolve OS, Frugalware, Funtoo, Jiyuu, Deepin, Korora, Mageia, Mandriva, Mandrake, Manjaro, LMDE, openSUSE, Parabola, Peppermint, Redhat Enterprise, Sabayon, Scientific Linux, SolusOS, SteamOS, TinyCore, Trisquel, Viperr
		- Detects Distro version and codename
		- Better distro detecting
		- Better BSD detecting
		- Better OS X detecting


Stub changes
	
		- Installer.jar removed
		- Key not saved in zip entry
		- Duplicates itself when installing
		- Loops through startup locations if failing to write to specified


Icon changes
	
		- Windows XP icon
		- Windows 2000 icon
		- New Mac OSX icon


Advanced Builder changes
	
		- Plugin panel looks better with GTK themes
		- Plugin panel only shows file names
		- Shows stub file icons
		- General panel label fix for GTK themes
		- Not showing up with default GTK theme on Linux fixed


System Information panel
	
		- OS icon shows correctly
		- Ping row removed
		- Key name changes


Protocol
	
		- Uses AES-128-CTR on streams
		- Uses AES-128-CBC on files

***


# jRAT 5


### Released on 2015-01-10

- View image thumbnails bug fixed
- Image preview bug fixed
- Will not crash if failing to load plugin
- Table row height saves in settings
- Shows distro name properly in Linux, along with uname -a in Computer Info
- Successful startup if logged in as root on Linux/BSD
- Anti VM check also checks for hard disk size under 60 gb
- Improvements in remote file frames
- Some table adjustments
- Some frames that had size 0, 0 will show properly on other systems than Windows
- Sample mode updated
- Solaris support (Will replace SunOS with Solaris)
- FreeBSD (PCBSD) support
- Partial OpenBSD support
- Not removing startup file on Linux fixed
- Detects Ubuntu
- Detects Kali/Backtrack
- Detects CentOS
- Detects Debian
- Detects Elementary OS
- Detects Linux Mint
- Detects Slackware
- Detects Gentoo
- Detects Arch
- Detects Raspbian
- domains.txt removed jrat.me***


# jRAT 4.5


### Released on 2014-12-14

- New remote screen that only updates changed areas on desktop
- Web request timeout set
- Connection bug fixed
- Computer info frame exit on close fixed
- Transfers plugins to remote machine on connect if not installed (Will do this each connect)
- Toggle plugin transferring in plugins menu item
- Native packet range extended to 110
- Doesn't write some extra bytes when using file transfer
- New domain (jrat.se)
- Plugin gallery bug fixed where more than 3 plugins would smash it together
- Webcam plugin updated to support selecting of webcam
- Remote SOCKS plugin added***


# jRAT 4.4.4


### Released on 2014-12-02

- Backwards compatible
- Changes to how HTTP requests are made
- Doesn't check domain queue each connection
- -nossl argument removed
- domains.txt file will specify which domains it will connect to in case one is unavailable
- Windows XP startup working
- Better vBox and VMware detection***


# jRAT 4.4.3


### Released on 2014-11-25

- Get external IP in Build fixed
- Backup domain system working properly
- Request elevation on Windows, OS X and Linux
- Check in log if plugin is updated
- Protocol changes (Not backwards compatible)
- Active ports control panel shows blue on connecting
- .app output option
***


# jRAT 4.4.2


### Released on 2014-10-03

- New plugin online installer
- New online plugin system, developers can host their own files and have it download through the gallery
- -nossl parameter, doesn't connect through HTTPS***


# jRAT 4.4.1


### Released on 2014-10-16

- Non-countries removed from country list
- 300 second countdown on lite version removed (Still forced tray icon and disabled features)
- Make installer run the file next boot instead of directly (Windows only)
- HTTPS connection to website
- Plugin frame fixes
- Plugin status strings
- Be able to compile archives with plugins
- Windows 8 icon
- Sockets will not disappear if failing to bind
- Most settings loads in plain text***


# jRAT 4.4


### Released on 2014-09-21

- Quick Jump to directories in File manager
- Inject JAR file into memory from file or url
- Upload and execute and Update from file
- Network stressing re-added
- ARME rewritten
- Slowloris added
- Under the hood changes***


# jRAT 4.3.3


### Released on 2014-08-23

- Remote screen disconnect bug fixed
- Remote screen crashing will not reconnect fixed
- -melt instead of MELT argument
- Upgrade menu item points to /purchase.php
- On connect event fixed
- Host file related actions works on OS X and Linux too
***


# jRAT 4.3.2_1


### Released on 2014-06-23

- Advanced builder bug fix
- Plugin install bug fix***


# jRAT 4.3.2


### Released on 2014-06-15

- Automatic updater, press Update when notified
- Key text field removed from add socket
- 300 seconds notification when not premium
- Advanced builder only if premium***


# jRAT 4.3.1


### Released on 2014-06-05

- Twitter in menubar added
- New icons
- Warns if port is used***


# jRAT 4.3


### Released on 2014-05-28

- All folders and files now in /files/ more structured
- Can see thumbnails when choosing monitor
- Performance frame more info
- Import key feature (KEY GOES IN /files/jrat.key NOW)
- Ping icon values changed
- File search icon placement fixed
- Windows delete file on uninstall re-made
- Prevent from running in VMware (More software to come)***


# jRAT 4.2.2_2


### Released on 2014-05-12

- Not being able to use plugins fixed
- .app for OS X***


# jRAT 4.2.2_1


### Released on 2014-05-04

- Encryption text fields removed
- Bug making all connections crash fixed***


# jRAT 4.2.2


### Released on 2014-05-02

- Grid color bug fixed
- FrameAbout back
- Better error explanations on connection fail
- New handshake and RSA handshake implemented, will not work with old versions
- jRAT will generate your key pair if it doesn't exist
- Encryption key references removed from build and sockets tab since this is dynamic now
- Monitor tab in control panel better
- Pick monitor from monitor setup when choosing screen to remote control
- Active ports only executes on Windows
- Lots of under the hood changes***


# jRAT 4.2.1


### Released on 2014-02-23

- Small under the hood changes
- Non premium clients closes after 5 mins
- Ad frame removed, FrameAbout cleared
- Plugins working***


# jRAT 4.2


### Released on 2014-02-18

- New domains***


# jRAT 4.1.5_1


### Released on 2014-01-13

- Build fix***


# jRAT 4.1.5


### Released on 2014-01-12

- Remote screen remade
- Change remote screen settings on the fly
- Keyboard improved in remote screen
- Generate multiple keys easier
- Rename connection id bug fixed
- Small design "fixes" on some frames
- Server>Connection, Client>Controller
- ZKM support integrated for custom builds
- Rest is under the hood fixes***


# jRAT 4.1.4_4


### Released on 2013-12-08

- Small global crash fix***


# jRAT 4.1.4_3


### Released on 2013-12-08

- Small plugin installation fix
- Small change to allow well requested plugin
- Sample Mode fixed***


# jRAT 4.1.4_2


### Released on 2013-11-21

- Plugin download/build bug fixed***


# jRAT 4.1.4_1


### Released on 2013-11-20

- Plugin gallery working***


# jRAT 4.1.4


### Released on 2013-11-20

- Ad for donations doesn't show if premium
- Now you can have multiple IP/DNS to connect to when building
- Redirect removed
- Online gallery now includes plugins***


# jRAT 4.1.3


### Released on 2013-10-26

- Fixed a bug where over around 32 gigs of RAM would display incorrectly
- New graphical controls for performance frame and control panel
- Drain CPU removed, useless feature
- Libs now loads from files/lib/ instead of files/
- Install Message not working now fixed
- Debug Info now includes os version and os arch
- Executable output improved a lot from what it was before***


# jRAT 4.1.2_1


### Released on 2013-09-22

- Install in root drive fixed
- Install in root drive is now not default
- Update button fixed***


# jRAT 4.1.2


### Released on 2013-09-22

- KL plugin tries to enable assistive devices on OSX BEFORE hooking
- 2500 actions buffer in KL plugin
- Webcam quality 0,25 - 0,75 (Quality slider next small update)
- Delay in Installer.jar fixed
- Create LaunchAgents folder on OSX if doesn't exist (Prevents problems on many machines)
- File Manager download now has default filename
- Executable output will end with .exe automatically
- Install in root drive (C:\ or /)
- Quality now working in remote screen again (slider when viewing soon)
- Quick Remote Screen overflow fixed
- Text to Speech (Speech) fixed
- Run Command now also opens files
- Geo Location fixed***


# jRAT 4.1.1


### Released on 2013-08-15

- Quick update to fix auth, actually not complete one planned
- File Transfer upload fixed
- Safer updating & downloading, asks for file type, tested on ge.tt
- Argument added
***


# jRAT 4.1


### Released on 2013-08-13

- Remote Desktop out of memory FIXED
- Webcam faster, lower quality
- Sound capture working
- Now startups correctly if ran as root on Mac OSX
- Remote Screen now (a bit) faster
- Resize remote screen (50% smaller default)
- Keylogger working on OSX 10.8.3 without root needed (First time may need root to enable accessive devices, jRAT will do this for you)
- Keylogger works without root on Linux (Ubuntu tested)
- OnStart method instead of onEnable for plugins***


# jRAT 4.0.3_1


### Released on 2013-08-10

- Changelog small bug fixed***


# jRAT 4.0.3


### Released on 2013-08-10

- Remote Screen now works continuosly together with other actions
- Sound capture fixed
- .exe output not recommended
- Important message when generating key
- Works on Windows XP
- Minecraft stealer removed due to being outdated and not used good
- Small note on how to disable HTTP question dialogs
- Update warning about not loosing jrat.key***


# jRAT 4.0.2


### Released on 2013-07-27

- Update from URL improved/fixed
- Thumbnail bug fixed***


# jRAT 4.0.1


### Released on 2013-07-15

- Generate key in main frame (Main>Generate key)
- Remote file fixes
- Hebrew & Chinese browsing confirmed working, working on other languages
- Control Panel close bug
- Upgrade menu item***


# jRAT 4.0


### Released on 2013-07-12

- Plugin packet fixes
- Flood removed permanently
- Text to Speech improved
- ID added for premium***


# jRAT 3.4.1


### Released on 2013-07-03

- Memory leaks fixed with remote screen and file transfer
- Contributors and donators now lists
- Local Area Network Computers in control panel has icon
- Remote Registry improved
- Thumbnail preview now supports transparency
- Linux icon fix***


# jRAT 3.4


### Released on 2013-07-02

- Online plugin gallery
- UniqueID for future licensing
- Building with stub plugins is better
- Preview plugin frames
- Still starts if website offline
- View antivirus, firewall (windows only)
- Extractor added to website for more simple installing 
- Better design
***


# jRAT 3.3_3


### Released on 2013-06-22

- API fixes and changes
- Example API***


# jRAT 3.3_2


### Released on 2013-06-21

- Date null pointer fixed***


# jRAT 3.3_1


### Released on 2013-06-21

- Critical control panel bug fixed
- Changelog now displays in HTML, shows all logs
- Persistance now works on Linux
- File transfers now compresses using GZip
- Will ask for directory if launched in home dir (double click)***


# jRAT 3.3


### Released on 2013-10-20

- NEW DOMAIN, jrat.pro
- Text Protocol > Binary Protocol
- More faster and stable
- Load key and password from opened sockets
- Plugin renderer fixed
- API fixes, improvements
- Flood re-added (better)
- Control Panel Loaded Plugins columns fixed
- Ping timeout problem if big transfer is occuring
- More compression in work***


# jRAT 3.2.5_1


### Released on 2013-06-10

- Binder working (again)
- Compression on remote screen, soon to be more
- Annoying bug with ping fixed***


# jRAT 3.2.5


### Released on 2013-06-10

- Now installs successfully on Linux. Tested on Ubuntu.
- Flood hidden
- Optionable encryption to support languages such as Russian, Arabic, Chinese
- Encryption changed to AES with 128 bit key
- LR donations removed, LTC added
- Using IP2C library to get geo location (Selected by default)
- Binder working
- Proxy added
- Frame for installing plugins removed
- Using .bookmarks, .settings in config files***


# jRAT 3.2.4


- API changes***


# jRAT 3.2.3.9


- Timeout now default***


# jRAT 3.2.3.8


- Quick Remote Desktop added
- Fix for plugin loading fail, use "Do not load classes"
- Other small fixes***


# jRAT 3.2.3.7


- Sound capture fixed
- Better execution methods***


# jRAT 3.2.3.6


- Small fixes.***


# jRAT 3.2.3.5


- Plugins out and working***


# jRAT 3.2.3_4


- API ready for plugins***


# jRAT 3.2.3_3


- Uninstall now removes jar
- API functional***


# jRAT 3.2.3_2


- Version URL changed***


# jRAT 3.2.3_1


- Protocol fixes***


# jRAT 3.2.3


- Temporary release***


# jRAT 3.2.2_2


- Advanced Build error fixed***


# jRAT 3.2.2_1


- Line number tables added for debugging***


# jRAT 3.2.2


- Critical build and settings bug fixed***


# jRAT 3.2.1


- Small bug fixes
- API working***


# jRAT 3.1


- Full Mac OS X functionality
- Does not work with previous jRAT versions***


# jRAT 2.3


- Unknown***


# jRAT 2.2


- Unknown***


# jRAT 2.1


- Not backwards compatible***


# jRAT 2.0


Unknown***


# jRAT 1.2


None***


# jRAT 1.1


- Mac+Linux more functions working.
- NOTE: Mac shows in taskbar
- Multi IP support
- Chat
- ARME flood
- Rename
- Flag
- Reconnect
- JavaScript
- Crazy mouse
- Play sound from url
- Better log (Not finished)
***


# jRAT 1.0


- Initial release***


