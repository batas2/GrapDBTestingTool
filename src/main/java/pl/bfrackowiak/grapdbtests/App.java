package pl.bfrackowiak.grapdbtests;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import pl.bfrackowiak.TestsScenario.Commands.CreateGraphCommand;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;
import pl.bfrackowiak.TestsScenario.RandomScenarioGenerator;
import pl.bfrackowiak.TestsScenario.ScenarioExecutor;

/**
 * @author Bartosz Frackowiak http://bfrackowiak.pl/
 */
public class App {

    public static final int MIN_SCENARIO_LENGTH = 500;
    public static final int MAX_SCENARIO_LENGTH = 5001;
    public static final int SCENARIO_STEP = 500;
    public static final int VERTEX_COUNT = 1000;
    public static final int EDGE_COUNT = 2000;

    private static void RunScenario() throws IOException {

        Graph<VertexModel, WeightedEdge> graph = GraphFactory.getRandomGrap(VERTEX_COUNT, EDGE_COUNT);
        RandomScenarioGenerator scenarioGenerator = new RandomScenarioGenerator();
        ScenarioExecutor scenarioExecutor = new ScenarioExecutor();

        PrintWriter out = new PrintWriter(new FileWriter("random_scenario.csv"));

        for (int scenarioLength = MIN_SCENARIO_LENGTH; scenarioLength < MAX_SCENARIO_LENGTH; scenarioLength += SCENARIO_STEP) {
            List<ScenarioCommand> scenario = scenarioGenerator.getScenario(CreateGraphCommand.cloneGraph(graph), scenarioLength);

            GraphDAO postgres = new PostgresSQLImp();
            long postgresTime = scenarioExecutor.Execute(scenario, postgres);

            GraphDAO neo4j = new Neo4JImp();
            long neo4jTime = scenarioExecutor.Execute(scenario, neo4j);

            GraphDAO titan = new TitanImp();
            long titanTime = scenarioExecutor.Execute(scenario, titan);

            out.println(scenarioLength + "," + postgresTime + "," + neo4jTime + "," + titanTime);
        }
        out.close();
    }

    public static void main(String[] args) {
        try {
            RunScenario();
        } catch (IOException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
}
