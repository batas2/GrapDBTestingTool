package pl.bfrackowiak.TestsScenario.Commands;

import pl.bfrackowiak.grapdbtests.GraphDAO;
import pl.bfrackowiak.grapdbtests.VertexModel;

/**
 * @author Bartosz Frackowiak
 * http://bfrackowiak.pl/
 */
public class ReadVertexCommand implements ScenarioCommand {

    private VertexModel vertexModel;

    public ReadVertexCommand(VertexModel vertexModel) {
        this.vertexModel = vertexModel;
    }

    public VertexModel getVertexModel() {
        return vertexModel;
    }

    @Override
    public void Execute(GraphDAO graphDAO) {
        graphDAO.readVertex(vertexModel);
    }

    @Override
    public String toString() {
        return "ReadVertexCommand{" + "vertexModel=" + vertexModel + '}';
    }
}
