## <p align="center"> :heavy_minus_sign: :white_small_square: OUR GAME :white_small_square: :heavy_minus_sign: <br />
<br />

![pngaaa com-14402](https://user-images.githubusercontent.com/93945532/148700984-96867c93-8ba1-4d0e-9661-adb1c4edcb98.png)


 <img align="right" width="250" src="https://user-images.githubusercontent.com/93945532/148700704-633b6232-4714-4fda-8488-8f30b16b1b02.png">
 <img align="right" width="250" src="https://user-images.githubusercontent.com/93945532/148701348-131505d1-68ce-4543-8931-5d0b6e0d84b0.png">
 <img  align="left" width="250" src="https://user-images.githubusercontent.com/93945532/148700752-182e40ef-a31e-455f-8ff1-d6072129c8e3.jpg">

   
 <br />
   <br />
   <br />
   <br />
   <br />
   <br />
   <br />
   <br />
   <br />
  <br />
  <br />
  <br />

## :large_orange_diamond: Main Classes:
We used our classes from OOP-Ex2 and we add package game with this classes:

#### :black_small_square: Agent- Represents the agent that collect the pokemon on the route. <br />
#### :black_small_square: Pokemon - Represents the pokemons <br />
#### :black_small_square: Info- represents the game server info. We have on this class the time for end and the servers parameters. <br />
#### :black_small_square: Manager- on this class main algorithm that plans in what way the agents collect the pokemons. <br />
#### :black_small_square: GameDraw- Displays the draw of the graph, the agents and the pokemons. <br />

<br />

## :large_orange_diamond: Our Algorithm:
The algorithm split to two ways:
* **When we have one agent:** the agent will go to the closest pokemon and collect it. We check which pokemon closer by checking the shortest path from the agent to each pokemons (src node of the pokemon edge).
* **When we have more than one agent:** for each pokemon the we choose the closest agent for collection it. If the agent has last 5 more nodes to go, we also check if his closest from the others.

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
  11           |        1181      |      493        |
  12           |        40      |    163          |
  13           |        290      |      444        |
  14           |      249        |        228      |
  15           |   469           |       493       |
   
<br />

## :large_orange_diamond: Gui:
   For run the game we need run the server and then run the Ex4.jar ("java -jar Ex4.jar") Shuold run in the dir of the project so the resource file work well. <br />
   Up Edge- in blue. If it has a pokeon it's Bulbasaur. <br />
   Down Edge- in yellow. If it has a pokeon it's Pikachu. <br />
   When the Pokemon is classified into a list of one of the agents, its number turned from white into black. <br />

![image](https://user-images.githubusercontent.com/64011788/148699619-47473fd0-b904-4522-922b-f244dbdcb041.png)

![image](https://user-images.githubusercontent.com/64011788/148699649-9b365dec-2ab1-4d79-a3d3-265dbf578591.png)

<br />

## :large_orange_diamond: Tests:
We tests the main functions on the classes, we reused some of the tests from the Ex2.
We run all the game levels and check if the algorithm is right. 
<br />

## :large_orange_diamond: Diagram:

<br />

![image](https://user-images.githubusercontent.com/64011788/148699867-ae78767a-ba4b-461b-a26d-a1e92a334371.png)

