
# Bomberman-Java

## Overview
This project is a classic Bomberman game clone developed in Java using Object-Oriented Programming principles. The game features a playable Bomberman character, enemies, bombs, explosions, destructible walls, and a simple UI. It is designed as a desktop application using Java Swing for the graphical interface.

## Features
- Playable Bomberman character with movement controls
- Place bombs to destroy walls and defeat enemies
- Multiple enemy types (e.g., Red Balloon)
- Start menu and pause functionality
- Timer and score display
- Simple and clean UI
- Object-oriented and modular code structure

## Installation
1. **Clone the repository:**
	```
	git clone https://github.com/AhmetAdilAkca/Bomberman-Java.git
	```
2. **Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse, NetBeans).**
3. **Build the project:**
	- Make sure you have JDK 8 or higher installed.
	- Build the project using your IDE's build tools or compile manually:
	  ```
	  javac -d Bomber/bin Bomber/src/main/*.java Bomber/src/objects/*.java Bomber/src/monsters/*.java Bomber/src/tiles/*.java
	  ```
4. **Run the game:**
	- From your IDE, run the `main.Bomberman` class.
	- Or from the command line:
	  ```
	  java -cp Bomber/bin main.Bomberman
	  ```

## How to Play
- Use the arrow keys to move Bomberman.
- Press the designated key (e.g., spacebar) to place a bomb.
- Avoid enemies and explosions.
- Destroy all enemies to win the level.
- Use the start menu to begin or quit the game.

## Project Structure
- `Bomber/src/main/` - Main game logic and UI
- `Bomber/src/objects/` - Game objects (Bomb, Door, Explosion, Skills, etc.)
- `Bomber/src/monsters/` - Enemy classes
- `Bomber/src/tiles/` - Tile and wall management
- `res/` - Game resources (images, maps)

## Credits
- Developed by Ahmet Adil Akca
- Inspired by the classic Bomberman game

## License
This project is for educational purposes. Please check the repository for license details.
