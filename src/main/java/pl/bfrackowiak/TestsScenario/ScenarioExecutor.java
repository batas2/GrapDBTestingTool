package pl.bfrackowiak.TestsScenario;

import java.util.List;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;
import pl.bfrackowiak.grapdbtests.GraphDAO;

/**
 * @author Bartosz Frackowiak http://bfrackowiak.pl/
 */
public class ScenarioExecutor {

    public long Execute(List<ScenarioCommand> scenario, GraphDAO graphDAO) {

        long start = System.currentTimeMillis();

        graphDAO.init();
        
        for (ScenarioCommand command : scenario) {
            command.Execute(graphDAO);
        }

        graphDAO.dispose();

        long end = System.currentTimeMillis();

        return end - start;
    }
}
