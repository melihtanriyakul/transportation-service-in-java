import java.util.ArrayList;
import java.util.List;

/**
 * The Operations class has four different methods
 * to handle the cases of four different queries,
 * namely Q1, Q2, Q3, Q4.
 */
class Operations {

    /**
     * Takes all of the routes between the designated
     * source and destination cities as a parameter
     * and filters the routes with respect to the
     * designated transportation type and how many
     * times that transportation should be used and
     * returns the filtered routes.
     *
     * @param allRoutes          all of the routes between the designated cities
     * @param numOfType          how many times the designated transportation type should be used
     * @param transportationType the designated transportation type
     * @return returnRoutes         the list of routes, filtered through all routes
     */
    static ArrayList<String> handleQ1(List<String> allRoutes, int numOfType, String transportationType) {
        ArrayList<String> returnRoutes = new ArrayList<>();

        for (String route : allRoutes) {
            int count = (route.length() - route.replaceAll(transportationType + ",", "").length()) / 2;

            if (count >= numOfType) {
                returnRoutes.add(route);
            }
        }
        return returnRoutes;
    }

    /**
     * Takes all of the routes between the designated
     * source and destination cities as a parameter
     * and filters the routes with respect to the
     * designated intermediate city and returns
     * the filtered routes.
     *
     * @param allRoutes        all of the routes between the designated cities
     * @param intermediateCity the designated intermediate city that the route should contain
     * @return returnRoutes         the list of routes, filtered through all routes
     */
    static ArrayList<String> handleQ2(List<String> allRoutes, String intermediateCity) {
        ArrayList<String> returnRoutes = new ArrayList<>();

        for (String route : allRoutes) {
            if (route.contains(intermediateCity)) {
                returnRoutes.add(route);
            }
        }

        return returnRoutes;
    }

    /**
     * Takes all of the routes between the designated
     * source and destination cities as a parameter
     * and filters the routes so that only the routes
     * that are taken with the designated transportation
     * type and returns the filtered routes.
     *
     * @param allRoutes          all of the routes between the designated cities
     * @param transportationType the designated transportation type
     * @return returnRoutes         the list of routes, filtered through all routes
     */
    static ArrayList<String> handleQ3(List<String> allRoutes, String transportationType) {
        ArrayList<String> returnRoutes = new ArrayList<>();

        for (String route : allRoutes) {
            String[] splitStr = route.split(" ");
            int typeCount = splitStr.length / 2;
            int givenTypeCount = countOccurences(route, transportationType);
            if (typeCount == givenTypeCount) {
                returnRoutes.add(route);
            }
        }

        return returnRoutes;
    }

    /**
     * Takes all of the routes between the designated
     * source and destination cities as a parameter
     * and filters the routes with respect to the
     * designated transportation types and how many
     * times these types should be used from source
     * city to the destination city. And finally
     * return these filtered routes.
     *
     * @param allRoutes all of the routes between the designated cities
     * @param splitLine a line contains different transportation types and the numbers of use
     * @return returnRoutes         the list of routes, filtered through all routes
     */
    static ArrayList<String> handleQ4(List<String> allRoutes, String[] splitLine) {
        ArrayList<String> returnRoutes = new ArrayList<>();

        String firstType = splitLine[3].split("")[0];
        int firstCount = Integer.parseInt(splitLine[3].split("")[1]);
        String secondType = splitLine[4].split("")[0];
        int secondCount = Integer.parseInt(splitLine[4].split("")[1]);
        String thirdType = splitLine[5].split("")[0];
        int thirdCount = Integer.parseInt(splitLine[5].split("")[1]);

        for (String route : allRoutes) {
            if (firstCount == countOccurences(route, firstType) && secondCount == countOccurences(route, secondType)
                    && thirdCount == countOccurences(route, thirdType)) {
                returnRoutes.add(route);
            }
        }

        return returnRoutes;
    }

    /**
     * Takes two strings as parameters and
     * counts how many times the second string
     * occurs in the first string and returns
     * the number.
     *
     * @param str     supposedly a sentence or a paragraph
     * @param keyword a word to be search in the above string
     * @return count    number of occurrences of the second string in the first string
     */
    private static int countOccurences(String str, String keyword) {
        String[] words = str.split(", ");

        int count = 0;
        for (String word : words) {
            if (keyword.equals(word)) {
                count++;
            }
        }

        return count;
    }
}
