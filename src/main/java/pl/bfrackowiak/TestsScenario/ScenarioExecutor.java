package pl.bfrackowiak.TestsScenario;

import java.util.List;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;
import pl.bfrackowiak.grapdbtests.GraphDAO;

/**
 * @author Bartosz Frackowiak http://bfrackowiak.pl/
 */
public class ScenarioExecutor {

    public long Execute(List<ScenarioCommand> scenario, GraphDAO graphDAO) {
        graphDAO.init();

        long start = System.currentTimeMillis();

        for (ScenarioCommand command : scenario) {
            command.Execute(graphDAO);
        }

        long end = System.currentTimeMillis();

        graphDAO.dispose();

        return end - start;
    }
}
