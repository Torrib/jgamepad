jGamepad
========

Controller library for java using Xinput  
Currently only for Windows

## Features
* Use of the GUIDE button (Could not find any java libraries that supported this)
* Hotswapping of controllers (Controllers do not need to be on when your program starts)
* Easy to use button listeners for both press and hold events.

## Requirements
* Requires that DirectX is installed

## How to use the library
* Import jgamepad-x.x.x from release.
* Import jgamepad-javadoc-x.x.x as well if you want the javadoc (recomended).
* Copy Controller64.dll and Controller.dll to your project.
* If the dll files are not at the project root set the path using `Controller.dllPath = [path]`
* See src/examples for example usage


