package by.training;

import static org.junit.Assert.assertTrue;

import by.training.cassandra.dao.Connector;
import by.training.cassandra.dao.repo.KeyspaceRepository;
import by.training.cassandra.dao.repo.RepoInt;
import by.training.cassandra.dao.repo.RepoString;
import by.training.cassandra.model.ModelInt;
import by.training.cassandra.model.ModelString;
import com.datastax.driver.core.Session;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class AppTest {

    private static int COUNT = 1_000_000;

    private static final String KEYSPACE_NAME = "test";

    private KeyspaceRepository schemaRepository;
    private RepoString repoString;
    private RepoInt repoInt;
    private Session session;

    @BeforeClass
    public static void init() throws ConfigurationException, TTransportException, IOException, InterruptedException {
        // Start an embedded Cassandra Server
        EmbeddedCassandraServerHelper.startEmbeddedCassandra(20000L);
    }

    @Before
    public void connect() {
        Connector client = new Connector();
        client.connect("127.0.0.1", 9142);
        this.session = client.getSession();
        schemaRepository = new KeyspaceRepository(session);
        schemaRepository.createKeyspace(KEYSPACE_NAME, "SimpleStrategy", 1);
        schemaRepository.useKeyspace(KEYSPACE_NAME);
        repoString = new RepoString(session);
        repoInt = new RepoInt(session);
    }

    @Test
    public void mesure() {
        repoString.createTable();
        repoInt.createTable();

        Long before = System.currentTimeMillis();
        for (int i = 1; i < COUNT; i++) {
            repoString.insert(new ModelString(i, "192.168.1.1", "12345678", "www.tut.by"));
        }
        Long after = System.currentTimeMillis();
        System.out.println(String.format("insert %s rows: %s ms. string version.", COUNT, after - before));

        before = System.currentTimeMillis();
        for (int i = 1; i < COUNT; i++) {
            repoInt.insert(new ModelInt(i, 12345678, 12345678, "www.tut.by"));
        }
        after = System.currentTimeMillis();
        System.out.println(String.format("insert %s rows: %s ms. int version.",COUNT, after - before));

        before = System.currentTimeMillis();
        repoString.selectById(COUNT/2);
        after = System.currentTimeMillis();
        System.out.println(String.format("select by id: %s ms. string version.",after - before));

        before = System.currentTimeMillis();
        repoInt.selectById(COUNT/2);
        after = System.currentTimeMillis();
        System.out.println(String.format("select by id: %s ms. int version.",after - before));

        before = System.currentTimeMillis();
        repoString.selectAll();
        after = System.currentTimeMillis();
        System.out.println(String.format("selectAll %s rows: %s ms. string version.",COUNT, after - before));

        before = System.currentTimeMillis();
        repoInt.selectAll();
        after = System.currentTimeMillis();
        System.out.println(String.format("selectAll %s rows: %s ms. int version.",COUNT, after - before));



        repoString.deleteTable();
        repoInt.deleteTable();
    }

    @AfterClass
    public static void cleanup() {
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }
}
