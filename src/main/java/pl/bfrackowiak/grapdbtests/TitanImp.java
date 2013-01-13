package pl.bfrackowiak.grapdbtests;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.jgrapht.Graph;

/**
 * @author Bartosz Frackowiak http://bfrackowiak.pl/
 */
public class TitanImp implements GraphDAO {

    private static final String DB_PATH = "target/titan-tests-db";
    private static final String KNOWS = "knows";
    private TitanGraph graphDb;
    private HashMap<Long, Vertex> vertexMapping;

    public TitanImp() {
        vertexMapping = new LinkedHashMap<Long, Vertex>();
    }

    @Override
    public void init() {
        graphDb = TitanFactory.open(DB_PATH);
        //graphDb = TitanFactory.openInMemoryGraph();
    }

    private Vertex getVertexById(long id) {
        return vertexMapping.get(id);
    }

    @Override
    public void create(Graph<VertexModel, WeightedEdge> graph) {
        vertexMapping = new HashMap<Long, Vertex>(graph.vertexSet().size() * 5);
        for (VertexModel vertex : graph.vertexSet()) {
            createVertex(vertex);
        }

        for (WeightedEdge edge : graph.edgeSet()) {
            createEdge(edge.getSource(), edge.getTarget());
        }

    }

    @Override
    public void createEdge(VertexModel from, VertexModel to) {
        TitanTransaction tx = graphDb.startTransaction();
        try {

            Vertex source = getVertexById(from.getIdVal());
            Vertex target = getVertexById(to.getIdVal());

            graphDb.addEdge(null, source, target, KNOWS);

            tx.commit();
        } catch (Exception ex) {
            tx.abort();
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void removeEdge(VertexModel from, VertexModel to) {
        TitanTransaction tx = graphDb.startTransaction();
        try {
            Vertex fromVertex = getVertexById(from.getIdVal());
            Vertex toVertex = getVertexById(to.getIdVal());

            Iterable<Edge> edges = fromVertex.getEdges(Direction.OUT, KNOWS);
            for (Edge edge : edges) {

                Vertex inVertex = edge.getVertex(Direction.IN);

                if (inVertex.getId() == toVertex.getId()) {
                    graphDb.removeEdge(edge);
                }
            }

            tx.commit();
        } catch (Exception ex) {
            tx.abort();
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void createVertex(VertexModel vertexModel) {
        TitanTransaction tx = graphDb.startTransaction();
        try {
            Vertex node = graphDb.addVertex(vertexModel.getIdVal());

            node.setProperty("intVal", vertexModel.getIntVal());
            node.setProperty("doubleVal", vertexModel.getDoubleVal());
            node.setProperty("stringVal", vertexModel.getStringVal());

            vertexMapping.put(vertexModel.getIdVal(), node);
            tx.commit();
        } catch (Exception ex) {
            tx.abort();
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void updateVertex(VertexModel vertex, VertexModel newVertex) {
        TitanTransaction tx = graphDb.startTransaction();
        try {
            Vertex node = getVertexById(vertex.getIdVal());
            node.setProperty("intVal", newVertex.getIntVal());
            node.setProperty("doubleVal", newVertex.getDoubleVal());
            node.setProperty("stringVal", newVertex.getStringVal());

            tx.commit();
        } catch (Exception ex) {
            tx.abort();
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void removeVertex(VertexModel vertex) {
        TitanTransaction tx = graphDb.startTransaction();
        try {
            Vertex node = getVertexById(vertex.getIdVal());

            graphDb.removeVertex(node);
            vertexMapping.remove(vertex.getIdVal());

            tx.commit();
        } catch (Exception ex) {
            tx.abort();
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void readVertex(VertexModel vertex) {
        TitanTransaction tx = graphDb.startTransaction();
        try {
            Vertex node = getVertexById(vertex.getIdVal());
            tx.commit();
        } catch (Exception ex) {
            tx.abort();
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void dispose() {
        graphDb.shutdown();
    }
}
