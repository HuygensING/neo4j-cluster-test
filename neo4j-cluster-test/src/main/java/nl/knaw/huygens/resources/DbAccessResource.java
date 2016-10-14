package nl.knaw.huygens.resources;

import nl.knaw.huygens.db.DbAccess;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("dbaccess")
public class DbAccessResource {

  private final DbAccess dbAccess;

  public DbAccessResource(DbAccess dbAccess) {
    this.dbAccess = dbAccess;
  }

  @GET
  @Path("addVertex/{name}")
  public Response addVertex(@PathParam("name") String name) {
    dbAccess.createVertexWithName(name);

    return Response.ok(name).build();
  }

  @GET
  @Path("getVertices/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getVertices(@PathParam("name") String name) {
    List<String> vertices = dbAccess.getVerticesByName(name);

    if (vertices.isEmpty()) {
      return Response.status(404).entity("{\"message\":\"No Vertices with name '" + name + "' found\"}").build();
    }

      return Response.ok(vertices).build();
    }

  }
