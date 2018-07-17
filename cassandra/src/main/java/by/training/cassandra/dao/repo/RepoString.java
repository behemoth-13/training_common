package by.training.cassandra.dao.repo;

import by.training.cassandra.model.ModelString;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.ArrayList;
import java.util.List;

public class RepoString {
    private Session session;
    private static final String TABLE_NAME = "tableString";

    public RepoString(Session session) {
        this.session = session;
    }

    public void createTable() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(").append("id int PRIMARY KEY, ").append("ip text,").append("time text,").append("domain text);");

        final String query = sb.toString();
        session.execute(query);
    }

    public void deleteTable() {
        final String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        session.execute(query);
    }

    public void insert(ModelString model) {
        StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(id, ip, time, domain) ").append("VALUES (").append(model.getId()).append(", '").append(model.getIp()).append("', '").append(model.getTime()).append("', '")
                .append(model.getDomain()).append("');");

        final String query = sb.toString();
        session.execute(query);
    }

    public ModelString selectById(int id) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME).append(" WHERE id = ").append(id).append(";");

        final String query = sb.toString();

        ResultSet rs = session.execute(query);

        List<ModelString> models = new ArrayList<>();

        for (Row r : rs) {
            ModelString model = new ModelString(r.getInt("id"), r.getString("ip"), r.getString("time"), r.getString("domain"));
            models.add(model);
        }

        return models.get(0);
    }

    public List<ModelString> selectAll() {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

        final String query = sb.toString();
        ResultSet rs = session.execute(query);

        List<ModelString> models = new ArrayList<>();

        for (Row r : rs) {
            ModelString model = new ModelString(r.getInt("id"), r.getString("ip"), r.getString("time"), r.getString("domain"));
            models.add(model);
        }
        return models;
    }
}
