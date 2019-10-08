package webcrawlers;

import model.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchTenWorstMovies {

    public static void main(String[] args) {

        Document doc, docDetailed = null;


        List<Movie> movies = new ArrayList<>();

        try {
            doc = Jsoup.connect("https://www.imdb.com/chart/bottom")
                    .header("Accept-Language", "en")
                    .get();

            int limit = 0;
            for (Element row : doc.select("table.chart.full-width tr")) {
                if (limit <= 10) {
                        Movie movie = new Movie();
                        movie.setTitle(row.select(".titleColumn a").text());
                        movie.setRating(row.select(".imdbRating").text());
                        movie.setDirectors(row.select(".titleColumn a").attr("title").replace("(dir.)", "").split(","));

                        String link = row.select(".titleColumn a[href]").attr("abs:href");
                        movie.setLink(link);

                        System.out.println(movie);

                        movies.add(movie);

                        limit++;
                }
            }
//                docDetailed = Jsoup.connect(link)
//                        .header("Accept-Language", "en")
//                        .get();
//
//                int limit = 0;
//                for(Element row2: docDetailed.select("table.cast_list tr")) {
//
//                    if(limit <= 15) {
//                        if(row2.select("td:nth-of-type(2)").text().equals("")) {
//                            continue;
//                        }else{
//                            movie.setActor(row2.select("td:nth-of-type(2)").text());
//                        }
//                    } else {
//                        System.out.println("fim");
//                    }
//
//                    limit++;
//
//                    System.out.println(movie.getActor());
//                }


            System.out.println(movies);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
