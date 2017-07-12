import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Podcast {

  private String name;
  private String genre;
  private String description;
  private int id;

  public Podcast(String name, String genre, String description) {
    this.name = name;
    this.genre = genre;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getGenre() {
    return genre;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public static List<Podcast> all() {
    String sql = "SELECT id, name, genre, description FROM podasts";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Podcast.class);
    }
  }

  @Override
  public boolean equals(Object otherPodcast) {
    if (!(otherPodcast instanceof Podcast)) {
      return false;
    } else {
      Podcast newPodcast = (Podcast) otherPodcast;
      return this.getDescription().equals(newPodcast.getDescription());
    }
  }





}
