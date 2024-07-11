# Arkanoid Plan

### Nikita Daga

## Basic Game Rules

* When a player starts a level, the ball should be launched away from the paddle and continue moving
  in that direction until it comes into contact with a block, wall, or paddle.
* When the ball comes into contact with a wall or the paddle, the ball should bounce off of the wall
  or paddle.
* When the ball comes into contact with a Basic Block, the ball should bounce off the block and the
  block should be destroyed.
* When a player presses left and right arrow keys, the paddle should move so that the player can
  keep the ball from exiting the window.
* When the ball comes into contact with the bottom of the window, the player should lose a life and
  the ball reset to its initial position.
* When all blocks in a level have been cleared, the level has been completed. The game should load a
  new level with different block configurations and interactions.
* The game should include 3 levels, which should be distinct from one another in block
  configuration.
* When 3 lives have been lost (balls missed) or all levels are completed, the game should end.
* During game play, the game display should display the number of remaining lives and current level.
* When the game is first loaded, the game should display a splash screen detailing game rules.
* The player should score points when destroying a block, receiving a power-up, and completing a
  level. The game display should display the current score.
* When the user completes each level, the game should display a results screen indicating whether
  the player won or lost, score, and numbers of live remaining.

## Paddle Variations

* Positional Bounces: When the ball bounces off the paddle, the direction it bounces is
  determined by where on the paddle the contact was made (i.e., the middle third cause the ball to
  bounce normally, the left and right thirds cause the ball to bounce back in the direction it
  came).

* Warped: When the paddle reaches the edge of the screen, it warps to the other side

## Block Variations

* Power-Up: When a power-up block is destroyed, the player receives a random power-up.

* Multi-Hit: When the ball comes into contact with a multi-hit block, the block’s health
  is decremented. If the health reaches zero, the block is destroyed.

* Color-Coded: Different blocks give different point values upon destruction

## Power-up Types

* Paddle Extension: When the player gets a Paddle Extension power-up, the width of the
  paddle is expanded to make it easier to block the ball.

* Speed Down: When the player gets a Speed Down power-up, the speed of the ball decreases.

* Grow Ball: When the player gets a Grow Ball power-up, the size of the ball grows and
  damages more blocks.

* Double Score: When the player gets a Double Score power-up, each destructed block will
  give double the score for 15 seconds

## Cheat Keys

* L: When the player presses the L key, their current life total should be incremented by

* R: When the player presses the R key, the ball and paddle should be reset to their
  starting positions.

* 1-3: When the player presses any key 1-3, the current level should be cleared and the
  game should load the level corresponding to that key

* S: When the player presses the S key, the game pauses and the splash screen appears.

* C: When the player presses the C key, the game changes color mode for a visual
  variation.

* F: When the player presses the F key, the paddle movement becomes faster for 15 seconds.

## Level Descriptions

| Number | Brick                       |
    |--------|-----------------------------|
| 0      | 0 points                    |
| 1      | 100 points                  |
| 2      | 200 points                  |
| 3      | 300 points                  |
| 4      | Power-Up                    |
| 5      | 400 points + require 3 hits |

* Level 1: All blocks would break with one hit. The ball would have a given speed and the paddle
  would have a given length (unless altered by a power-up).


* Level 2: Blocks may require upto 2 hits to break. The ball speed would increase from level 1.


* Level 3: There will be some blocks that need to be hit thrice. The paddle size would reduce from
  the previous levels.



