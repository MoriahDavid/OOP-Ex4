# :heavy_minus_sign: :white_small_square: POKEMON GAME :white_small_square: :heavy_minus_sign: <br />


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

<br />

## :large_orange_diamond: Main Classes:
We used our classes from OOP-Ex2 and we add package game with this classes:

Agent - Represents the agent that collect the pokemon on the route. <br />
Pokemon - Represents the pokemons <br />
Info- represents the game server info. We have on this class the time for end and the servers parameters. <br />
Manager- on this class main algorithm that plans in what way the agents collect the pokemons. <br />
GameDraw- Displays the draw of the graph, the agents and the pokemons. <br />

<br />

## :large_orange_diamond: Our Algorithm:
The algorithm split to two ways:
* **When we have one agent:** the agent will go to the closest pokemon and collect it. We check which pokemon closer by checking the shortest path from the agent to each pokemons (src node of the pokemon edge).
* **When we have more than one agent:** 

<br />

## :large_orange_diamond: Results:
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
   
<br />

## :large_orange_diamond: Gui:

![image](https://user-images.githubusercontent.com/64011788/148699619-47473fd0-b904-4522-922b-f244dbdcb041.png)

![image](https://user-images.githubusercontent.com/64011788/148699649-9b365dec-2ab1-4d79-a3d3-265dbf578591.png)

<br />

## :large_orange_diamond: Tests:

<br />

## :large_orange_diamond: Diagram:

<br />

![image](https://user-images.githubusercontent.com/64011788/148699867-ae78767a-ba4b-461b-a26d-a1e92a334371.png)

