/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.grapdbtests;

import javax.persistence.Entity;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 *
 * @author Bartosz
 */
@Entity
public class WeightedEdge extends DefaultWeightedEdge {

    public WeightedEdge() {
    }

    public double getWeight() {
        return getWeight();
    }

    public VertexModel getSource() {
        return getSource();
    }

    public VertexModel getTarget() {
        return getTarget();
    }
}
