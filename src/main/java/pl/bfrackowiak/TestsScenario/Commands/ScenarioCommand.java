/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.TestsScenario.Commands;

import org.jgrapht.Graph;
import pl.bfrackowiak.grapdbtests.GraphDAO;

/**
 *
 * @author Bartosz
 */
public interface ScenarioCommand {

    void Execute(GraphDAO graphDAO);
}
