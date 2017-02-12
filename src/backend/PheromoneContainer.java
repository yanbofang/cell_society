package backend;

public class PheromoneContainer extends Container {
	private int foodPheromone=0;
	private int homePheromone=0;
	public int getFoodPheromone() {
		return foodPheromone;
	}
	public void setFoodPheromone(int foodPheromone) {
		this.foodPheromone = foodPheromone;
	}
	public int getHomePheromone() {
		return homePheromone;
	}
	public void setHomePheromone(int homePheromone) {
		this.homePheromone = homePheromone;
	}
}
