- Overview



	The program includes these classes:
	- CellSocietyMain - Controls UI and display
	- Simulation - Coordinates the next following class and return the status of next round
	- Grid - Contains a list of Container. Iterates through each Container and updates the cell within.
	- Cell - Abstract Class
	- Fish, Shark, Tree, etc. Inherit Cell. They are added specifically according to each simulation.

	Running Procedure:
	
	1. In the program, we have a main class, which controls all the scene control and User Interface. This main class passes parameters to the Simulation class and call Simulation class each time UI needs to update.
	2. Simulation Class create a new Grid to hold the status of next round, connect the current Grid to the new Grid and connect each container of the current Grid to each container of the next Grid. Then call Grid.startNewRoundSimulation()
	3. Grid Class will iterate through each Container which belongs to the Grid in the high-to-low priority order. Call Container.containCell().ruleCheck() and let each cell handles the rule check and update.
	4. Each cell will update the future state of itself and modify the future state of other containers if needed and permitted. (Each cell will be assigned with a priority. High prioirty can modify low priority cell).
	5. Each cell will lock the future state. So if the future state of the current container has been set, we are not allowed to do any change to it and we will skip it in the iteration.
	




- Design Details
	1. Reillustration of all Classes:
		- Grid: A class contains an ArrayList of Containers. Current Grid is linked to the Grid of next round by method .next(). It will check each cell in the order of Priority.(Priority is an Instance Variable of Cell class). 
			1. Specifically, all cells that can eat or destroy other cells (which we call Active) are considered as Priority 1, e.g. Shark, Fire. This kind of cell needs to be checked first because they can change the status of other cell before other cell's action.	
			2. All cells that can move (which we call Passive) are considered as Prioirity 2, e.g. Fish, Group of people, because they can occupy empty cell and change their status. 
			3. Lastly, all cells that can't move, e.g. Empty Cell, are considered as Prioirity 3 (which we call Forced), e.g. Blank Cell.
			4. In each round of check, it will call the currentContainers.containCell().statusUpdate method to update the cell contained in the next round.
			5. This generalization is good because only high prioirity cell can change the status of low prioirity cell. When new kind of cell is added, we only need to determine the priority level of this new cell and maintian the overall structure.
		- Container: A class contains different kind of cell. It can access part of informaiton from the Grid which contains it and access the cell it contains. Its spatial relationship with other containers has been set before the first round and been copied over to next round during the running. The advantage of this design is that if we encounter irregular shape cell, we can easily modify the program by updating this relationship for each cell without interrupting other parts. The important methods are:
			1. .neighbors(): Return an ArrayList<> of Containers 'next to' it.
			2. .containCells(): Return the Cell it contains.
			3. .nextRound(): Return the Container at the same location in the next round.
			4. .locked(): Set the status of the container to be locked. It means no one can update this cell in the future.
			5. .setCells(): set the cell it contains and link the cell back to itself.
		- Cell: An abstract class which represents each species.
			- It contains a key abstract method called .ruleCheck() which is implemented by each inheritor. 
		- Fish: A class that can move, multiply and die.
			Its .ruleCheck() checks: 
			1. if it has survived for a time and there is empty space nearby, then multiply 
			2. if there is empty space nearby, then move.
		- Shark: A class that can move, multiply, die and eat.
			Its .ruleCheck() checks():
			1. if there is a fish nearby, eat it 
			2. if it has survived for a time and there is empty space nearby, then multiply 
			3. if there is empty space nearby, then move.
		- People: A class that can move.
			1. If the neighbors nearby are not satisfying, move to a random empty block.
		- Fire: A class that can move, eat and die.
			1. If the neighbors near it has a tree, under certain probability, fire it. 
			2. Set the future of the current cell's container to contain an Empty cell
		- Empty Cell: A class that can only be edited passibely.
			1. If the empty cell's future hasn't been set yet, set the empty cell's container's future to contain an empty cell.
	2. Image of Relationship between classes
		<img src="https://coursework.cs.duke.edu/CompSci308_2017Spring/cellsociety_team16/blob/3bcbff25cc190248889c49582f83d55a1ea91910/Relationship.png" width="500" height="400">
	3. Image of Inheritance structure between classes
		![Inheritance Structure between classes](https://coursework.cs.duke.edu/CompSci308_2017Spring/cellsociety_team16/blob/3bcbff25cc190248889c49582f83d55a1ea91910/Relationship.png)
	2. Apply the rules to a middle cell: set the next state of a cell to dead by counting its numver of neighbors using the Game of Life rules for a cell in the middle:
		1. Call Grid().startNewRoundSimulation()
		2. for-loop visits all the containers in priority orders
		3. When you reach the specific container which contains the specific cell you asked for, you will call thisContainer.containCells().ruleCheck().
		4. In the ruleCheck(), it will call this.myContainer().neighbors() to get a return of an ArrayList<Containers> which contains all the containers around it.
		5. It will count the number of live neighbors inside the ArrayList of container. If it contains fewer than two or more than three live cells. it will call this.nextRound().setCells(Dead) to make the same position at next round contain a dead cell.
		6. Then it will call .locked() to lock the position for this round and next round. Because the status of cell at next round at this position has already been set, no one can modify it.  
	3. Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
		- The edge cell case is easy under our current design, because we should have already set the correct neighbors for the container that contains this cell. 
	4. Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
		1. This should have been handled in Grid() class. It will firstly iterate through all cells of Priority 1 by calling currentCell.ruleCheck(). Let me make an example using Sharks. If the shark eats fish, update the currentCell.getContainers().nextRound().setCells(Empty) and currentNeighbors.getContainers().nextRound().setCells(currentCell). Then lock both containers.
		2. It will secondly iterate through all cells of Priority 2 whose future haven't been set by calling currentCell.ruleCheck(). It will act based on the rult set inside the class. And lock the future container accordingly.
		3. It will thirdly iterate through all cells of Priority 3 and keep going until all cells of the future grid are filled and locked.
		4. Then we set the future Grid to be current Grid, create a new Grid() as the new future container and link all the cells of the current Grid with the cells of the future Grid. Make sure the nextRound() method work and the spatial relationship between containers copied over. 
		5. Then we can start a new simulation and keep going.
	5. Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
		1. Load the XML file and get the specific parameter
		2. When constructing Fire class, it should pass the parameter probCatch to the Fire instantiation so that it can update the rule check function using this value.
	6. Switch simulations: use the GUI to change the current simulation from Game of Life to Wator
		1. Get the input of which simulation should be ran next and stop the current Scene and step function from running.
		2. In the Scene setup() method, it should create a new scene with the parameters given by the user. Then read from preset Constant which class will we be using. 
		3. Fill the first Grid with random classes and display them. 
		4. In step(), it should call Grid.startNewRoundSimulation() and keeps going.