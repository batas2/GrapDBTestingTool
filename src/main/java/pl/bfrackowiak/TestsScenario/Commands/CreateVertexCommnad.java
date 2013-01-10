/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.TestsScenario.Commands;

import pl.bfrackowiak.grapdbtests.GraphDAO;
import pl.bfrackowiak.grapdbtests.VertexModel;

/**
 *
 * @author Bartosz
 */
public class CreateVertexCommnad implements ScenarioCommand {

    private VertexModel vertexModel;

    public CreateVertexCommnad(VertexModel vertexModel) {
        this.vertexModel = vertexModel;
    }

    public VertexModel getVertexModel() {
        return vertexModel;
    }

    @Override
    public void Execute(GraphDAO graphDAO) {
        graphDAO.createVertex(vertexModel);
    }

    @Override
    public String toString() {
        return "CreateVertexCommnad{" + "vertexModel=" + vertexModel + '}';
    }
}
