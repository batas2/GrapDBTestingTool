/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.grapdbtests;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 *
 * @author Bartosz
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
