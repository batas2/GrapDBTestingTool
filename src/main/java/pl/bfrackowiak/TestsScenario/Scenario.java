package pl.bfrackowiak.TestsScenario;

import java.util.List;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;

/**
 *
 * @author Bartosz
 */
public interface Scenario {

    List<ScenarioCommand> getScenario(int length);
}
