package pl.bfrackowiak.grapdbtests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.jgrapht.Graph;

/**
 * @author Bartosz Frackowiak http://bfrackowiak.pl/
 */
public class PostgresSQLImp implements GraphDAO {

    private static final String PERSISTENCE_UNIT_NAME = "PostgresPersistenceProvider";
    private static final String POSRTGRES_SQL_RESETSQL = "/PosrtgresSqlReset.sql";
    private static final String INSERT_VERTEX_QUERY = "INSERT INTO \"Vertex\"(\"Id\", \"IntVal\", \"doubleVal\", \"stringVal\")  VALUES ('%d', '%d', '%f', '%s');";
    private static final String UPDATE_VERTEX_QUERY = "UPDATE \"Vertex\" SET \"IntVal\"='%d', \"doubleVal\"='%f', \"stringVal\"='%s';";
    private static final String READ_VERTEX_QUERY = "SELECT \"Id\", \"IntVal\", \"doubleVal\", \"stringVal\"  FROM \"Vertex\" WHERE \"Id\" = '%d';";
    private static final String DELETE_VERTEX_QUERY = "DELETE FROM \"Vertex\" WHERE \"Id\" ='%d';";
    private static final String INSERT_EDGE_QUERY = "INSERT INTO \"Edges\"(\"src\", \"target\")  VALUES ('%d', '%d');";
    private static final String DELETE_EDGE_QUERY = "DELETE FROM \"Edges\" WHERE \"src\" ='%d' AND \"target\" = '%d';";
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
        for (VertexModel vertex : graph.vertexSet()) {
            createVertex(vertex);
        }
        for (WeightedEdge edge : graph.edgeSet()) {
            createEdge(edge.getSource(), edge.getTarget());
        }
    }

    @Override
    public void createEdge(VertexModel from, VertexModel to) {
        try {
            connection.getTransaction().begin();

            Query query = connection.createNativeQuery(String.format(Locale.US, INSERT_EDGE_QUERY, from.getIdVal(), to.getIdVal()));
            query.executeUpdate();

            connection.getTransaction().commit();
        } catch (Exception e) {
            connection.getTransaction().rollback();
            System.out.println(e);
        }
    }

    @Override
    public void removeEdge(VertexModel from, VertexModel to) {
        try {
            connection.getTransaction().begin();

            Query query = connection.createNativeQuery(String.format(Locale.US, DELETE_EDGE_QUERY, from.getIdVal(), to.getIdVal()));
            query.executeUpdate();

            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            connection.getTransaction().rollback();
        }
    }

    @Override
    public void createVertex(VertexModel vertexModel) {
        try {
            connection.getTransaction().begin();

            Query query = connection.createNativeQuery(String.format(Locale.US, INSERT_VERTEX_QUERY, vertexModel.getIdVal(), vertexModel.getIntVal(), vertexModel.getDoubleVal(), vertexModel.getStringVal()));
            query.executeUpdate();

            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            connection.getTransaction().rollback();
        }
    }

    @Override
    public void updateVertex(VertexModel vertex, VertexModel newVertex) {
        try {
            connection.getTransaction().begin();

            Query query = connection.createNativeQuery(String.format(Locale.US, UPDATE_VERTEX_QUERY, vertex.getIntVal(), vertex.getDoubleVal(), vertex.getStringVal()));
            query.executeUpdate();

            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            connection.getTransaction().rollback();
        }
    }

    @Override
    public void removeVertex(VertexModel vertex) {
        try {
            connection.getTransaction().begin();

            Query query = connection.createNativeQuery(String.format(Locale.US, DELETE_VERTEX_QUERY, vertex.getIdVal()));
            query.executeUpdate();

            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            connection.getTransaction().rollback();
        }
    }

    @Override
    public void readVertex(VertexModel vertex) {
        try {
            connection.getTransaction().begin();

            Query query = connection.createNativeQuery(String.format(Locale.US, READ_VERTEX_QUERY, vertex.getIdVal()));

            for (Object v : query.getResultList()) {
            }

            connection.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            connection.getTransaction().rollback();
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
