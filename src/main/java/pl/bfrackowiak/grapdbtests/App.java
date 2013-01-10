package pl.bfrackowiak.grapdbtests;

import java.util.List;
import org.jgrapht.Graph;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;
import pl.bfrackowiak.TestsScenario.RandomScenarioGenerator;
import pl.bfrackowiak.TestsScenario.ScenarioExecutor;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        Graph graph = GraphFactory.getRandomGrap(20, 40);
        RandomScenarioGenerator scenarioGenerator = new RandomScenarioGenerator(graph);

        List<ScenarioCommand> scenario = scenarioGenerator.getScenario(200);
        ScenarioExecutor scenarioExecutor = new ScenarioExecutor(scenario);


        GraphDAO neo4j = new Neo4JImp();

        GraphDAO postgres = new PostgresSQLImp();
        postgres.init();

        long start = System.currentTimeMillis();

        scenarioExecutor.Execute(postgres);

        long end = System.currentTimeMillis();

        System.out.println("PostgresSQL Execution time was " + (end - start) + " ms.");

        start = System.currentTimeMillis();

        scenarioExecutor.Execute(neo4j);

        end = System.currentTimeMillis();

        System.out.println("Neo4j Execution time was " + (end - start) + " ms.");

    }
}
