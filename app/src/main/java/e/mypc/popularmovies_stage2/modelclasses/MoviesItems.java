package e.mypc.popularmovies_stage2.modelclasses;

public class MoviesItems {
    public int id;
    public String title;
    public String vote_average;
    public String poster_path;
    public String release_date;
    public String overview;

    public MoviesItems(int id, String title, String vote_average, String poster_path, String release_date, String overview) {
        this.id = id;
        this.title = title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return "http://image.tmdb.org/t/p/w185"+poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
