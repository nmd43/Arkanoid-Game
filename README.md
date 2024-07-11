# Breakout
## Nikita Daga


DO NO FORK THIS REPOSITORY, clone it directly to your computer.


This project implements the game of Breakout with multiple levels.

### Timeline

 * Start Date: 16th January 2024

 * Finish Date: 24th January 2024

 * Hours Spent: 25 hours



### Attributions

 * Resources used for learning (including AI assistance)
   * ChatGPT
   * https://horstmann.com/corejava/corejava_11ed-bonuschapter13-javafx.pdf
   * OpenJFX Overview Slides
 


### Running the Program

 * Main class:



 * Data files needed: level1_blocks, level2_blocks, level3_blocks


 * Key/Mouse inputs: Left and Right arrow key for movement, space bar for navigation between pages as displayed.


 * Cheat keys: L adds a life, R resets paddle and ball position, (1,2,3) navigate between levels, and S shows the instructions


### Notes/Assumptions

 * Assumptions or Simplifications: N/A


 * Known Bugs: After moving scenes from Level 1, the ball speed increases despite not asking it to do so. 


 * Features implemented: All features in the core and extension of the project functional specifications on the course website were implemented. All variations except BREAK 15C from the PLAN.MD are implemented however some modifications were also made (given below).



 * Features unimplemented:
   * BREAK 15C Exploding: When an exploding block is destroyed, it also destroys (or damages, in the case of Multi-Hit Blocks) neighboring blocks.


 * Modified Features: 
   * BREAK 17D S Updated: The instruction page pops up as a pause, however, it does not reset the game so users can pick up where they left off after seeing the rules.
   * In Level 2 Features: Update: Not randomized but set by color. Blocks giving 300 points need 2 hits.
   * In Level 3 Features: Update: No blocks are indestructible, rather those blocks giving 400 points need 3 hits. The paddle is not sped up since there is a cheat key to do this.


 * Noteworthy Features: 
   * Power-Ups are randomized and stay for about 15 seconds before reverting.
   * Level 1 does not have multi-hit blocks. Level 2 has some double hit blocks. Level 3 has some triple hit blocks.


### Assignment Impressions


