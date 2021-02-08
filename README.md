# Reversi [![Build Status](https://travis-ci.com/enricodoretto/reversi.svg?branch=main)](https://travis-ci.com/enricodoretto/reversi)

Reversi is a strategy board game for two players, played on an 8×8 uncheckered board. It was invented in 1883. Othello, a variant with a change to the board's initial setup, was patented in 1971.

There are sixty-four identical game pieces called disks (often spelled "discs"), which are light on one side and dark on the other. Players take turns placing disks on the board with their assigned color facing up. During a play, any disks of the opponent's color that are in a straight line and bounded by the disk just placed and another disk of the current player's color are turned over to the current player's color. The object of the game is to have the majority of disks turned to display your color when the last playable empty square is filled.

## Features of the game
#### Game modes
- 1 vs 1 on local PC
- 1 vs 1 with remote opponent (as host or as client)
- 1 vs PC

#### Game options
- GUI or CLI version
- Custom board size
- Othello or Reversi

## How to play
Clone the repository, build the project, launch the main. If no arguments are present the default version is the GUI one. If "CLI" is passed as argument the game will start in the Command Line. 

##### Note: you need to have java 15 in order to compile the game

Input your name and choose the game mode.
Set the parameters required for the game mode chosen and play!

### Notes:
- In CLI, if you want to end the game before the end, press 'q' when it is your turn.
- In GUI, if you want to end the game before the end, close the window or press the main menu button
- To play the client-server version you need to open port 10000 on your firewall.
- When playing on big boards (26x26, 24x24) the board might take some time to update after a move is made.


# Developers
- [Doretto Enrico](https://github.com/enricodoretto)
- [Michelin Davide](https://github.com/DavideMichielin)
- [Nadizar Giorgia](https://github.com/giorgia-nadizar)
- [Tumino Adriano](https://github.com/Gideon996)


 
