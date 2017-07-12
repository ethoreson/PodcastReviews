import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

  get("/", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("template", "templates/index.vtl");
    model.put("podcasts", Podcast.all());
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    String name = request.queryParams("name");
    String genre = request.queryParams("podcastGenre");
    String description = request.queryParams("description");
    Podcast newPodcast = new Podcast(name, genre, description);
    newPodcast.save();
    response.redirect("/");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Podcast podcast = Podcast.find(Integer.parseInt(request.params(":id")));
    model.put("podcast", podcast);
    model.put("reviews", podcast.getReviews());
    model.put("template", "templates/podcast.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Podcast podcast = Podcast.find(Integer.parseInt(request.params(":id")));
    int podcastId = podcast.getId();
    String title = request.queryParams("title");
    int rating = Integer.parseInt(request.queryParams("reviewRating"));
    String description = request.queryParams("description");
    Review newReview = new Review(title, rating, description, podcastId);
    newReview.save();
    model.put("podcast", podcast);
    model.put("template", "templates/podcast.vtl");
    String url = String.format("/%d", podcastId);
    response.redirect(url);
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  }
}
