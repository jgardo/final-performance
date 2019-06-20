package pl.jgardo.field.instance;

import java.util.*;

public class ObjectHolder {

    private List<PointFinal> nonFinalPointFinals;
    private final List<PointFinal> finalPointFinals;
    private List<PointFinal> nonFinalPointFinalsAsList;
    private final List<PointFinal> finalPointFinalsAsList;
    private PointFinal[] nonFinalPointFinalsAsArray;
    private final PointFinal[] finalPointFinalsAsArray;

    ObjectHolder(PointFinal[] points) {
        this.nonFinalPointFinals = new ArrayList<>(Arrays.asList(points));
        this.finalPointFinals = new ArrayList<>(Arrays.asList(points));
        this.nonFinalPointFinalsAsList = Arrays.asList(points);
        this.finalPointFinalsAsList = Arrays.asList(points);
        this.nonFinalPointFinalsAsArray = points;
        this.finalPointFinalsAsArray = points;
    }

    public double getXFromFinalListFinalPoint(int i) {
        return finalPointFinals.get(i).getX();
    }

    public double getXFromNonFinalListFinalPoint(int i) {
        return nonFinalPointFinals.get(i).getX();
    }

    public double getXFromFinalListFinalPointAsList(int i) {
        return finalPointFinalsAsList.get(i).getX();
    }

    public double getXFromNonFinalListFinalPointAsList(int i) {
        return nonFinalPointFinalsAsList.get(i).getX();
    }

    public double getXFromFinalListFinalPointAsArray(int i) {
        return finalPointFinalsAsArray[i].getX();
    }

    public double getXFromNonFinalListFinalPointAsArray(int i) {
        return nonFinalPointFinalsAsArray[i].getX();
    }
}
