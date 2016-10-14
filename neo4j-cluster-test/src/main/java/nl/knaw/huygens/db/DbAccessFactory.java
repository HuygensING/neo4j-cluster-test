package nl.knaw.huygens.db;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class DbAccessFactory {
  private static Label LABEL = Label.label("test");
  @NotNull
  private String path;


  public DbAccess build(Environment environment) {
    final GraphDatabaseService graphDatabaseService =
      new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(new File(path))
                                .setConfig(GraphDatabaseSettings.pagecache_memory, "512M")
                                .setConfig(GraphDatabaseSettings.string_block_size, "60")
                                .setConfig(GraphDatabaseSettings.array_block_size, "300")
                                .newGraphDatabase();

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

  @JsonProperty
  public String getPath() {
    return path;
  }

  @JsonProperty
  public void setPath(String path) {
    this.path = path;
  }
}
