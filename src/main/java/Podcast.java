import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
    String sql = "SELECT id, name, genre, description FROM podcasts";
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
      return this.getName().equals(newPodcast.getName()) && this.getId() == newPodcast.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO podcasts (name, genre, description) VALUES (:name, :genre, :description)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("genre", this.genre)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  public static Podcast find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM podcasts where id=:id";
      Podcast podcast = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Podcast.class);
      return podcast;
    }
  }

  public List<Review> getReviews() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE podcast_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Review.class);
    }
  }


}
