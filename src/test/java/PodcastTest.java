import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PodcastTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/podcast_review_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM podcasts *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Podcast_instantiatesCorrectly_true() {
    Podcast myPodcast = new Podcast("Storyville", "Storytelling", "A diverse collection of short stories");
    assertEquals(true, myPodcast instanceof Podcast);
  }

  @Test
  public void getName_retrievesPodcastsName_true() {
    Podcast myPodcast = new Podcast("Storyville", "Storytelling", "A diverse collection of short stories");
    assertEquals("Storyville", myPodcast.getName());
  }

  @Test
  public void getGenre_retrievesPodcastsGenre_true() {
    Podcast myPodcast = new Podcast("Storyville", "Storytelling", "A diverse collection of short stories");
    assertEquals("Storytelling", myPodcast.getGenre());
  }

  @Test
  public void getDescription_retrievesPodcastsDescription_true() {
    Podcast myPodcast = new Podcast("Storyville", "Storytelling", "A diverse collection of short stories");
    assertEquals("A diverse collection of short stories", myPodcast.getDescription());
  }

  @Test
  public void equals_returnsTrueIfEntriesAretheSame() {
    Podcast firstPodcast = new Podcast("Storyville", "Storytelling", "A diverse collection of short stories");
    Podcast secondPodcast = new Podcast("Storyville", "Storytelling", "A diverse collection of short stories");
    assertTrue(firstPodcast.equals(secondPodcast));
  }

}
