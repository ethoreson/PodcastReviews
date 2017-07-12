import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class PodcastTest {

  Podcast myPodcast = new Podcast("Storyville", "Storytelling", "A diverse collection of short stories");

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
  public void Podcast_instantiatesCorrectly_true() {

    assertEquals(true, myPodcast instanceof Podcast);
  }

  @Test
  public void getName_retrievesPodcastsName_true() {
    assertEquals("Storyville", myPodcast.getName());
  }

  @Test
  public void getGenre_retrievesPodcastsGenre_true() {
    assertEquals("Storytelling", myPodcast.getGenre());
  }

  @Test
  public void getDescription_retrievesPodcastsDescription_true() {
    assertEquals("A diverse collection of short stories", myPodcast.getDescription());
  }

  @Test
  public void equals_returnsTrueIfEntriesAretheSame() {
    Podcast firstPodcast = new Podcast("Storyville", "Storytelling", "A diverse collection of short stories");
    Podcast secondPodcast = new Podcast("Storyville", "Storytelling", "A diverse collection of short stories");
    assertTrue(firstPodcast.equals(secondPodcast));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    myPodcast.save();
    assertTrue(Podcast.all().get(0).equals(myPodcast));
  }

  @Test
  public void all_returnsAllInstancesOfPodcast_true() {
    Podcast firstPodcast = new Podcast("Storyville", "Storytelling", "A diverse collection of short stories");
    firstPodcast.save();
    Podcast secondPodcast = new Podcast("RadioLab", "News", "A science podcast");
    secondPodcast.save();
    assertEquals(true, Podcast.all().get(0).equals(firstPodcast));
    assertEquals(true, Podcast.all().get(1).equals(secondPodcast));
  }

  @Test
  public void save_assignsIdToObject() {
    Podcast myPodcast = new Podcast("RadioLab", "News", "A science podcast");
    myPodcast.save();
    Podcast savedPodcast = Podcast.all().get(0);
    assertEquals(myPodcast.getId(), savedPodcast.getId());
  }

  @Test
  public void getId_PodcastsInstantiateWithAnID() {
    Podcast myPodcast = new Podcast("RadioLab", "News", "A science podcast");
    myPodcast.save();
    assertTrue(myPodcast.getId() > 0);
  }

  @Test
  public void find_returnsPodcastWithSameId_secondPodcast() {
    Podcast firstPodcast = new Podcast("Storyville", "Storytelling", "A diverse collection of short stories");
    firstPodcast.save();
    Podcast secondPodcast = new Podcast("RadioLab", "News", "A science podcast");
    secondPodcast.save();
    assertEquals(Podcast.find(secondPodcast.getId()), secondPodcast);
  }

  @Test
  public void getReviews_retrievesALlReviewsFromDatabase_reviewsList() {
    Review firstReview = new Review("I loved it!", 5, "I love the host!", myPodcast.getId());
    firstReview.save();
    Review secondReview = new Review("It was okay", 3, "I didn't understand some of it", myPodcast.getId());
    secondReview.save();
    Review[] reviews = new Review[] { firstReview, secondReview };
    assertTrue(myPodcast.getReviews().containsAll(Arrays.asList(reviews)));
  }

}
