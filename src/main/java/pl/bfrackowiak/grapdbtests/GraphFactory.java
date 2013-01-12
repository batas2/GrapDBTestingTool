/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.grapdbtests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 * @author Bartosz Frackowiak
 * http://bfrackowiak.pl/
 */
public class GraphFactory {

    private static Random rand = new Random();

    public static VertexModel GetRandomVertex() {
        VertexModel vertex = new VertexModel(rand.nextInt(), rand.nextDouble(), "foo" + rand.nextInt());
        return vertex;
    }

    public static Graph<VertexModel, WeightedEdge> getRandomGrap(int vertexCount, int edgeCount) {
        DirectedGraph<VertexModel, WeightedEdge> graph =
                new DefaultDirectedGraph<VertexModel, WeightedEdge>(WeightedEdge.class);

        for (int i = 0; i < vertexCount; i++) {
            VertexModel v = GetRandomVertex();
            v.setIdVal(i);
            graph.addVertex(v);
        }

        Object[] vertexModels = graph.vertexSet().toArray();

        for (int i = 0; i < edgeCount; i++) {
            VertexModel vertexSrc = (VertexModel) vertexModels[rand.nextInt(vertexCount)];
            VertexModel vertexDest = (VertexModel) vertexModels[rand.nextInt(vertexCount)];
            graph.addEdge(vertexSrc, vertexDest);
        }
        return graph;
    }

    /**
     * *
     ** Creates a toy directed graph based on URL objects that represents link
     * * structure.
     *
     * * @return a graph based on URL objects.
     *
     */
    public static DirectedGraph<URL, DefaultEdge> createDirectedGraph() {
        DirectedGraph<URL, DefaultEdge> g =
                new DefaultDirectedGraph<URL, DefaultEdge>(DefaultEdge.class);

        try {
            URL amazon = new URL("[http://www.amazon.com](http://www.amazon.com)");
            URL yahoo = new URL("[http://www.yahoo.com](http://www.yahoo.com)");
            URL ebay = new URL("[http://www.ebay.com](http://www.ebay.com)");

            g.addVertex(amazon);
            g.addVertex(yahoo);
            g.addVertex(ebay);


            g.addEdge(yahoo, amazon);
            g.addEdge(yahoo, ebay);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return g;
    }

    /**
     * *
     ** Craete a toy graph based on String objects.
     *
     * * @return a graph based on String objects.
     */
    public static UndirectedGraph<String, DefaultEdge> createUndirectedGraph() {
        UndirectedGraph<String, DefaultEdge> g =
                new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v3, v4);
        g.addEdge(v4, v1);

        return g;
    }
}
