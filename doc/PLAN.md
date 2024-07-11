# Breakout Plan
### Nikita Daga

## Interesting Breakout Variants

 * Brick Breaker Hero: This is interesting because there are periods of variation within each level. The player has another goal of dodging attacks from the boss enemies. If caught by an attack, the gameplay becomes harder for a period of time without changing modes or levels, keeping users engaged. 

 * Devilish: Allowing users to keep moving forward changes the incentives of the game from trying to hit all bricks, to consistently hitting bricks along the same path. This is interesting because the skills required for this include consistency of ball placement and more accurate aim.


## Paddle Ideas

 * BREAK 14A Positional Bounces: When the ball bounces off the paddle, the direction it bounces is determined by where on the paddle the contact was made (i.e., the middle third cause the ball to bounce normally, the left and right thirds cause the ball to bounce back in the direction it came).

 * BREAK 14X Warped: When the paddle reaches the edge of the screen, it warps to the other side


## Block Ideas

 * BREAK 15A Power-Up: When a power-up block is destroyed, the player receives a random power-up.

 * BREAK 15B Multi-Hit: When the ball comes into contact with a multi-hit block, the block’s health is decremented. If the health reaches zero, the block is destroyed.

 * BREAK 15C Exploding: When an exploding block is destroyed, it also destroys (or damages, in the case of Multi-Hit Blocks) neighboring blocks.

 * BREAK 15X Color-Coded: Different blocks give different point values upon destruction


## Power-up Ideas

 * BREAK 16A Paddle Extension: When the player gets a Paddle Extension power-up, the width of the paddle is expanded to make it easier to block the ball.

 * BREAK 16X Speed Down: When the player gets a Speed Down power-up, the speed of the ball decreases. 

 * BREAK 16X Grow Ball: When the player gets a Grow Ball power-up, the size of the ball grows and damages more blocks.

 * BREAK 16X Double Score: When the player gets a Double Score power-up, each destructed block will give double the score for 15 seconds


## Cheat Key Ideas

 * BREAK 17A L: When the player presses the L key, their current life total should be incremented by 1.

 * BREAK 17B R: When the player presses the R key, the ball and paddle should be reset to their starting positions.

 * BREAK 17C 1-3: When the player presses any key 1-3, the current level should be cleared and the game should load the level corresponding to that key

 * BREAK 17D S: When the player presses the S key, the level should be cleared and the starting splash screen loaded as if the game had just started.

 * BREAK 17X C: When the player presses the C key, the game changes color mode for a visual variation. 

 * BREAK 17X F: When the player presses the F key, the paddle movement becomes faster for 15 seconds.


## Level Descriptions

| Number | Brick          |
    | ------ |----------------|
| 0 | 0 points       |
| 1 | 100 points     |
| 2 | 200 points     |
| 3 | 300 points     |
| 4 | Power-Up       |
| 5 | Indestructible |

* Level 1: All blocks would break with one hit. The ball would have a given speed and the paddle would have a given length (unless altered by a power-up).

      3 2 3 0 1 2 4 1
      
      0 0 3 4 2 1 2 2
      
      4 2 1 1 1 0 0 3
      
      2 0 1 3 0 4 0 0
      
      4 1 3 4 0 2 2 1
    

 * Level 2: Blocks may require multiple hits to break (randomized from 1 to 3). The ball speed would increase from level 1.


     2 1 1 3 2 4 1 2
   
     0 1 0 4 1 2 2 1

     4 2 1 0 1 3 2 1

     1 2 0 2 0 3 1 2

     1 2 3 0 0 3 0 1


 * Level 3: There will be some indestructible blocks that need to be maneuvered around. The paddle size would reduce from the previous levels.

   

     5 1 0 1 1 3 3 5
   
     5 0 1 4 5 0 3 1
   
     4 1 2 5 1 0 2 2
   
     3 5 4 2 0 1 5 1

     5 1 0 5 0 3 2 4

## Class Ideas

 * Paddle Class: handleKeyPress()

 * Ball Class: move(), checkCollisionPaddle()

 * Block Class: checkCollisionBlock(), strength(), createBlocks()

 * Power Up: applyPaddle(), applyBallSize(), applyBallSpeed()

