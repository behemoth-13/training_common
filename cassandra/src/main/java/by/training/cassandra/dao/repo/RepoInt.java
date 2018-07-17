package by.training.cassandra.dao.repo;

import by.training.cassandra.model.ModelInt;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.ArrayList;
import java.util.List;

public class RepoInt {
    private Session session;
    private static final String TABLE_NAME = "tableInt";

    public RepoInt(Session session) {
        this.session = session;
    }

    public void createTable() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(").append("id int PRIMARY KEY, ").append("ip int,").append("time bigint,").append("domain text);");

        final String query = sb.toString();
        session.execute(query);
    }

    public void deleteTable() {
        final String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        session.execute(query);
    }

    public void insert(ModelInt model) {
        StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(id, ip, time, domain) ").append("VALUES (").append(model.getId()).append(", ").append(model.getIp()).append(", ").append(model.getTime()).append(", '")
                .append(model.getDomain()).append("');");

        final String query = sb.toString();
        session.execute(query);
    }

    public ModelInt selectById(int id) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME).append(" WHERE id = ").append(id).append(";");

        final String query = sb.toString();

        ResultSet rs = session.execute(query);

        List<ModelInt> models = new ArrayList<>();

        for (Row r : rs) {
            ModelInt model = new ModelInt(r.getInt("id"), r.getInt("ip"), r.getLong("time"), r.getString("domain"));
            models.add(model);
        }

        return models.get(0);
    }

    public List<ModelInt> selectAll() {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

        final String query = sb.toString();
        ResultSet rs = session.execute(query);

        List<ModelInt> models = new ArrayList<>();

        for (Row r : rs) {
            ModelInt model = new ModelInt(r.getInt("id"), r.getInt("ip"), r.getLong("time"), r.getString("domain"));
            models.add(model);
        }
        return models;
    }
}
