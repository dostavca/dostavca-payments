public class Transport {
    private Packet packet;
    private double distance;

    public Transport(Packet packet, double distance) {
        this.packet = packet;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {

        return packet;
    }
}
