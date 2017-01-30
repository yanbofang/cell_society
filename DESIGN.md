- Design Details
	1. Reillustration of all Classes:
		- Grid: A class contains an ArrayList of Containers. Current Grid is linked to the Grid of next round by method .next(). It will check each cell in the order of Priority.(Priority is an Instance Variable of Cell class). 
			1. Specifically, all cells that can eat or destroy other cells (which we call Active) are considered as Priority 1, e.g. Shark, Fire. This kind of cell needs to be checked first because they can change the status of other cell before other cell's action.	
			2. All cells that can move (which we call Passive) are considered as Prioirity 2, e.g. Fish, Group of people, because they can occupy empty cell and change their status. 
			3. Lastly, all cells that can't move, e.g. Empty Cell, are considered as Prioirity 3 (which we call Forced), e.g. Blank Cell.
			4. In each round of check, it will call the Containers.containCell().statusUpdate method to update the cell contained in the next round.
			5. This generalization is good because only high prioirity cell can change the status of low prioirity cell. When new kind of cell is added, we only need to determine the priority level of this new cell and maintian the overall structure.
		- Container: A class contains different kind of cell. It can access part of informaiton from the Grid which contains it and access the cell it contains. Its spatial relationship with other containers has been set before the first round and been copied over to next round during the running. The advantage of this design is that if we encounter irregular shape cell, we can easily modify the program by updating this relationship for each cell without interrupting other parts. The important methods are:
			1. .neighbors(): Return an ArrayList<> of Containers 'next to' it.
			2. .containCells(): Return the Cell it contains.
			3. .nextRound(): Return the Container at the same location in the next round.
			4. .locked(): Set the status of the container to be locked. It means no one can update this cell in the future.
			5. .setCells(): set the cell it contains and link the cell back to itself.
		- Cell: An abstract class which represents each species. 
		- Fish: A class that can move, multiply and die.
		- Shark: A class that can move, multiply, die and eat.
		- People: A class that can move.
		- Fire: A class that can move, eat and die.
	- Apply the rules to a middle cell: set the next state of a cell to dead by counting its numver of neighbors using the Game of Life rules for a cell in the middle:
		1. Call Grid().startNewRoundSimulation()
		2. for-loop visits all the containers in priority orders
		3. When you reach the specific container which contains the specific cell you asked for, you will call thisContainer.containCells().ruleCheck().
		4. In the ruleCheck(), it will call this.myContainer().neighbors() to get a return of an ArrayList<Containers> which contains all the containers around it.
		5. It will count the number of live neighbors inside the ArrayList of container. If it contains fewer than two or more than three live cells. it will call this.nextRound().setCells(Dead) to make the same position at next round contain a dead cell.
		6. Then it will call .locked() to lock the position for this round and next round. Because the status of cell at next round at this position has already been set, no one can modify it.  