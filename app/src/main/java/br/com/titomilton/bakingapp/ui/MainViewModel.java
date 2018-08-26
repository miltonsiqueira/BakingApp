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
}