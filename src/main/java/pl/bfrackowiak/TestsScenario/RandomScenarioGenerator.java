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
 * @author Bartosz Frackowiak http://bfrackowiak.pl/
 */
public class RandomScenarioGenerator implements ScenarioGenerator {

    private Random rand;
    private Graph<VertexModel, WeightedEdge> graph;

    public RandomScenarioGenerator() {
        rand = new Random();
    }

    @Override
    public List<ScenarioCommand> getScenario(Graph<VertexModel, WeightedEdge> graph, int length) {
        this.graph = graph;
        ScenarioGraphCRUD crud = new ScenarioGraphCRUD(graph, this);
        List<ScenarioCommand> scenario = new LinkedList<ScenarioCommand>();

        scenario.add(new CreateGraphCommand(graph));

        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(6);
            switch (index) {
                case 0:
                    scenario.add(crud.getCreateEdgeCommand());
                    break;
                case 1:
                    scenario.add(crud.getDeleteEdgeCommand());
                    break;
                case 2:
                    scenario.addAll(crud.getDeleteVertexCommand());
                    break;
                case 3:
                    scenario.add(crud.getUpdateVertexCommand());
                    break;
                case 4:
                    scenario.add(crud.getReadVertexCommand());
                    break;
                case 5:
                    scenario.add(crud.getCreateVertexCommand());
                    break;
            }
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
