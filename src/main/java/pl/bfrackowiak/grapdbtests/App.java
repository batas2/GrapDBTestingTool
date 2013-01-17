package pl.bfrackowiak.grapdbtests;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.jgrapht.Graph;
import pl.bfrackowiak.TestsScenario.Commands.CreateGraphCommand;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;
import pl.bfrackowiak.TestsScenario.InsertScenarioGenerator;
import pl.bfrackowiak.TestsScenario.RandomScenarioGenerator;
import pl.bfrackowiak.TestsScenario.ReadScenarioGenerator;
import pl.bfrackowiak.TestsScenario.ScenarioExecutor;
import pl.bfrackowiak.TestsScenario.ScenarioGenerator;

/**
 * @author Bartosz Frackowiak http://bfrackowiak.pl/
 */
public class App {

    public static final int MIN_SCENARIO_LENGTH = 10;
    public static final int MAX_SCENARIO_LENGTH = 101;
    public static final int SCENARIO_STEP = 50;
    public static final int VERTEX_COUNT = 1000;
    public static final int EDGE_COUNT = 2000;

    private static void RunScenario(ScenarioGenerator scenarioGenerator, String file) throws IOException {

        Graph<VertexModel, WeightedEdge> graph = GraphFactory.getRandomGrap(VERTEX_COUNT, EDGE_COUNT);

        PrintWriter out = new PrintWriter(new FileWriter(file));

        for (int scenarioLength = MIN_SCENARIO_LENGTH; scenarioLength < MAX_SCENARIO_LENGTH; scenarioLength += SCENARIO_STEP) {
            ScenarioExecutor scenarioExecutor = new ScenarioExecutor();
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
            RunScenario(new RandomScenarioGenerator(), "random_scenario.csv");
            RunScenario(new InsertScenarioGenerator(), "insert_scenario.csv");
            RunScenario(new ReadScenarioGenerator(), "read_scenario.csv");
        } catch (IOException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
}
