/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.TestsScenario;

import org.jgrapht.Graph;
import pl.bfrackowiak.grapdbtests.GraphDAO;
import pl.bfrackowiak.grapdbtests.GraphFactory;

/**
 *
 * @author Bartosz
 */
public class RandomScenario {

    private GraphDAO graphDAO;
    private GraphFactory graphFactory;

    public RandomScenario(GraphDAO graphDao, GraphFactory graphFactory) {
        graphDAO = graphDao;
        graphFactory = graphFactory;
    }

    public void Run(int iterations) {
        Graph g = graphFactory.getRandomGrap();
        
        for (int i = 0; i < iterations; i++) {
            
        }
    }
}
