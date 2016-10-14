package nl.knaw.huygens;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import nl.knaw.huygens.db.DbAccessFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Neo4jClusterTestConfiguration extends Configuration {

  @Valid
  @NotNull
  private DbAccessFactory dbAccessFactory = new DbAccessFactory();

  @JsonProperty("dbAccessFactory")
  public DbAccessFactory getDbAccessFactory() {
    return dbAccessFactory;
  }

  @JsonProperty
  public void setDbAccessFactory(DbAccessFactory dbAccessFactory){
    this.dbAccessFactory = dbAccessFactory;
  }
}
