# Arkanoid Design

## Nikita Daga

## Design Goals

I wanted to make it easy to add new types of blocks (different colors, number of hits required, and
points), cheat keys, power-ups, and block configurations for each level.

## High-Level Design

In addition to the Main class, I have a class for Ball, Paddle, Blocks, and GamePlay.

* The ball class is responsible for creating the ball and defining its movement on the screen. It
  interacts with the Paddle class to check if there has been a collision between the two elements.
  
* The Paddle class is responsible creating the paddle and handling its movement (and warps across
  the screen) according to user Key Presses for which it interacts with the Main. 

* The Blocks class is responsible for reading the level-wise text file (upon retrieving current
  level from the GamePlay class), and creating the appropriate blocks. It defines the color and
  points of the block as well as number of hits needed to break them and if the block will give a
  power-up.
  
* The GamePlay class is responsible for setting the game screen with the appropriate level, number
  of lives, and score. It also displays the screen between levels, instructions, and the game
  over/congratulations page.

## Assumptions or Simplifications

The game only moves forward. This means that there is no method that stores the initial state of the
game which can be called for a reset.


## How to Add New Levels

To add new levels, the update method that checks if 3 levels are completed should be modified to
check for x levels. Then, the text files showing block configurations for each level should be added
as "levelX_blocks". If the level would like to have different types of blocks that have different
colors, scores, or hits_required, these details can be added to the getColorFill(),
calculatePoints(), and calculateHitsNeeded() methods respectively. In the loadLevel method of the
GamePlay class, if statements can be added to set ball speed/size or paddle speed/size based on the
level. 