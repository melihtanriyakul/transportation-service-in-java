import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * The TransportationMap class has an attribute
 * and a couple of methods. Its attribute is
 * cityList and it holds the list of the cities
 * in the map. The methods of the class consist
 * of 'addRoute', 'printAllRoutes', recPrintAllRoutes,
 * and 'printCities, these methods can be used to
 * builds the routes between the cities, then also
 * print these routes and print the cities in the map.
 */
class TransportationMap {
    private ArrayList<City> cityList;

    /**
     * Class constructor.
     */
    TransportationMap(ArrayList<String> cities) {
        cityList = new ArrayList<>();
        int numOfCities = 0;

        for (String city : cities) {
            cityList.add(new City(city, numOfCities));
            numOfCities++;
        }
    }

    ArrayList<City> getCityList() {
        return cityList;
    }

    /**
     * Takes two cities and a transportation type
     * as parameters as parameters and build a route
     * between these cities.
     */
    void addRoute(City firstCity, City secondCity, char type) {
        try {
            firstCity.getRoutes().get(secondCity).add(type);
        } catch (Exception e) {
            ArrayList<Character> currentTypes;
            currentTypes = new ArrayList<>();
            currentTypes.add(type);
            firstCity.getRoutes().put(secondCity, currentTypes);
        }
    }

    /**
     * Takes a source city and a destination city as
     * parameters and finds all of the routes
     * between these two cities and insert them
     * into an arraylist an returns it.
     *
     * @return allRoutes    all routes between the source and destination cities
     */
    ArrayList<String> printAllRoutes(City srcCity, City desCity) {
        boolean[] isVisited = new boolean[cityList.size()];
        ArrayList<String> routeList = new ArrayList<>();
        ArrayList<String> allRoutes = new ArrayList<>();

        routeList.add(srcCity.getName());

        recPrintAllRoutes(srcCity, desCity, isVisited, routeList, allRoutes);

        return allRoutes;
    }

    /**
     * A auxiliary method, takes the source and
     * destination cities and necessary lists
     * as parameters and finds all different
     * routes with all the different transportation
     * between these two cities and insert these
     * routes into the array list.
     *
     * @param srcCity   the source city
     * @param desCity   the destination city
     * @param isVisited the boolean array to designate the cities which already visited
     * @param routeList the array list to contain the cities and transportation types from the source to destination
     * @param allRoutes the array list to contain all of the different routes between the source and destination
     */
    private void recPrintAllRoutes(City srcCity, City desCity, boolean[] isVisited, ArrayList<String> routeList, ArrayList<String> allRoutes) {
        isVisited[srcCity.getIndex()] = true;
        String currentRoute;

        if (srcCity.equals(desCity)) {
            currentRoute = String.join(" ", routeList);
            String[] parseStr = currentRoute.split(" ");
            currentRoute = String.join(", ", parseStr);
            allRoutes.add(currentRoute);

            isVisited[srcCity.getIndex()] = false;
            return;
        }

        for (City currentCity : srcCity.getRoutes().keySet()) {
            if (!isVisited[currentCity.getIndex()]) {
                if (srcCity.getRoutes().get(currentCity).contains('A')) {
                    routeList.add("A " + currentCity.getName());
                    recPrintAllRoutes(currentCity, desCity, isVisited, routeList, allRoutes);

                    routeList.remove("A " + currentCity.getName());
                }

                if (srcCity.getRoutes().get(currentCity).contains('H')) {
                    routeList.add("H " + currentCity.getName());
                    recPrintAllRoutes(currentCity, desCity, isVisited, routeList, allRoutes);

                    routeList.remove("H " + currentCity.getName());
                }

                if (srcCity.getRoutes().get(currentCity).contains('R')) {
                    routeList.add("R " + currentCity.getName());
                    recPrintAllRoutes(currentCity, desCity, isVisited, routeList, allRoutes);

                    routeList.remove("R " + currentCity.getName());
                }

            }
        }

        isVisited[srcCity.getIndex()] = false;
    }

    /**
     * Takes an output file and prints all of
     * the cities in the map with the other
     * cities that can be accessible from them.
     *
     * @param output the output file
     */
    void printCities(PrintWriter output) {
        for (City currentCity : this.cityList) {
            ArrayList<String> destinationCities = new ArrayList<>();
            currentCity.getRoutes().forEach((city, type) -> destinationCities.add(city.getName()));

            String strDes = String.join(" ", destinationCities);
            output.println(currentCity.getName() + " --> " + strDes);
        }
    }
}
