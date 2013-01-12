/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.grapdbtests;

import org.jgrapht.graph.DefaultEdge;

/**
 *
 * @author Bartosz
 */
public class WeightedEdge extends DefaultEdge {

    public WeightedEdge() {
    }

    @Override
    public VertexModel getSource() {
        return (VertexModel) super.getSource();
    }

    @Override
    public VertexModel getTarget() {
        return (VertexModel) super.getTarget();
    }
}
