package pl.bfrackowiak.TestsScenario.Commands;

import pl.bfrackowiak.grapdbtests.GraphDAO;

/**
 * @author Bartosz Frackowiak
 * http://bfrackowiak.pl/
 */
public interface ScenarioCommand {

    void Execute(GraphDAO graphDAO);
}
