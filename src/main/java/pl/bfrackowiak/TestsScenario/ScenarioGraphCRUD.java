/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.TestsScenario;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.jgrapht.Graph;
import pl.bfrackowiak.TestsScenario.Commands.CreateEdgeCommand;
import pl.bfrackowiak.TestsScenario.Commands.CreateVertexCommnad;
import pl.bfrackowiak.TestsScenario.Commands.ReadVertexCommand;
import pl.bfrackowiak.TestsScenario.Commands.RemoveEdgeCommand;
import pl.bfrackowiak.TestsScenario.Commands.RemoveVertexCommand;
import pl.bfrackowiak.TestsScenario.Commands.ScenarioCommand;
import pl.bfrackowiak.TestsScenario.Commands.UpdateVertexCommand;
import pl.bfrackowiak.grapdbtests.VertexModel;
import pl.bfrackowiak.grapdbtests.WeightedEdge;

/**
 *
 * @author Bartosz
 */
public class ScenarioGraphCRUD {

    private ScenarioGenerator generator;
    private Graph<VertexModel, WeightedEdge> graph;
    private int startVertexSetSize;
    private int startEdgeSetSize;
    private int vertexId;
    private Random rand;

    public ScenarioGraphCRUD(Graph<VertexModel, WeightedEdge> graph, ScenarioGenerator generator) {
        this.generator = generator;
        this.graph = graph;
        startVertexSetSize = graph.vertexSet().size();
        startEdgeSetSize = graph.edgeSet().size();
        vertexId = startVertexSetSize;
        rand = new Random();
    }

    public ScenarioCommand getCreateEdgeCommand() {

        VertexModel vertexSrc = generator.getVertex();
        VertexModel vertexDest = generator.getVertex();

        graph.addEdge(vertexSrc, vertexDest);
        return new CreateEdgeCommand(vertexSrc, vertexDest);
    }

    public ScenarioCommand getDeleteEdgeCommand() {

        if (graph.edgeSet().size() > startEdgeSetSize) {

            WeightedEdge edge = generator.getEdge();

            graph.removeEdge(edge.getSource(), edge.getTarget());
            return new RemoveEdgeCommand(edge.getSource(), edge.getTarget());
        } else {
            return getReadVertexCommand();
        }
    }

    public List<ScenarioCommand> getDeleteVertexCommand() {
        List<ScenarioCommand> commands = new LinkedList< ScenarioCommand>();
        if (graph.vertexSet().size() > startVertexSetSize) {

            VertexModel vertex = generator.getVertex();
            for (WeightedEdge edge : graph.edgesOf(vertex)) {
                commands.add(new RemoveEdgeCommand(edge.getSource(), edge.getTarget()));
                graph.removeEdge(edge);
            }

            graph.removeVertex(vertex);
            commands.add(new RemoveVertexCommand(vertex));
        }
        return commands;
    }

    public ScenarioCommand getUpdateVertexCommand() {
        VertexModel vertex = generator.getVertex();
        VertexModel newVertex = new VertexModel(rand.nextInt(), rand.nextDouble(), "foo" + rand.nextInt());

        return new UpdateVertexCommand(vertex, newVertex);
    }

    public ScenarioCommand getReadVertexCommand() {
        VertexModel vertex = generator.getVertex();
        return new ReadVertexCommand(vertex);
    }

    public ScenarioCommand getCreateVertexCommand() {
        VertexModel newVertex = new VertexModel(rand.nextInt(), rand.nextDouble(), "foo" + rand.nextInt());
        newVertex.setIdVal(vertexId++);
        graph.addVertex(newVertex);
        return new CreateVertexCommnad(newVertex);
    }
}
