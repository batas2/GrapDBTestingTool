package pl.bfrackowiak.TestsScenario;

import java.util.List;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;

/**
 * @author Bartosz Frackowiak
 * http://bfrackowiak.pl/
 */
public interface Scenario {

    List<ScenarioCommand> getScenario(int length);
}
