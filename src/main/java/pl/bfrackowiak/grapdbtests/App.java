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

        Graph graph = GraphFactory.getRandomGrap(4, 8);

        RandomScenarioGenerator scenarioGenerator = new RandomScenarioGenerator(graph);

        List<ScenarioCommand> scenario = scenarioGenerator.getScenario(20);
        ScenarioExecutor scenarioExecutor = new ScenarioExecutor(scenario);

//        GraphDAO postgres = new PostgresSQLImp();
//        long postgresTime = scenarioExecutor.Execute(postgres);
//        System.out.println("PostgresSQL Execution time was " + postgresTime + " ms.");
//
//        GraphDAO neo4j = new Neo4JImp();
//        long neo4jTime = scenarioExecutor.Execute(neo4j);
//        System.out.println("Neo4j Execution time was " + neo4jTime + " ms.");

        GraphDAO titan = new TitanImp();
        long titanTime = scenarioExecutor.Execute(titan);
        System.out.println("Titan Execution time was " + titanTime + " ms.");
    }
}
