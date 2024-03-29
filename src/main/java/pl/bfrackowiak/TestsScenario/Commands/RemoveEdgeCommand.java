package pl.bfrackowiak.TestsScenario.Commands;

import pl.bfrackowiak.grapdbtests.GraphDAO;
import pl.bfrackowiak.grapdbtests.VertexModel;

/**
 * @author Bartosz Frackowiak
 * http://bfrackowiak.pl/
 */
public class RemoveEdgeCommand implements ScenarioCommand {

    private VertexModel vertexFrom;
    private VertexModel vertexTo;

    public RemoveEdgeCommand(VertexModel from, VertexModel to) {
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
        graphDAO.removeEdge(vertexFrom, vertexTo);
    }

    @Override
    public String toString() {
        return "RemoveEdgeCommand{" + "vertexFrom=" + vertexFrom + ", vertexTo=" + vertexTo + '}';
    }
}
