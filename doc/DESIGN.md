# Breakout Design
## Nikita Daga


## Design Goals
I wanted to make it easy to add new types of blocks (different colors, number of hits required, and points),
cheat keys, power-ups, and block configurations for each level.

## High-Level Design
In addition to the Main class, I have a class for Ball, Paddle, Blocks, and GamePlay. 
* The ball class is responsible for creating the ball and defining its movement on the screen. It interacts with the Paddle class to check if there has been a collision between the two elements.
Based on whether and which part of the paddle the collision was in, the ball movement is modified. The Ball class also has methods for changing the speed and the size of the ball, which are called when Power-Ups are activated.
* The Paddle class is responsible creating the paddle and handling its movement (and warps across the screen) according to user Key Presses for which it interacts with the Main. It also has methods for changing width and speed which are called when Power-Ups/ Cheat Keys are activated from different classes.
* The Blocks class is responsible for reading the level-wise text file (upon retrieving current level from the GamePlay class), and creating the appropriate blocks. It defines the color and points of the block as well as number of hits needed to break them and if the block will give a power-up.
It has a method for handling collisions with the Ball. Upon collision, the method updates the hits needed to destroy the block, the total score of the player (from GamePlay), removing the block from the screen, updating the bounce direction of the ball (from Ball), and activating a randomized power-up. The activate and deactivate power-up methods in this class interact with different classes based on whether the ball speed/size, paddle width, or double scores need to be changed.
This class also checks whether the Level is Complete if lives are 0 or all blocks have been destroyed and passes this value to the Main Class to update the next stage accordingly.
* The GamePlay class is responsible for setting the game screen with the appropriate level, number of lives, and score. It also displays the screen between levels, instructions, and the game over/congratulations page. It is responsible for interacting with the Blocks class to load the right level. The lives and scores are also updated within GamePlay. Finally, all cheat keys are called in the GamePlay and depending on their functions, interact with other classes to implement functionality.
* The Main calls various screens from GamePlay when appropriate (ex: instructions when run, and then gameplay when the space bar is pressed). It handles the timeline of the animations as well as noting any key presses and calling the appropriate class (GamePlay for cheat keys and Paddle for arrow keys). 

## Assumptions or Simplifications
The game only moves forward. This means that there is no method that stores the initial state of the game which can be called for a reset. Any features that require going back in time and accessing stored values need to manually be updated to simulate a reset. This makes it hard to completely restart the game once started. the deactivation of power-ups also requires manually reducing the length of an increased paddle, rather than simply calling a reset length method.

## Changes from the Plan
* Features unimplemented:
    * BREAK 15C Exploding: When an exploding block is destroyed, it also destroys (or damages, in the case of Multi-Hit Blocks) neighboring blocks.
* Modified Features:
    * BREAK 17D S Updated: The instruction page pops up as a pause, however, it does not reset the game so users can pick up where they left off after seeing the rules.
    * In Level 2 Features: Update: Not randomized but set by color. Blocks giving 300 points need 2 hits.
    * In Level 3 Features: Update: No blocks are indestructible, rather those blocks giving 400 points need 3 hits. The paddle is not sped up since there is a cheat key to do this.
* Known functionality error: Ball changes speed if the screen is moved from the initial Level 1 game scene. 

## How to Add New Levels
To add new levels, the update method that checks if 3 levels are completed should be modified to check for x levels. Then, the text files showing block configurations for each level should be added as "levelX_blocks". If the level would like to have different types of blocks that have different colors, scores, or hits_required, these details can be added to the getColorFill(), calculatePoints(), and calculateHitsNeeded() methods respectively. In the loadLevel method of the GamePlay class, if statements can be added to set ball speed/size or paddle speed/size based on the level. 