package by.training.cassandra.model;

public class ModelInt {
    private int id;
    private int ip;
    private long time;
    private String domain;

    public ModelInt() {
    }

    public ModelInt(int id, int ip, long time, String domain) {
        this.id = id;
        this.ip = ip;
        this.time = time;
        this.domain = domain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "ModelInt{" +
                "id=" + id +
                ", ip=" + ip +
                ", time=" + time +
                ", domain='" + domain + '\'' +
                '}';
    }
}
