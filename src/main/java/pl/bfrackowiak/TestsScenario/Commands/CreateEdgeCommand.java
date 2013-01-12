package pl.bfrackowiak.TestsScenario.Commands;

import pl.bfrackowiak.grapdbtests.GraphDAO;
import pl.bfrackowiak.grapdbtests.VertexModel;

/**
 * @author Bartosz Frackowiak
 * http://bfrackowiak.pl/
 */
public class CreateEdgeCommand implements ScenarioCommand {

    private VertexModel vertexFrom;
    private VertexModel vertexTo;

    public CreateEdgeCommand(VertexModel from, VertexModel to) {
        vertexFrom = from;
        vertexTo = to;
    }

    public VertexModel getVertexFrom() {
        return vertexFrom;
    }

    public VertexModel getVertexTo() {
        return vertexTo;
    }

    @Override
    public void Execute(GraphDAO graphDAO) {
        graphDAO.createEdge(vertexFrom, vertexTo);
    }

    @Override
    public String toString() {
        return "CreateEdgeCommand{" + "vertexFrom=" + vertexFrom + ", vertexTo=" + vertexTo + '}';
    }
}
