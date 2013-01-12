package pl.bfrackowiak.grapdbtests;

import org.jgrapht.graph.DefaultEdge;

/**
 * @author Bartosz Frackowiak
 * http://bfrackowiak.pl/
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
