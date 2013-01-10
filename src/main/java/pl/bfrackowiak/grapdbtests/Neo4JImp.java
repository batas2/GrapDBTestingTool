/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.grapdbtests;

import java.io.File;
import java.io.IOException;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;

/**
 *
 * @author Bartosz
 */
public class Neo4JImp implements GraphDAO {

    private static final String DB_PATH = "target/neo4j-tests-db";
    String greeting;
    // START SNIPPET: vars
    GraphDatabaseService graphDb;
    Node firstNode;
    Node secondNode;
    Relationship relationship;
    // END SNIPPET: vars

    public Neo4JImp() {
        init();
    }

    @Override
    public void init() {
        clearDb();

        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        registerShutdownHook(graphDb);


        // START SNIPPET: transaction
        Transaction tx = graphDb.beginTx();
        try {
            // Updating operations go here
            // END SNIPPET: transaction
            // START SNIPPET: addData
            firstNode = graphDb.createNode();
            firstNode.setProperty("message", "Hello, ");
            secondNode = graphDb.createNode();
            secondNode.setProperty("message", "World!");


            relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
            relationship.setProperty("message", "brave Neo4j ");
            // END SNIPPET: addData

            // START SNIPPET: readData
            System.out.print(firstNode.getProperty("message"));
            System.out.print(relationship.getProperty("message"));
            System.out.print(secondNode.getProperty("message"));
            // END SNIPPET: readData

            greeting = ((String) firstNode.getProperty("message"))
                    + ((String) relationship.getProperty("message"))
                    + ((String) secondNode.getProperty("message"));

            // START SNIPPET: transaction
            tx.success();
        } finally {
            tx.finish();
        }
        // END SNIPPET: transaction
    }

    @Override
    public void create(Graph<VertexModel, WeightedEdge> graph) {
        Transaction tx = graphDb.beginTx();
        try {
            for (VertexModel vertex : graph.vertexSet()) {
                Node node = graphDb.createNode();
                node.setProperty("intVal", vertex.getIntVal());
                node.setProperty("doubleVal", vertex.getDoubleVal());
                node.setProperty("stringVal", vertex.getStringVal());
                vertex.setIdVal(node.getId());
            }

            for (WeightedEdge edge : graph.edgeSet()) {
                Node source = graphDb.getNodeById(edge.getSource().getIdVal());
                Node target = graphDb.getNodeById(edge.getTarget().getIdVal());

                source.createRelationshipTo(target, RelTypes.KNOWS);
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
            Node source = graphDb.getNodeById(from.getIdVal());
            Node target = graphDb.getNodeById(to.getIdVal());

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
            Node source = graphDb.getNodeById(from.getIdVal());
            Node target = graphDb.getNodeById(to.getIdVal());

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
            vertexModel.setIdVal(node.getId());
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
            Node node = graphDb.getNodeById(vertex.getIdVal());
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
            Node node = graphDb.getNodeById(vertex.getIdVal());

            for (Relationship r : node.getRelationships()) {
                r.delete();
            }
            node.delete();

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
            graphDb.getNodeById(vertex.getIdVal());
            tx.success();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            tx.finish();
        }
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // START SNIPPET: createReltype
    private static enum RelTypes implements RelationshipType {

        KNOWS
    }
    // END SNIPPET: createReltype

    private void clearDb() {
        try {
            FileUtils.deleteRecursively(new File(DB_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void removeData() {
        Transaction tx = graphDb.beginTx();
        try {
            // START SNIPPET: removingData
            // let's remove the data
            firstNode.getSingleRelationship(RelTypes.KNOWS, Direction.OUTGOING).delete();
            firstNode.delete();
            secondNode.delete();
            // END SNIPPET: removingData

            tx.success();
        } finally {
            tx.finish();
        }
    }

    void shutDown() {
        System.out.println();
        System.out.println("Shutting down database ...");
        // START SNIPPET: shutdownServer
        graphDb.shutdown();
        // END SNIPPET: shutdownServer
    }

    // START SNIPPET: shutdownHook
    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running example before it's completed)
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }
    // END SNIPPET: shutdownHook    
}
