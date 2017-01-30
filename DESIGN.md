- Introduction
This program provides users the ability to perform a variety of Cellular Automata (CA) simulations including Conway’s Game of Life, Schelling's model of segregation, Wa-Tor World model of predator-prey relationships, Spreading of Fire and so on. The primary design goal is to create a flexible simulation tool that can handle different CA models without the need to make significant changes in our program. Extensions can be made to model new simulations and interactions without modifying how the game updates or the user interface. The subclasses are open and more can be added/extended from the abstract class, while the superclass, general backend, and user interface setup are closed.

- User Interface
The user will be able to interact with the program in several ways:  
1. Switch between different simulation models using a drop-down menu, which will then give the user the option to modify parameter specific to the simulation
2. Set up a variety of parameters such as the number of rows/columns, the percentage of different groups of cells in the grid, and the speed the animation refreshes at/the rate of the program updating the state of the cells
Errors will be reported to the user through pop-up window detailing the problem, for example if they enter a wrong type of input. Default values will be assigned for unmodified fields.
3. Play, Pause, Reset or Step a simulation using buttons


- Design Considerations
There may be some overlap in the way cells interact with one another in their respective simulations. For instance, both the predator/shark type and the fire type can find preys and trees, respectively, amongst their neighbors and replace their types. This may lead to some duplicate code in the predator/shark and fire subclasses depending on the generality. Previously we discussed how we could create subclasses of Cell based on their behaviour. For example, the fire and the shark can both be incorporated in a Predator class. However, we thought that there would be many different rules regarding shark and fire, and putting them in one class would result in a very large and messy class. Furthermore, there was overlap between subclasses that made it difficult to figure out how to divide them up: both the fish and the shark types can change an empty cell to their current type, i.e. “move”, although fire cannot; but both the fire and shark types can change a tree or fish cell, respectively, to their current type, i.e. “burning” or “eating” their neighbors. It was difficult to decide whether to group fish and shark or shark and fire into an overarching class due to their overlapping abilities.

![UI Design](images/CellSocietyUIDesign.jpg?raw=true)

- Team Responsibilities
Primary responsibilities of each team member:
Xingyu Chen: Work on backend classes, and implement the model of segregation.
Kris Elbert: Implement the Wa-Tor World model of predator-prey relationships, and work on the user interface, interactions, etc.
Yanbo Fang: Implement the Conway’s Game of Life and the Spreading of Fire simulations, and work on the user interface, interactions, etc.

A High Level Plan: 

