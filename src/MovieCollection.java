import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection {
  private ArrayList<Movie> movies;
  private final ArrayList<String> ACTORS;
  private final ArrayList<String> GENRES;
  private Scanner scanner;

  public MovieCollection(String fileName) {
    importMovieList(fileName);
    ACTORS = categoriesFromMovies("cast");
    GENRES = categoriesFromMovies("genre");
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (menuOption.equals("t")) {
        searchTitles();
      } else if (menuOption.equals("c")) {
        searchCast();
      } else if (menuOption.equals("k")) {
        searchKeywords();
      } else if (menuOption.equals("g")) {
        listGenres();
      } else if (menuOption.equals("r")) {
        listHighestRated();
      } else if (menuOption.equals("h")) {
        listHighestRevenue();
      } else if (menuOption.equals("q")) {
        System.out.println("Goodbye!");
      } else {
        System.out.println("Invalid choice!");
      }
    }
  }

  private void importMovieList(String fileName) {
    try {
      movies = new ArrayList<Movie>();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        // get data from the columns in the current row and split into an array
        String[] movieFromCSV = line.split(",");

        /* TASK 1: FINISH THE CODE BELOW */
        // using the movieFromCSV array,
        // obtain the title, cast, director, tagline,
        // keywords, overview, runtime (int), genres,
        // user rating (double), year (int), and revenue (int)
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);

        // create a Movie object with the row data:
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        // add the Movie to movies:
        movies.add(nextMovie);
      }
      bufferedReader.close();
    } catch(IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // list results and let user choose a movie to learn about
    listMovies(results);
  }

  private void sortStrings(ArrayList<String> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      String temp = listToSort.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortResults(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortMoviesByRevenue(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      int tempRevenue = temp.getRevenue();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempRevenue - listToSort.get(possibleIndex - 1).getRevenue() > 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortMoviesByRating(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      double tempRating = temp.getUserRating();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempRating - listToSort.get(possibleIndex - 1).getUserRating() > 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords() {
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieKeywords = movies.get(i).getKeywords();
      movieKeywords = movieKeywords.toLowerCase();

      if (movieKeywords.indexOf(searchTerm) != -1) {
        //add the Movie object to the results list
        results.add(movies.get(i));
      }
    }

    // list results and let user choose a movie to learn about
    listMovies(results);
  }

  private void searchCast() {
    System.out.print("Enter a person to search for (first or last name): ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<String> people = new ArrayList<>();

    // search through ALL movies for cast members matching searchTerm
    for (int i = 0; i < ACTORS.size(); i++) {
      String actor = ACTORS.get(i);

      if (actor.toLowerCase().indexOf(searchTerm) != -1) {
        people.add(actor);
      }
    }

    for (int i = 0; i < people.size(); i++) {
      System.out.println((i+1) + ". " + people.get(i));
    }

    System.out.println("Which would you like to see all movies for?");
    System.out.print("Enter number: ");
    int actorNum = scanner.nextInt();
    scanner.nextLine();

    String actor = people.get(actorNum - 1).toLowerCase();
    ArrayList<Movie> results = new ArrayList<>();
    for (Movie movie : movies) {
      String movieCast = movie.getCast().toLowerCase();

      if (movieCast.indexOf(actor) != -1) {
        //add the Movie object to the results list
        results.add(movie);
      }
    }

    // list results and let user choose a movie to learn about
    listMovies(results);
  }
  
  private void listGenres() {
    for (int i = 0; i < GENRES.size(); i++) {
      System.out.println((i+1) + ". " + GENRES.get(i));
    }

    System.out.println("Which would you like to see all movies for?");
    System.out.print("Enter number: ");
    int genreNum = scanner.nextInt();
    scanner.nextLine();

    String genre = GENRES.get(genreNum - 1).toLowerCase();
    ArrayList<Movie> results = new ArrayList<>();
    for (Movie movie : movies) {
      String movieGenre = movie.getGenres().toLowerCase();

      if (movieGenre.indexOf(genre) != -1) {
        //add the Movie object to the results list
        results.add(movie);
      }
    }

    // list results and let user choose a movie to learn about
    listMovies(results);
  }
  
  private void listHighestRated() {
    listHighest("rating");
  }
  
  private void listHighestRevenue() {
    listHighest("revenue");
  }

  private void listHighest(String category) {
    ArrayList<Movie> results = top50(category);
    listMovies(results, category);
  }

  private void listMovies(ArrayList<Movie> results, String category) {
    if (results.size() > 0) {
      // display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        // will print rating or revenue depending on category argument
        String info = switch (category) {
          case "rating" -> Double.toString(results.get(i).getUserRating());
          case "revenue" -> Integer.toString(results.get(i).getRevenue());
          default -> null;
        };
        System.out.println("" + choiceNum + ". " + title + ": " + info);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  // overloaded method, sort by alphabet if no category for sorting is provided
  private void listMovies(ArrayList<Movie> results) {
    if (results.size() > 0) {
      // sort the results by title
        sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private ArrayList<String> categoriesFromMovies(String category) {
    ArrayList<String> categories = new ArrayList<>();
    for (int i = 0; i < movies.size(); i++) {
      String data = switch (category) {
        case "cast" -> movies.get(i).getCast();
        case "genre" -> movies.get(i).getGenres();
        default -> null;
      };
      String[] categoryList = data.split("\\|");

      for (String c : categoryList) {
        if (categories.indexOf(c) == -1) {
          categories.add(c);
        }
      }
    }
    sortStrings(categories);
    return categories;
  }

  private ArrayList<Movie> top50(String category) {
    ArrayList<Movie> results = new ArrayList<>();
    switch (category) {
      case "rating" -> sortMoviesByRating(movies);
      case "revenue" -> sortMoviesByRevenue(movies);
    }
    for (int i = 0; i < 50; i++) {
      results.add(movies.get(i));
    }
    return results;
  }
}