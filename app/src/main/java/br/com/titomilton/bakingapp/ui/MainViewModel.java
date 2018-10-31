package br.com.titomilton.bakingapp.ui;

import android.arch.lifecycle.ViewModel;

import br.com.titomilton.bakingapp.entity.Recipe;
import br.com.titomilton.bakingapp.entity.Step;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainViewModel extends ViewModel {
    private Recipe recipe;
    private Step step;

    public Step getStep() {
        if (this.step == null) {
            if (recipe != null && recipe.getSteps() != null && !recipe.getSteps().isEmpty()) {
                this.step = recipe.getSteps().get(0);
            }
        }
        return this.step;
    }
}