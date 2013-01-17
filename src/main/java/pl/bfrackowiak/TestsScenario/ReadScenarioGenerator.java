/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.TestsScenario;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.jgrapht.Graph;
import pl.bfrackowiak.TestsScenario.Commands.CreateGraphCommand;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;
import pl.bfrackowiak.grapdbtests.VertexModel;
import pl.bfrackowiak.grapdbtests.WeightedEdge;

/**
 *
 * @author Bartosz
 */
public class ReadScenarioGenerator implements ScenarioGenerator {

    private Random rand;
    private Graph<VertexModel, WeightedEdge> graph;

    public ReadScenarioGenerator() {
        rand = new Random();
    }

    @Override
    public List<ScenarioCommand> getScenario(Graph<VertexModel, WeightedEdge> graph, int length) {
        this.graph = graph;
        ScenarioGraphCRUD crud = new ScenarioGraphCRUD(graph, this);
        List<ScenarioCommand> scenario = new LinkedList<ScenarioCommand>();

        scenario.add(new CreateGraphCommand(graph));
        for (int i = 0; i < length; i++) {
            scenario.add(crud.getReadVertexCommand());
            scenario.add(crud.getReadVertexCommand());

        }
        return scenario;
    }

    @Override
    public WeightedEdge getEdge() {
        int edgeCount = graph.edgeSet().size();
        int edgeIndex = rand.nextInt(edgeCount);

        Object[] edges = graph.edgeSet().toArray();
        return (WeightedEdge) edges[edgeIndex];
    }

    @Override
    public VertexModel getVertex() {
        int graphSize = graph.vertexSet().size();
        int vertexIndex = rand.nextInt(graphSize);

        Object[] vertexModels = graph.vertexSet().toArray();
        return (VertexModel) vertexModels[vertexIndex];
    }
}
