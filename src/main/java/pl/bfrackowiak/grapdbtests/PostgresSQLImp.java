/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.grapdbtests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.jgrapht.Graph;

/**
 *
 * @author Bartosz
 */
public class PostgresSQLImp implements GraphDAO {

    private static final String PERSISTENCE_UNIT_NAME = "PostgresPersistenceProvider";
    private static final String POSRTGRES_SQL_RESETSQL = "/PosrtgresSqlReset.sql";
    private EntityManager connection;

    @Override
    public void init() {
        try {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            connection = emf.createEntityManager();

            InitDataBase();

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    @Override
    public void create(Graph<VertexModel, WeightedEdge> graph) {
        try {
            connection.getTransaction().begin();
            for (VertexModel vertex : graph.vertexSet()) {
                Query createNativeQuery = connection.createNativeQuery("INSERT INTO \"Vertex\"(\"Id\", \"IntVal\", \"doubleVal\", \"stringVal\")  VALUES ('" + vertex.getIdVal() + "', '" + vertex.getIntVal() + "', '" + vertex.getDoubleVal() + "', '" + vertex.getStringVal() + "');");
                createNativeQuery.executeUpdate();

            }
            for (WeightedEdge edge : graph.edgeSet()) {
                Query createNativeQuery = connection.createNativeQuery("INSERT INTO \"Edges\"(\"src\", \"target\")  VALUES ('" + edge.getSource().getIdVal() + "', '" + edge.getSource().getIdVal() + "');");
                createNativeQuery.executeUpdate();
            }
            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
//            try {
//                connection.getTransaction().rollback();
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
        }
    }

    @Override
    public void createEdge(VertexModel from, VertexModel to) {
        try {
            connection.getTransaction().begin();

            Query createNativeQuery = connection.createNativeQuery("INSERT INTO \"Edges\"(\"src\", \"target\")  VALUES ('" + from.getIdVal() + "', '" + to.getIdVal() + "');");
            createNativeQuery.executeUpdate();

            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
//            try {
//                connection.getTransaction().rollback();
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
        }
    }

    @Override
    public void removeEdge(VertexModel from, VertexModel to) {
        try {
            connection.getTransaction().begin();

            Query createNativeQuery = connection.createNativeQuery("DELETE FROM \"Edges\" WHERE \"src\" ='" + from.getIdVal() + "' AND \"target\" = '" + to.getIdVal() + "';");
            createNativeQuery.executeUpdate();

            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
//            try {
//                connection.getTransaction().rollback();
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
        }
    }

    @Override
    public void createVertex(VertexModel vertexModel) {
        try {
            connection.getTransaction().begin();

            Query createNativeQuery = connection.createNativeQuery("INSERT INTO \"Vertex\"(\"Id\", \"IntVal\", \"doubleVal\", \"stringVal\")  VALUES ('" + vertexModel.getIdVal() + "', '" + vertexModel.getIntVal() + "', '" + vertexModel.getDoubleVal() + "', '" + vertexModel.getStringVal() + "');");
            createNativeQuery.executeUpdate();

            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
//            try {
//                connection.getTransaction().rollback();
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
        }
    }

    @Override
    public void updateVertex(VertexModel vertex, VertexModel newVertex) {
        try {
            connection.getTransaction().begin();


            Query createNativeQuery = connection.createNativeQuery("UPDATE \"Vertex\" SET \"IntVal\"='" + vertex.getIntVal() + "', \"doubleVal\"='" + vertex.getDoubleVal() + "', \"stringVal\"='" + vertex.getStringVal() + "';");
            createNativeQuery.executeUpdate();


            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
//            try {
//                connection.getTransaction().rollback();
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
        }
    }

    @Override
    public void removeVertex(VertexModel vertex) {
        try {
            connection.getTransaction().begin();

            Query createNativeQuery = connection.createNativeQuery("DELETE FROM \"Vertex\" WHERE \"Id\" ='" + vertex.getIdVal() + "'");
            createNativeQuery.executeUpdate();

            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
//            try {
//                connection.getTransaction().rollback();
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
        }
    }

    @Override
    public void readVertex(VertexModel vertex) {
        try {
            connection.getTransaction().begin();

            Query query = connection.createNativeQuery("SELECT \"Id\", \"IntVal\", \"doubleVal\", \"stringVal\"  FROM \"Vertex\" WHERE \"Id\" = '" + vertex.getIdVal() + "';");
            for (Object v : query.getResultList()) {
            }

            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
//            try {
//                connection.getTransaction().rollback();
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
        }
    }

    @Override
    public void dispose() {
        
    }

    private void InitDataBase() throws IOException {
        connection.getTransaction().begin();
        String s;
        StringBuilder sb = new StringBuilder();

        InputStreamReader is = new InputStreamReader(getClass().getResourceAsStream(POSRTGRES_SQL_RESETSQL));
        BufferedReader br = new BufferedReader(is);
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        br.close();
        String[] inst = sb.toString().split(";");
        for (String sqlCommnad : inst) {
            Query q = connection.createNativeQuery(sqlCommnad);
            q.executeUpdate();
        }
        connection.getTransaction().commit();
    }
}
