package pl.bfrackowiak.grapdbtests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.jgrapht.Graph;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;

/**
 * @author Bartosz Frackowiak http://bfrackowiak.pl/
 */
public class Neo4JImp implements GraphDAO {

    private static final String DB_PATH = "target/neo4j-tests-db";
    private GraphDatabaseService graphDb;
    private HashMap<Long, Node> vertexMapping;

    private Node getVertexById(long id) {
        return vertexMapping.get(id);
    }

    @Override
    public void init() {
        clearDb();

        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);

        registerShutdownHook(graphDb);
    }

    @Override
    public void create(Graph<VertexModel, WeightedEdge> graph) {
        vertexMapping = new HashMap<Long, Node>(graph.vertexSet().size() * 5);
        Transaction tx = graphDb.beginTx();
        try {
            for (VertexModel vertex : graph.vertexSet()) {
                createVertex(vertex);
            }

            for (WeightedEdge edge : graph.edgeSet()) {
                createEdge(edge.getSource(), edge.getTarget());
            }
            tx.success();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            tx.finish();
        }
    }

    @Override
    public void createEdge(VertexModel from, VertexModel to) {
        Transaction tx = graphDb.beginTx();
        try {
//            Node source = graphDb.getNodeById(from.getIdVal());
//            Node target = graphDb.getNodeById(to.getIdVal());

            Node source = getVertexById(from.getIdVal());
            Node target = getVertexById(to.getIdVal());

            source.createRelationshipTo(target, RelTypes.KNOWS);
            tx.success();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            tx.finish();
        }
    }

    @Override
    public void removeEdge(VertexModel from, VertexModel to) {
        Transaction tx = graphDb.beginTx();
        try {
//            Node source = graphDb.getNodeById(from.getIdVal());
//            Node target = graphDb.getNodeById(to.getIdVal());

            Node source = getVertexById(from.getIdVal());
            Node target = getVertexById(to.getIdVal());

            Iterable<Relationship> relationships = source.getRelationships(Direction.OUTGOING);
            for (Relationship r : relationships) {
                if (r.getEndNode().getId() == to.getIdVal()) {
                    r.delete();
                }
            }

            tx.success();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            tx.finish();
        }
    }

    @Override
    public void createVertex(VertexModel vertexModel) {
        Transaction tx = graphDb.beginTx();
        try {
            Node node = graphDb.createNode();
            node.setProperty("intVal", vertexModel.getIntVal());
            node.setProperty("doubleVal", vertexModel.getDoubleVal());
            node.setProperty("stringVal", vertexModel.getStringVal());

            vertexMapping.put(vertexModel.getIdVal(), node);
            tx.success();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            tx.finish();
        }
    }

    @Override
    public void updateVertex(VertexModel vertex, VertexModel newVertex) {
        Transaction tx = graphDb.beginTx();
        try {
            //Node node = graphDb.getNodeById(vertex.getIdVal());

            Node node = getVertexById(vertex.getIdVal());

            node.setProperty("intVal", newVertex.getIntVal());
            node.setProperty("doubleVal", newVertex.getDoubleVal());
            node.setProperty("stringVal", newVertex.getStringVal());
            tx.success();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            tx.finish();
        }
    }

    @Override
    public void removeVertex(VertexModel vertex) {
        Transaction tx = graphDb.beginTx();
        try {
            //Node node = graphDb.getNodeById(vertex.getIdVal());

            Node node = getVertexById(vertex.getIdVal());
            for (Relationship r : node.getRelationships()) {
                r.delete();
            }

            node.delete();
            vertexMapping.remove(vertex.getIdVal());

            tx.success();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            tx.finish();
        }
    }

    @Override
    public void readVertex(VertexModel vertex) {
        Transaction tx = graphDb.beginTx();
        try {
            //graphDb.getNodeById(vertex.getIdVal());
            Node node = getVertexById(vertex.getIdVal());
            for (Relationship r : node.getRelationships()) {
                r.toString();
            }
            tx.success();

        } catch (Exception ex) {
            tx.failure();
            System.out.println(ex.getMessage());
        } finally {
            tx.finish();
        }
    }

    @Override
    public void dispose() {
        graphDb.shutdown();
    }

    private static enum RelTypes implements RelationshipType {

        KNOWS
    }

    private void clearDb() {
        try {
            FileUtils.deleteRecursively(new File(DB_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }
}
