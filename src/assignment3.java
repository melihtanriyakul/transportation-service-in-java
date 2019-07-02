import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class assignment3 {
    public static void main(String[] args) {
        PrintWriter output = null;
        String transNetworkInput = "";
        String queryInput = "";

        String transNetworkInputFile = args[0];
        String queryInputFile = args[1];
        String outputFile = args[2];

        try {
            /** Reading the input files into strings and creating an output file for the results. */
            transNetworkInput = readFile(transNetworkInputFile);
            queryInput = readFile(queryInputFile);
            output = writeToaFile(outputFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /** Extracting cities from the input file. */
        ArrayList<String> cities = parseCities(transNetworkInput);
        /** Creating the transportation map with the cities and the routes between them. */
        TransportationMap cityGraph = new TransportationMap(cities);
        buildGraph(transNetworkInput, cityGraph);

        /** Indexing the cities for the operations. */
        HashMap<String, Integer> citiesAndIndexes = new HashMap<>();
        for (int i = 0; i < cities.size(); i++) {
            citiesAndIndexes.put(cities.get(i), i);
        }

        /** Handling the queries in the input file. */
        handleOperations(cityGraph, queryInput, citiesAndIndexes, output);

        try {
            if (output != null) {
                output.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Takes an input file and extract the cities and
     * inserts them into an array list and returns it.
     *
     * @param input the input file that contains the cities
     * @return cities   the array list that contains the cities
     */
    private static ArrayList<String> parseCities(String input) {
        ArrayList<String> cities = new ArrayList<>();
        String[] lines = input.split("\\r?\\n");

        for (String line : lines) {
            if (line.length() == 0) {
                break;
            }

            String[] splitLine = line.split(" ");
            if (splitLine.length > 1) {
                cities.add(splitLine[0]);
            }
        }

        return cities;
    }

    /**
     * Takes an input file and a transportation map
     * as parameters and build the routes between
     * the cities with respect to the transportation
     * type.
     *
     * @param cityGraph the transportation map that contains the cities and routes between them
     * @param input     the input file as a string that contains the cities and routes between them
     */
    private static void buildGraph(String input, TransportationMap cityGraph) {
        char currentType = '\0';
        int cityIndex = 0;

        String[] lines = input.split("\\r?\\n");

        for (String line : lines) {
            String[] splitLine = line.split(" ");
            if (splitLine.length == 1) {
                switch (splitLine[0].replaceAll("\\s+", "")) {
                    case "Airway":
                        cityIndex = 0;
                        currentType = 'A';
                        break;
                    case "Highway":
                        cityIndex = 0;
                        currentType = 'H';
                        break;
                    case "Railway":
                        cityIndex = 0;
                        currentType = 'R';
                        break;
                    default:
                        break;
                }
            } else if (splitLine.length > 1) {
                String[] routes = splitLine[1].split("");

                for (int i = 0; i < routes.length; i++) {
                    if (routes[i].equals("1")) {
                        cityGraph.addRoute(cityGraph.getCityList().get(cityIndex), cityGraph.getCityList().get(i), currentType);
                    }
                }
                cityIndex++;
            }
        }
    }

    /**
     * Takes the transportation map, the query
     * input file, an array list that contains
     * cities and their corresponding indexes
     * and an output file. It reads the queries
     * from the input file and calls the four
     * different operations with respect to
     * the keyword in the queries and prints
     * the results into the output file.
     *
     * @param cityGraph        the transportation map that contains the cities and the routes between them
     * @param input            the query file that contains the queries which will be operated
     * @param citiesAndIndexes the hash map that contains the cities and their corresponding indexes
     * @param output           the output file which the results will be written on
     */
    private static void handleOperations(TransportationMap cityGraph, String input, HashMap<String,
            Integer> citiesAndIndexes, PrintWriter output) {
        ArrayList<String> returnRoutes;
        String[] operations = input.split("\\r?\\n");
        String transportationType;
        List<String> allRoutes = new ArrayList<>();

        for (String operation : operations) {
            String[] splitLine = operation.split(" ");

            if (splitLine.length > 1) {
                int srcIndex = citiesAndIndexes.get(splitLine[1]);
                int desIndex = citiesAndIndexes.get(splitLine[2]);

                City srcCity = cityGraph.getCityList().get(srcIndex);
                City desCity = cityGraph.getCityList().get(desIndex);

                allRoutes = cityGraph.printAllRoutes(srcCity, desCity);
            }

            switch (splitLine[0]) {
                case "Q1":
                    int numOfType = Integer.parseInt(splitLine[3]);
                    output.println(String.join(", ", splitLine));

                    transportationType = splitLine[4];
                    returnRoutes = Operations.handleQ1(allRoutes, numOfType, transportationType);

                    if (returnRoutes.size() > 0) {
                        for (String route : returnRoutes) {
                            output.println(route);
                        }
                    } else {
                        output.println("There is no way!");
                    }

                    break;
                case "Q2":
                    output.println(String.join(", ", splitLine));

                    String intermediateCity = splitLine[3];
                    returnRoutes = Operations.handleQ2(allRoutes, intermediateCity);

                    if (returnRoutes.size() > 0) {
                        for (String route : returnRoutes) {
                            output.println(route);
                        }
                    } else {
                        output.println("There is no way!");
                    }

                    break;
                case "Q3":
                    output.println(String.join(", ", splitLine));

                    transportationType = splitLine[3];
                    returnRoutes = Operations.handleQ3(allRoutes, transportationType);

                    if (returnRoutes.size() > 0) {
                        for (String route : returnRoutes) {
                            output.println(route);
                        }
                    } else {
                        output.println("There is no way!");
                    }

                    break;
                case "Q4":
                    output.println(String.join(", ", splitLine));
                    returnRoutes = Operations.handleQ4(allRoutes, splitLine);

                    if (returnRoutes.size() > 0) {
                        for (String route : returnRoutes) {
                            output.println(route);
                        }
                    } else {
                        output.println("There is no way!");
                    }

                    break;
                case "PRINTGRAPH":
                    output.println(String.join(", ", splitLine));
                    cityGraph.printCities(output);

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Reads the given file into a String.
     */
    private static String readFile(String fileName) throws Exception {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    /**
     * Opens the given output file. If there is no file
     * with the given file name then creates a new one.
     */
    private static PrintWriter writeToaFile(String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter buffWriter = new BufferedWriter(fw);

            return new PrintWriter(buffWriter);
        } catch (IOException e) {
            return null;
        }
    }
}
