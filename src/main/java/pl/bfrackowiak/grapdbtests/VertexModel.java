/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bfrackowiak.grapdbtests;

import java.util.Random;

/**
 *
 * @author Bartosz
 */
public class VertexModel {

    private long idVal;
    private int intVal;
    private double doubleVal;
    private String stringVal;

    public VertexModel() {
    }

    public VertexModel(int id) {
        idVal = id;
    }

    public VertexModel(int intVal, double doubleVal, String stringVal) {
        this.intVal = intVal;
        this.doubleVal = doubleVal;
        this.stringVal = stringVal;
    }

    public long getIdVal() {
        return idVal;
    }

    public void setIdVal(long idVal) {
        this.idVal = idVal;
    }

    @Override
    public String toString() {
        return "EdgeModel{" + "intVal=" + intVal + ", doubleVal=" + doubleVal + ", stringVal=" + stringVal + '}';
    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    public double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(double doubleVal) {
        this.doubleVal = doubleVal;
    }

    public String getStringVal() {
        return stringVal;
    }

    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }

    public static VertexModel getRandomVertex() {
        Random r = new Random();
        return new VertexModel(r.nextInt(), r.nextDouble(), "Random String" + r.nextInt());
    }
}
