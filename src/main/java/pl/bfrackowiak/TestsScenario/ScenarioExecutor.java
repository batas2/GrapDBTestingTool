/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.TestsScenario;

import java.util.List;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;
import pl.bfrackowiak.grapdbtests.GraphDAO;

/**
 *
 * @author Bartosz
 */
public class ScenarioExecutor {

    private List<ScenarioCommand> scenario;

    public ScenarioExecutor(List<ScenarioCommand> scenario) {
        this.scenario = scenario;
    }

    public long Execute(GraphDAO graphDAO) {
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
