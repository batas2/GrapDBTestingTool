package pl.bfrackowiak.TestsScenario.Commands;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import pl.bfrackowiak.grapdbtests.GraphDAO;
import pl.bfrackowiak.grapdbtests.VertexModel;
import pl.bfrackowiak.grapdbtests.WeightedEdge;

/**
 * @author Bartosz Frackowiak http://bfrackowiak.pl/
 */
public class CreateGraphCommand implements ScenarioCommand {

    private Graph<VertexModel, WeightedEdge> graph;

    public CreateGraphCommand(Graph<VertexModel, WeightedEdge> graph) {
        this.graph = cloneGraph(graph);
    }

    @Override
    public void Execute(GraphDAO graphDAO) {
        graphDAO.create(graph);
    }

    @Override
    public String toString() {
        return "CreateGraphCommand{" + "graph=" + graph + '}';
    }

    public static Graph<VertexModel, WeightedEdge> cloneGraph(Graph<VertexModel, WeightedEdge> graph) {

        try {
            VertexModel[] vertexModels = new VertexModel[graph.vertexSet().size()];
            DefaultDirectedGraph<VertexModel, WeightedEdge> result = new DefaultDirectedGraph<VertexModel, WeightedEdge>(WeightedEdge.class);

            for (VertexModel vertex : graph.vertexSet()) {
                VertexModel v = (VertexModel) vertex.clone();
                vertexModels[(int) v.getIdVal()] = v;
                result.addVertex(v);
            }

            for (WeightedEdge edge : graph.edgeSet()) {
                result.addEdge(vertexModels[(int) edge.getSource().getIdVal()], vertexModels[(int) edge.getTarget().getIdVal()]);
            }
            return result;
        } catch (CloneNotSupportedException ex) {
            System.out.println(ex);
        }
        return null;
    }
}
