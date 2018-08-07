package database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.titomilton.bakingapp.entity.Recipe;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe ORDER BY id")
    List<Recipe> getAll();

    @Insert
    void insert(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Insert
    Integer[] insert(List<Recipe> recipes);
}
