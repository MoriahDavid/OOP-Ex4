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
We used our classes from OOP-Ex2 and we add package game with this classes:`

Agent - Represents the agent that collect the pokemon on the route.
Pokemon - Represents the pokemons
Info- represents the game server info. We have on this class the time for end and the servers parameters.
Manager- on this class main algorithm that plans in what way the agents collect the pokemons.
GameDraw- Displays the draw of the graph, the agents and the pokemons. 

## Our algorithm:
The algorithm split to two ways:
* When we have one agent: the agent will go to the closest pokemon and collect it. We check which pokemon closer by checking the shortest path from the agent to each pokemons (src node of the pokemon edge).
* When we have more than one agent: 

## Results:
## Gui:
## Tests:
## Diagram:
