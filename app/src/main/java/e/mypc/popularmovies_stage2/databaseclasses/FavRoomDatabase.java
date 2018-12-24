package e.mypc.popularmovies_stage2.databaseclasses;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavMovies.class}, version = 1,exportSchema = false)
public abstract class FavRoomDatabase extends RoomDatabase {
    public static final String DBNAME="fav_database";


    public abstract FavDAO favDAO();

    private static FavRoomDatabase INS;

    public static FavRoomDatabase getDatabase(final Context context) {
        if (INS == null) {
            synchronized (FavRoomDatabase.class) {
                if (INS == null) {
                    INS = Room.databaseBuilder(context.getApplicationContext(),
                            FavRoomDatabase.class, DBNAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INS;
    }
}
