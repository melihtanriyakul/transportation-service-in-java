import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The City class has three attributes:
 * index, name and routes which contain
 * the routes to the nearby cities and
 * which transportation type can be used.
 */
class City {
    private int index;
    private String name;
    private Map<City, List<Character>> routes;

    /**
     * Class constructor.
     */
    City(String name, int num) {
        this.name = name;
        index = num;
        routes = new HashMap<>();
    }

    int getIndex() {
        return index;
    }

    String getName() {
        return name;
    }

    Map<City, List<Character>> getRoutes() {
        return routes;
    }
}
