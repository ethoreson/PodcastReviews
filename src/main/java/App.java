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
    String genre = request.queryParams("genre");
    String description = request.queryParams("description");
    Podcast newPodcast = new Podcast(name, genre, description);
    newPodcast.save();
    response.redirect("/");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  }
}
