package pl.bfrackowiak.grapdbtests;

import org.jgrapht.Graph;

/**
 * @author Bartosz Frackowiak
 * http://bfrackowiak.pl/
 */
public interface GraphDAO {

    void init();

    void create(Graph<VertexModel, WeightedEdge> graph);

    void createEdge(VertexModel from, VertexModel to);

    void removeEdge(VertexModel from, VertexModel to);

    void createVertex(VertexModel vertexModel);
    
    void updateVertex(VertexModel vertex, VertexModel newVertex);

    void removeVertex(VertexModel vertex);

    void readVertex(VertexModel vertex);

    void dispose();
}
