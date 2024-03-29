# jRAT Controller

## Features

- _should_ run on any system that supports Swing, full list [here](../README.md)
- Uses native system look and feel
- Easy to implement more types of clients, such as mobile phone users. Some work on Android devices has begun.


### Building

- Output as Java Archive ```.jar```
- Output as Mac OS X Application ```.app``` (using [JarToApp](https://github.com/redpois0n/JarToApp))
- Output as Windows Executable ```.exe```, requires .NET (using [jarbuilder](https://github.com/java-rat/jarbuilder))
- Export as
  - C/C++ Shellcode
  - C# Shellcode
  - Delphi Shellcode
  - Java Shellcode
  - Python Shellcode

## Arguments

Typed when running from terminal

```
$ java -jar Controller.jar --genkey --hidetitle --showhexkey ... ...
```

| Argument	  		            | Description
| ---         		            | :---
| --debug			            | Debugging mode
| --genkey			            | Create new key file to default location (files/jrat.key)
| --hidetitle		            | Main frame title is only "jRAT"
| --showhexkey		            | Prints current key file to console
| -h, --headless	            | Headless mode
| --nomenubar		            | Doesn't use OS X native menu bar
| --dump-default-config <path>   | Creates sample configuration

## Commands

Typed in the terminal

| Command	  		| Description
| ---         		| :---
| liststats			| Lists statistics for debugging purposes
| addstats			| Generates and adds random statistics for debugging purposes
| help				| Prints help for all available commands
| socket			| Adds, removes or modifies already existing socket
| save				| Force save all settings

## Dependencies

- [graphslib](https://github.com/redpois0n/graphslib)
- [iconlib](https://github.com/redpois0n/iconlib)
- [oslib](https://github.com/redpois0n/oslib)
- [swing-terminal](https://github.com/redpois0n/swing-terminal)
- [pathtree](https://github.com/redpois0n/pathtree)
- [json-io](https://github.com/jdereg/json-io)
