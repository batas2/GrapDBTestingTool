package pl.bfrackowiak.TestsScenario;

import java.util.List;
import org.jgrapht.Graph;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;
import pl.bfrackowiak.grapdbtests.VertexModel;
import pl.bfrackowiak.grapdbtests.WeightedEdge;

/**
 * @author Bartosz Frackowiak http://bfrackowiak.pl/
 */
public interface ScenarioGenerator {

    List<ScenarioCommand> getScenario(Graph<VertexModel, WeightedEdge> graph, int length);

    VertexModel getVertex();

    WeightedEdge getEdge();
}
