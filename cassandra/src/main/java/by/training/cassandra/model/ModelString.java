package by.training.cassandra.model;

public class ModelString {
    private int id;
    private String ip;
    private String time;
    private String domain;

    public ModelString() {
    }

    public ModelString(int id, String ip, String time, String domain) {
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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
        return "ModelString{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", time='" + time + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
