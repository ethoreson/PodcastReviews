import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class ReviewTest {

  Review myReview = new Review("I loved it!", 5, "I was hooked from the beginning", 1);

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/podcast_review_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deletePodcastsQuery = "DELETE FROM podcasts *;";
      String deleteReviewsQuery = "DELETE FROM reviews *;";
      con.createQuery(deletePodcastsQuery).executeUpdate();
      con.createQuery(deleteReviewsQuery).executeUpdate();
    }
  }

  @Test
  public void Review_instantiatesCorrectly_true() {
    assertEquals(true, myReview instanceof Review);
  }

  @Test
  public void getTitle_retrievesReviewsTitle_true() {
    assertEquals("I loved it!", myReview.getTitle());
  }

  @Test
  public void getGenre_retrievesReviewsRating_true() {
    assertEquals(5, myReview.getRating());
  }

  @Test
  public void getDescription_retrievesReviewsDescription_true() {
    assertEquals("I was hooked from the beginning", myReview.getDescription());
  }

  @Test
  public void equals_returnsTrueIfEntriesAretheSame() {
    Review firstReview = new Review("I loved it!", 5, "I was hooked from the beginning", 1);
    Review secondReview = new Review("I loved it!", 5, "I was hooked from the beginning", 1);
    assertTrue(firstReview.equals(secondReview));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    myReview.save();
    assertTrue(Review.all().get(0).equals(myReview));
  }

  @Test
  public void all_returnsAllInstancesOfReview_true() {
    Review firstReview = new Review("I loved it!", 5, "I was hooked from the beginning", 1);
    firstReview.save();
    Review secondReview = new Review("It was bad", 2, "Boring boring BORING!", 1);
    secondReview.save();
    assertEquals(true, Review.all().get(0).equals(firstReview));
    assertEquals(true, Review.all().get(1).equals(secondReview));
  }

  @Test
  public void save_assignsIdToObject() {
    Review myReview = new Review("I loved it!", 5, "I was hooked from the beginning", 1);
    myReview.save();
    Review savedReview = Review.all().get(0);
    assertEquals(myReview.getId(), savedReview.getId());
  }

  @Test
  public void getId_ReviewsInstantiateWithAnID() {
    Review myReview = new Review("I loved it!", 5, "I was hooked from the beginning", 1);
    myReview.save();
    assertTrue(myReview.getId() > 0);
  }

  @Test
  public void find_returnsReviewWithSameId_secondReview() {
    Review firstReview = new Review("I loved it!", 5, "I was hooked from the beginning", 1);
    firstReview.save();
    Review secondReview = new Review("It was bad", 2, "Boring boring BORING!", 1);
    secondReview.save();
    assertEquals(Review.find(secondReview.getId()), secondReview);
  }

}
