package pl.bfrackowiak.TestsScenario.Commands;

import org.jgrapht.Graph;
import pl.bfrackowiak.grapdbtests.GraphDAO;
import pl.bfrackowiak.grapdbtests.VertexModel;
import pl.bfrackowiak.grapdbtests.WeightedEdge;

/**
 * @author Bartosz Frackowiak
 * http://bfrackowiak.pl/
 */
public class CreateGraphCommand implements ScenarioCommand {

    private Graph graph;

    public CreateGraphCommand(Graph<VertexModel, WeightedEdge> graph) {
        this.graph = graph;
    }

    @Override
    public void Execute(GraphDAO graphDAO) {
        graphDAO.create(graph);
    }
}
