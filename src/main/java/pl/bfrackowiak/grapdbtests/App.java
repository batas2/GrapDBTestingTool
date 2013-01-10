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

        List<ScenarioCommand> scenario = scenarioGenerator.getScenario(2000);

        GraphDAO graphDAO = new Neo4JImp();
        ScenarioExecutor scenarioExecutor = new ScenarioExecutor(scenario);
        
        long start = System.currentTimeMillis();
        
        scenarioExecutor.Execute(graphDAO);

        long end = System.currentTimeMillis();

        System.out.println("Execution time was " + (end - start) + " ms.");
    }
}
