package database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import java.util.List;

import br.com.titomilton.bakingapp.entity.Ingredient;
import br.com.titomilton.bakingapp.entity.Recipe;
import br.com.titomilton.bakingapp.entity.Step;
import br.com.titomilton.bakingapp.utils.AppExecutors;

@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 1, exportSchema = false)
public abstract class AppDatabase  extends RoomDatabase{
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipes";
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if( instance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating the database instance (" + DATABASE_NAME + ")");
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Accessing database");
        return instance;
    }

    public void sync(final Context context, final List<Recipe> recipes){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase instance = getInstance(context);
                instance.clearAllTables();
                instance.recipeDao().insert(recipes);
            }
        });
    }

    public abstract RecipeDao recipeDao();
}
