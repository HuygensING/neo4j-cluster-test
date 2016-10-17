package nl.knaw.huygens.db;

import java.util.List;

public interface DbAccess {
  void close();

  void createVertexWithName(String name);

  List<String> getVerticesByName(String name);

  List<String> getAll(int numberOfVertices);
}
