# POKEMON GAME <br />


⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜  <br />
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛  <br />
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛💛  <br />
⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛💛💛  <br />
💛⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛💛💛💛  <br />
💛💛⬛⬜⬜⬜⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬛💛💛💛💛  <br />
💛💛💛⬛⬛⬛💛💛💛💛💛💛💛⬛⬛⬛💛💛💛💛💛  <br />
💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛  <br />
💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛⬛  <br />
💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛⬛💛  <br />
⬛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛💛⬛⬛💛💛  <br />
💛💛💛⬛💛💛💛💛💛💛💛💛💛💛⬛💛💛⬛💛💛⬛  <br />
💛💛⬛⬜⬛💛💛💛💛💛💛💛💛⬛⬜⬛💛⬛⬛⬛⬜  <br />
💛💛⬛⬛⬛💛💛💛💛💛💛💛💛⬛⬛⬛💛⬛⬜⬜⬜  <br />
💛💛⬛⬛⬛💛💛💛💛⬛💛💛💛⬛⬛⬛💛💛⬛⬜⬜  <br />
⬛⬛💛💛💛💛💛💛💛💛💛💛💛💛💛💛⬛⬛⬛⬜⬜  <br />
🍎🍎⬛💛💛⬛⬛⬛⬛⬛⬛⬛💛💛💛💛🍎🍎⬛⬛⬛  <br />
🍎🍎⬛💛💛💛⬛⬛⬜⬜⬛⬛💛💛💛⬛🍎🍎⬛💛💛  <br />
🍎🍎⬛💛💛💛⬛⬜⬜⬜⬜⬛💛💛💛⬛🍎🍎⬛💛💛  <br />
⬛⬛💛💛💛💛💛⬛⬜⬜⬜⬛💛💛💛⬛⬛⬛💛💛💛  <br />
💛💛💛💛💛💛💛💛⬛⬛⬛💛💛💛💛💛💛⬛💛💛💛  <br />

## Main classes:
We used our classes from OOP-Ex2 and we add package game with this classes:

Agent - Represents the agent that collect the pokemon on the route. <br />
Pokemon - Represents the pokemons <br />
Info- represents the game server info. We have on this class the time for end and the servers parameters. <br />
Manager- on this class main algorithm that plans in what way the agents collect the pokemons. <br />
GameDraw- Displays the draw of the graph, the agents and the pokemons. <br />

## Our algorithm:
The algorithm split to two ways:
* **When we have one agent:** the agent will go to the closest pokemon and collect it. We check which pokemon closer by checking the shortest path from the agent to each pokemons (src node of the pokemon edge).
* **When we have more than one agent:** 

## Results:
Level          | Grade        | Moves        |
-------------- | ------------ | -------------|
   0           |    164          |   196           |
   1           |       612       |  521            |
   2           |        305      |      233        |   
   3           |       1027       |   594           |
   4           |      366        |  248            |
   5           |       815       |   551           |   
   6           |         85     |      171        |
   7           |     437         |    478          |
   8           |       130       |   205           |
   9           |      585        |       515       |
  10           |      169        |       201       |
  11           |              |              |
  12           |        40      |    163          |
  13           |              |              |
  14           |              |              |
  15           |   469           |       493       |
   
## Gui:
## Tests:
## Diagram:
