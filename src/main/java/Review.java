import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Review {

  private String title;
  private int rating;
  private String description;
  private int id;
  private int podcast_id;

  public Review(String title, int rating, String description, int podcast_id) {
    this.title = title;
    this.rating = rating;
    this.description = description;
    this.podcast_id = podcast_id;
  }

  public String getTitle() {
    return title;
  }

  public int getRating() {
    return rating;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public int getPodcastId() {
    return podcast_id;
  }

  public static List<Review> all() {
    String sql = "SELECT id, title, rating, description, podcast_Id FROM reviews";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Review.class);
    }
  }

  @Override
  public boolean equals(Object otherReview) {
    if (!(otherReview instanceof Review)) {
      return false;
    } else {
      Review newReview = (Review) otherReview;
      return this.getTitle().equals(newReview.getTitle()) &&
      this.getId() == newReview.getId() &&
      this.getPodcastId() == newReview.getPodcastId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO reviews (title, rating, description, podcast_id) VALUES (:title, :rating, :description, :podcast_id)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("title", this.title)
      .addParameter("rating", this.rating)
      .addParameter("description", this.description)
      .addParameter("podcast_id", this.podcast_id)
      .executeUpdate()
      .getKey();
    }
  }

  public static Review find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews where id=:id";
      Review review = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Review.class);
      return review;
    }
  }

  // public List<Review> getReviews() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM reviews WHERE podcast_id=:id";
  //     return con.createQuery(sql)
  //       .addParameter("id", this.id)
  //       .executeAndFetch(Review.class);
  //   }
  // }


}
