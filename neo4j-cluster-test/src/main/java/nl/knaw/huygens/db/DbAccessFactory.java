package nl.knaw.huygens.db;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import org.neo4j.cluster.ClusterSettings;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.graphdb.factory.HighlyAvailableGraphDatabaseFactory;
import org.neo4j.kernel.ha.HaSettings;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class DbAccessFactory {
  private static Label LABEL = Label.label("test");

  @NotNull
  @JsonProperty
  private String path;

  @NotNull
  @JsonProperty("db_mode")
  private String dbMode;

  @JsonProperty("server_id")
  private String serverId;

  @JsonProperty("server_name")
  private String serverName;

  @JsonProperty("coordination_port")
  private String coordinationPort;

  @JsonProperty("data_port")
  private String dataPort;

  @JsonProperty("init_hosts")
  private String initialHosts;

  public DbAccess build(Environment environment) {
    final GraphDatabaseService graphDatabaseService = createGraphDb();

    DbAccess dbAccess = createDbAccess(graphDatabaseService);

    environment.lifecycle().manage(new Managed() {
      @Override
      public void start() throws Exception {

      }

      @Override
      public void stop() throws Exception {
        dbAccess.close();
      }
    });

    return dbAccess;
  }

  private GraphDatabaseService createGraphDb() {
    if ("HA".equals(dbMode)) {
      return new HighlyAvailableGraphDatabaseFactory()
        .newEmbeddedDatabaseBuilder(new File(path))
        .setConfig(GraphDatabaseSettings.pagecache_memory, "512M")
        .setConfig(GraphDatabaseSettings.string_block_size, "60")
        .setConfig(GraphDatabaseSettings.array_block_size, "300")
        .setConfig(ClusterSettings.server_id, serverId)
        .setConfig(ClusterSettings.instance_name, serverName)
        .setConfig(ClusterSettings.cluster_server, String.format("%s:%s", serverName, coordinationPort))
        .setConfig(ClusterSettings.instance_name, serverName)
        .setConfig(ClusterSettings.cluster_name, serverName)
        .setConfig(ClusterSettings.initial_hosts, initialHosts)
        .setConfig(HaSettings.ha_server, String.format("%s:%s", serverName, dataPort))
        .setConfig(HaSettings.pull_interval, "5ms")
        .newGraphDatabase();
    } else {
      return new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(new File(path))
                                       .setConfig(GraphDatabaseSettings.pagecache_memory, "512M")
                                       .setConfig(GraphDatabaseSettings.string_block_size, "60")
                                       .setConfig(GraphDatabaseSettings.array_block_size, "300")
                                       .newGraphDatabase();
    }
  }

  private DbAccess createDbAccess(final GraphDatabaseService graphDatabaseService) {
    return new DbAccess() {
      @Override
      public void close() {
        graphDatabaseService.shutdown();
      }

      @Override
      public void createVertexWithName(String name) {
        try (Transaction transaction = graphDatabaseService.beginTx()) {
          Node node = graphDatabaseService.createNode(LABEL);
          node.setProperty("name", name);
          transaction.success();
        }
      }

      @Override
      public List<String> getVerticesByName(String name) {
        try (Transaction transaction = graphDatabaseService.beginTx()) {
          ResourceIterator<Node> nodes = graphDatabaseService.findNodes(LABEL, "name", name);

          List<String> output =
            nodes.stream().map(v -> v.getId() + " : " + v.getProperty("name")).collect(Collectors.toList());
          transaction.success();

          return output;
        }

      }
    };
  }
}
