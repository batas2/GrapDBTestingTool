/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.grapdbtests;

import org.jgrapht.Graph;

/**
 *
 * @author Bartosz
 */
public interface GraphDAO {

    void init();

    void create(Graph<String, VertexModel> grap);

    void createEdge(VertexModel from, VertexModel to);

    void updateVertex(VertexModel vertex);

    void readVertex(VertexModel vertex);

    void deleteEdge(VertexModel from, VertexModel to);

    void deleteVertex(VertexModel vertex);

    void dispose();
}
