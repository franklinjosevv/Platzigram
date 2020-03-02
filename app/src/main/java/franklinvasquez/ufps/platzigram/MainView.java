package franklinvasquez.ufps.platzigram;

import java.util.List;

import franklinvasquez.ufps.platzigram.model.Note;

public interface MainView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<Note> notes);
    void onErrorLoading(String message);

}
