package pl.bfrackowiak.grapdbtests;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import org.jgrapht.Graph;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;
import pl.bfrackowiak.TestsScenario.RandomScenarioGenerator;
import pl.bfrackowiak.TestsScenario.ScenarioExecutor;

/**
 * @author Bartosz Frackowiak http://bfrackowiak.pl/
 */
public class App {

    public static final int MIN_SCENARIO_LENGTH = 500;
    public static final int MAX_SCENARIO_LENGTH = 5000;
    public static final int SCENARIO_STEP = 500;
    public static final int VERTEX_COUNT = 1000;
    public static final int EDGE_COUNT = 2000;

    private static void RandomScenario() {

        Graph graph = GraphFactory.getRandomGrap(VERTEX_COUNT, EDGE_COUNT);
        RandomScenarioGenerator scenarioGenerator = new RandomScenarioGenerator(graph);
        ScenarioExecutor scenarioExecutor = new ScenarioExecutor();

        try {
            PrintWriter postgresOut = new PrintWriter(new FileWriter("postgres.out"));
            PrintWriter neo4jOut = new PrintWriter(new FileWriter("neo4j.out"));
            PrintWriter titanOut = new PrintWriter(new FileWriter("titan.out"));

            for (int scenarioLength = MIN_SCENARIO_LENGTH; scenarioLength < MAX_SCENARIO_LENGTH; scenarioLength += SCENARIO_STEP) {
                List<ScenarioCommand> scenario = scenarioGenerator.getScenario(scenarioLength);

                GraphDAO postgres = new PostgresSQLImp();
                long postgresTime = scenarioExecutor.Execute(scenario, postgres);
                postgresOut.println(scenarioLength + ";" + postgresTime);

                GraphDAO neo4j = new Neo4JImp();
                long neo4jTime = scenarioExecutor.Execute(scenario, neo4j);
                neo4jOut.println(scenarioLength + ";" + postgresTime);

                GraphDAO titan = new TitanImp();
                long titanTime = scenarioExecutor.Execute(scenario, titan);
                titanOut.println(scenarioLength + ";" + postgresTime);
            }
            postgresOut.close();
            neo4jOut.close();
            titanOut.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        RandomScenario();
//        Graph graph = GraphFactory.getRandomGrap(4, 8);
//
//        RandomScenarioGenerator scenarioGenerator = new RandomScenarioGenerator(graph);
//
//        List<ScenarioCommand> scenario = scenarioGenerator.getScenario(20);
//        ScenarioExecutor scenarioExecutor = new ScenarioExecutor();
//
//        GraphDAO postgres = new PostgresSQLImp();
//        long postgresTime = scenarioExecutor.Execute(scenario, postgres);
//        System.out.println("PostgresSQL Execution time was " + postgresTime + " ms.");
//
//        GraphDAO neo4j = new Neo4JImp();
//        long neo4jTime = scenarioExecutor.Execute(scenario, neo4j);
//        System.out.println("Neo4j Execution time was " + neo4jTime + " ms.");
//
//        GraphDAO titan = new TitanImp();
//        long titanTime = scenarioExecutor.Execute(scenario, titan);
//        System.out.println("Titan Execution time was " + titanTime + " ms.");
    }
}
