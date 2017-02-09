#Peer Code Review

Author: xc77(Xingyu Chen), yh129(Yuxiang He)

The first part of code I refactored is to extract several helper method to handle the duplicated code. In my code there are several appearances of 

```
curContainer.getNext().setCell(new Fire()); curContainer.getNext().setLocked(true); 
```
I extracted a method to curContainer class:

```
public void setNext(Cell a) {
	this.getNext().setCell(a);
	this.getNext().setLocked(true);
}
```
And call:

```
curContainer.setNext(new Fire());
```
The reason I chose it is because first this should be Container's personal decision on how to implement this. Also, putting it in every handler will create bunch of duplicated codes 

The second part of code I refactored is I extracted all magic values existing into a constant.

The reason I chose to refactor this part is because first magic values make us difficult to modify the program

The third part of code I refactored is I hide several implementing detail into private method. For example, I put the specific solution to each type cell into three private methods.

For example,

```
	private void solveForTree(Container curContainer) {
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		Predicate<String> function = s -> s.compareTo(FIRE) == 0;
		int fireCnt = this.numberLiveNeighbor(myNeighbor, function);
		boolean fired=false;
		if (fireCnt>0) {
			for (Container curNeighbor:myNeighbor) {
				if (curNeighbor.getMyCell().is(FIRE)) {
					boolean fireHappened = (Math.random() * 1.0)<fireProb;
					if (fireHappened) {
						curContainer.setNext(new Fire());
						fired=true;
						break;
					} 
				}
			}
		}
		
		if (!fired) {
			curContainer.setNext(curContainer.getMyCell());
		}
	}
```

The reason why I refactored this part is because the implementing detail should be hidden in the private method instead of showing to public.
