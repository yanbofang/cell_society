package cellsociety_team16;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Class for the Graph of Populations
 * @author Yanbo Fang
 *
 */
public class PopulationGraph {

	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private Double currentX;
	private LineChart<Number, Number> lineChart;
	private XYChart.Series emptySeries;
	private XYChart.Series inactiveSeries;
	private XYChart.Series activeSeries;

	/**
	 * Construcotor for PopulationGraph
	 * @param model
	 */
	public PopulationGraph(SimulationModel model) {
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		currentX = 0.0;
		lineChart = new LineChart<Number, Number>(xAxis, yAxis);
	}

	/**
	 * Create the population graph
	 * @return
	 */
	public LineChart<Number, Number> createPopulationGraph() {
		emptySeries = new XYChart.Series<>();
		inactiveSeries = new XYChart.Series();
		activeSeries = new XYChart.Series();

		lineChart.getData().add(emptySeries);
		lineChart.getData().add(inactiveSeries);
		lineChart.getData().add(activeSeries);

		return lineChart;
	}

	/**
	 * Get the current X-axis value
	 * @return
	 */
	public double getCurrentX() {
		return this.currentX;
	}

	/**
	 * Update the graph, pass in a simulationModel and a double for xAxix value
	 * @param mySimulationModel
	 * @param x
	 */
	public void updateGraph(SimulationModel mySimulationModel, Double x) {
		currentX = x;
		emptySeries.getData().add(new XYChart.Data<>(currentX, mySimulationModel.getCounts().get(0)));
		inactiveSeries.getData().add(new XYChart.Data<>(currentX, mySimulationModel.getCounts().get(1)));
		activeSeries.getData().add(new XYChart.Data<>(currentX, mySimulationModel.getCounts().get(2)));

	}

}
