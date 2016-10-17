package nl.knaw.huygens;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.knaw.huygens.db.DbAccessFactory;
import nl.knaw.huygens.resources.DbAccessResource;

public class Neo4jClusterTestApplication extends Application<Neo4jClusterTestConfiguration> {

  public static void main(final String[] args) throws Exception {
    new Neo4jClusterTestApplication().run(args);
  }

  @Override
  public String getName() {
    return "Neo4jClusterTest";
  }

  @Override
  public void initialize(final Bootstrap<Neo4jClusterTestConfiguration> bootstrap) {
    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
      bootstrap.getConfigurationSourceProvider(),
      new EnvironmentVariableSubstitutor(false)
    ));
  }

  @Override
  public void run(final Neo4jClusterTestConfiguration configuration,
                  final Environment environment) {
    DbAccessFactory dbAccessFactory = configuration.getDbAccessFactory();
    environment.jersey().register(new DbAccessResource(dbAccessFactory.build(environment)));

  }

}
