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
public class ScenarioExecutor implements ScenarioCommand {
    
    private List<ScenarioCommand> scenario;
    
    public ScenarioExecutor(List<ScenarioCommand> scenario) {
        this.scenario = scenario;
    }
    
    @Override
    public void Execute(GraphDAO graphDAO) {
        for (ScenarioCommand command : scenario) {
            command.Execute(graphDAO);
        }
    }
}
