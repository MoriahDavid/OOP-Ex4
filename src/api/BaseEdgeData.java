package api;

public class BaseEdgeData implements api.EdgeData {

    final double EPS = 0.00000001;

    private int tag;
    private String info;
    private NodeData src;
    private double weight;
    private NodeData dest;

    public BaseEdgeData(double weight, NodeData src, NodeData dest, int tag, String info){
        this.weight = weight;
        this.src = src;
        this.dest = dest;
        this.tag = tag;
        this.info = "" + info;
    }

    public BaseEdgeData(double weight, NodeData src, NodeData dest) {
        this.weight = weight;
        this.src = src;
        this.dest = dest;
    }

    /**
     * The id of the source node of this edge.
     *
     * @return
     */
    @Override
    public int getSrc() {
        return this.src.getKey();
    }

    public NodeData getSrcObj() {
        return this.src;
    }
    public NodeData getDestObj() {
        return this.dest;
    }

    /**
     * The id of the destination node of this edge
     *
     * @return
     */
    @Override
    public int getDest() {
        return this.dest.getKey();
    }

    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Returns the remark (meta data) associated with this edge.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = "" + s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    public boolean edge_contains(GeoLocation p){
        GeoLocation a = this.src.getLocation();
        GeoLocation b = this.dest.getLocation();
        if(a.distance(p) + p.distance(b) - a.distance(b) <= EPS)
            return true;

        return false;
    }

    @Override
    public String toString() {
        return "BaseEdgeData{" +
                "src=" + src.getKey() +
                ", dest=" + dest.getKey() +
                ", weight=" + weight +
                '}';
    }
}
