package e.mypc.popularmovies_stage2.databaseclasses;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class FavMovies {
    @PrimaryKey
    @NonNull
    public int fid;

    public String ftitle;
    public String fvote_average;
    public String fposter_path;
    public String frelease_date;
    public String foverview;

    public FavMovies(int fid, String ftitle, String fvote_average, String fposter_path, String frelease_date, String foverview) {
        this.fid = fid;
        this.ftitle = ftitle;
        this.fvote_average = fvote_average;
        this.fposter_path = fposter_path;
        this.frelease_date = frelease_date;
        this.foverview = foverview;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFtitle() {
        return ftitle;
    }

    public void setFtitle(String ftitle) {
        this.ftitle = ftitle;
    }

    public String getFvote_average() {
        return fvote_average;
    }

    public void setFvote_average(String fvote_average) {
        this.fvote_average = fvote_average;
    }

    public String getFposter_path() {
        return fposter_path;
    }

    public void setFposter_path(String fposter_path) {
        this.fposter_path = fposter_path;
    }

    public String getFrelease_date() {
        return frelease_date;
    }

    public void setFrelease_date(String frelease_date) {
        this.frelease_date = frelease_date;
    }

    public String getFoverview() {
        return foverview;
    }

    public void setFoverview(String foverview) {
        this.foverview = foverview;
    }
}
