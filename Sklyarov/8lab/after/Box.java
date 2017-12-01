
public class Box{
    private int weight;
    private int cost;

    public Box(int weight, int cost) {
        this.weight = weight;
        this.cost = cost;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getCost() {
        return this.cost;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Box)) {
            return false;
        }
        Box tmp = (Box) o;
        return this.weight == tmp.getWeight() && this.cost == tmp.getCost();
    }
}