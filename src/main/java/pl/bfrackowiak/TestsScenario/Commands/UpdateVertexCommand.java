package pl.bfrackowiak.TestsScenario.Commands;

import pl.bfrackowiak.grapdbtests.GraphDAO;
import pl.bfrackowiak.grapdbtests.VertexModel;

/**
 * @author Bartosz Frackowiak
 * http://bfrackowiak.pl/
 */
public class UpdateVertexCommand implements ScenarioCommand {

    private VertexModel vertexModel;
    private VertexModel newVal;

    public UpdateVertexCommand(VertexModel vertexModel, VertexModel newVal) {
        this.vertexModel = vertexModel;
        this.newVal = newVal;
    }

    public VertexModel getVertexModel() {
        return vertexModel;
    }

    public VertexModel getNewVal() {
        return newVal;
    }

    @Override
    public void Execute(GraphDAO graphDAO) {
        graphDAO.updateVertex(vertexModel, newVal);
    }

    @Override
    public String toString() {
        return "UpdateVertexCommand{" + "vertexModel=" + vertexModel + ", newVal=" + newVal + '}';
    }
}
