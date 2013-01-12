package pl.bfrackowiak.TestsScenario;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import pl.bfrackowiak.TestsScenario.Commands.CreateEdgeCommand;
import pl.bfrackowiak.TestsScenario.Commands.CreateGraphCommand;
import pl.bfrackowiak.TestsScenario.Commands.CreateVertexCommnad;
import pl.bfrackowiak.TestsScenario.Commands.ReadVertexCommand;
import pl.bfrackowiak.TestsScenario.Commands.RemoveEdgeCommand;
import pl.bfrackowiak.TestsScenario.Commands.RemoveVertexCommand;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;
import pl.bfrackowiak.TestsScenario.Commands.UpdateVertexCommand;
import pl.bfrackowiak.grapdbtests.VertexModel;

/**
 *
 * @author Bartosz
 */
public class RandomScenarioGenerator implements Scenario {

    private Random rand;
    private Graph graph;
    private int startVertexSetSize;
    private int startEdgeSetSize;
    private int vertexId;

    public RandomScenarioGenerator(Graph<VertexModel, DefaultEdge> graph) {
        this.graph = graph;
        rand = new Random();

        startVertexSetSize = graph.vertexSet().size();
        startEdgeSetSize = graph.edgeSet().size();
        vertexId = startVertexSetSize;
    }

    @Override
    public List<ScenarioCommand> getScenario(int length) {
        List<ScenarioCommand> scenario = new LinkedList<ScenarioCommand>();

        scenario.add(new CreateGraphCommand(graph));

        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(6);
            switch (index) {
                case 0:
                    scenario.add(getCreateEdgeCommand());
                    break;
                case 1:
                    scenario.add(getDeleteEdgeCommand());
                    break;
                case 2:
                    scenario.add(getDeleteVertexCommand());
                    break;
                case 3:
                    scenario.add(getUpdateVertexCommand());
                    break;
                case 4:
                    scenario.add(getReadEdgeCommand());
                    break;
                case 5:
                    scenario.add(getCreateVertexCommand());
                    break;

            }
        }

        return scenario;
    }

    private ScenarioCommand getCreateEdgeCommand() {

        VertexModel vertexSrc = getRandomVertex();
        VertexModel vertexDest = getRandomVertex();

        graph.addEdge(vertexSrc, vertexDest);
        return new CreateEdgeCommand(vertexSrc, vertexDest);
    }

    private ScenarioCommand getDeleteEdgeCommand() {

        if (graph.edgeSet().size() > startEdgeSetSize) {
            VertexModel vertexSrc = getRandomVertex();
            VertexModel vertexDest = getRandomVertex();

            graph.removeEdge(vertexSrc, vertexDest);
            return new RemoveEdgeCommand(vertexSrc, vertexDest);
        } else {
            return getReadEdgeCommand();
        }
    }

    private ScenarioCommand getDeleteVertexCommand() {
        if (graph.vertexSet().size() > startVertexSetSize) {

            VertexModel vertex = getRandomVertex();

            graph.removeVertex(vertex);
            return new RemoveVertexCommand(vertex);
        } else {
            return getReadEdgeCommand();
        }
    }

    private ScenarioCommand getUpdateVertexCommand() {
        VertexModel vertex = getRandomVertex();
        VertexModel newVertex = new VertexModel(rand.nextInt(), rand.nextDouble(), "foo" + rand.nextInt());

        return new UpdateVertexCommand(vertex, newVertex);
    }

    private ScenarioCommand getReadEdgeCommand() {
        VertexModel vertex = getRandomVertex();
        return new ReadVertexCommand(vertex);
    }

    private ScenarioCommand getCreateVertexCommand() {
        VertexModel newVertex = new VertexModel(rand.nextInt(), rand.nextDouble(), "foo" + rand.nextInt());
        newVertex.setIdVal(vertexId++);
        graph.addVertex(newVertex);
        return new CreateVertexCommnad(newVertex);
    }

    private VertexModel getRandomVertex() {
        int graphSize = graph.vertexSet().size();
        int vertexIndex = rand.nextInt(graphSize);

        Object[] vertexModels = graph.vertexSet().toArray();
        return (VertexModel) vertexModels[vertexIndex];
    }
}
