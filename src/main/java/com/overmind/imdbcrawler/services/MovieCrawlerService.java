package com.overmind.imdbcrawler.services;

import com.overmind.imdbcrawler.model.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieCrawlerService {

    public List<Movie> searchImdbWorstMovies() {

        Document doc, docDetailed, docComment = null;
        List<Movie> movies = new ArrayList<>();


        /***
         * BUSCANDO TÍTULO, NOTA E DIRETORES DOS FILMES
         * **/

        try {
            doc = Jsoup.connect("https://www.imdb.com/chart/bottom")
                    .header("Accept-Language", "en")
                    .get();

            int limit = 0;
            for (Element row : doc.select("table.chart.full-width tr")) {
                if (limit < 10) {
                    if (row.select("td:nth-of-type(2)").text().equals("")) {
                        continue;
                    }
                    Movie movie = new Movie();
                    movie.setTitle(row.select(".titleColumn a").text());
                    movie.setRating(row.select(".imdbRating").text());
                    movie.setDirectors(row.select(".titleColumn a").attr("title").replace("(dir.)", "").split(","));

                    String link = row.select(".titleColumn a[href]").attr("abs:href");
                    movie.setLink(link);

                    movies.add(movie);

                    limit++;
                }
            }

            /***
             * BUSCANDO ATORES PRINCIPAIS DOS FILMES (15 LISTADOS NA PAGINA PRINCIPAL DO FILME)
             * **/

            for (Movie movie : movies) {

                List<String> listActors = new ArrayList<>();

                docDetailed = Jsoup.connect(movie.getLink())
                        .header("Accept-Language", "en")
                        .get();

                int lim = 0;
                for (Element row2 : docDetailed.select("table.cast_list tr")) {

                    if (lim <= 15) {
                        if (row2.select("td:nth-of-type(2)").text().equals("")) {
                            continue;
                        }
                        String actor = row2.select("td:nth-of-type(2)").text();
                        listActors.add(actor);

                    }

                    lim++;

                    movie.setActors(listActors);

                }

                /**
                 * BUSCANDO COMENTARIO POSITIVO
                 * */

                Elements info = docDetailed.select("div.user-comments > a[href]:nth-of-type(2)");
                String url = info.attr("abs:href");

                String urlFormatted = url.replace("?ref_=tt_urv", "?sort=userRating");

                docComment = Jsoup.connect(urlFormatted)
                        .header("Accept-Language", "en")
                        .get();

                String positiveComment = docComment.getElementsByClass("text show-more__control").text();
                movie.setPositiveComment(positiveComment);

            }

            return movies;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
