package franklinvasquez.ufps.platzigram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thebluealliance.spectrum.SpectrumPalette;

import java.util.zip.Inflater;

import franklinvasquez.ufps.platzigram.model.Note;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity implements EditorView {

    EditText txt_title;
    EditText txt_note;
    ProgressDialog progressDialog;
    ApiInterface apiInterface;
    Button button;
    SpectrumPalette palette;
    EditorPresenter presenter;
    int color,id;
    String title, note;

    Menu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        showToolbar("save", false);
        txt_title = (EditText) findViewById(R.id.title) ;
        txt_note = (EditText) findViewById(R.id.note) ;
        palette = (SpectrumPalette) findViewById(R.id.palette);

        palette.setOnColorSelectedListener(clr -> color = clr);

        palette.setSelectedColor(getResources().getColor(R.color.white));
        color = getResources().getColor(R.color.white);


        presenter = new EditorPresenter(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        note = intent.getStringExtra("note");
        color = intent.getIntExtra("color", 0);

        setDataFromIntentExtra();

        button = (Button) findViewById(R.id.save_retrofit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = txt_title.getText().toString().trim();
                String note = txt_note.getText().toString().trim();
                int colores = color;

                if(title.isEmpty()){
                    txt_title.setError("Please enter a title");
                }else if(note.isEmpty()){
                    txt_note.setError("Please enter a note");
                }else{
                    presenter.saveNote(title,note,colores);
                }
            }
        });



    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        actionMenu = menu;

        if (id != 0) {
            actionMenu.findItem(R.id.edit).setVisible(true);
            actionMenu.findItem(R.id.delete).setVisible(true);
            actionMenu.findItem(R.id.save).setVisible(false);
            actionMenu.findItem(R.id.update).setVisible(false);
        } else {
            actionMenu.findItem(R.id.edit).setVisible(false);
            actionMenu.findItem(R.id.delete).setVisible(false);
            actionMenu.findItem(R.id.save).setVisible(true);
            actionMenu.findItem(R.id.update).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String title = txt_title.getText().toString().trim();
        String note = txt_note.getText().toString().trim();
        int colores = this.color;

        switch (item.getItemId()){
            case R.id.save:
                if(title.isEmpty()){
                    txt_title.setError("Please enter a title");
                }else if(note.isEmpty()){
                    txt_note.setError("Please enter a note");
                }else{
                    presenter.saveNote(title,note,colores);
                }
                return true;
            case R.id.edit:

                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);

                return true;

            case R.id.update:
                //Update

                if (title.isEmpty()) {
                    txt_title.setError("Please enter a title");
                } else if (note.isEmpty()) {
                    txt_note.setError("Please enter a note");
                } else {
                    presenter.updateNote(id, title, note, colores);
                }

                return true;
            case R.id.delete:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Confirm !");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setNegativeButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    presenter.deleteNote(id);
                });
                alertDialog.setPositiveButton("Cencel",
                        (dialog, which) -> dialog.dismiss());

                alertDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote(final String title, final String note, final int color){

        progressDialog.show();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Note> call = apiInterface.saveNote(title,note,color);

        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(@NonNull Call<Note> call, @NonNull Response<Note> response) {
                progressDialog.dismiss();

                if(response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if(success){
                        Toast.makeText(getApplicationContext(),"exito",Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Note> call,@NonNull Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    private void setDataFromIntentExtra() {

        if (id != 0) {
            txt_title.setText(title);
            txt_note.setText(note);
            palette.setSelectedColor(color);

            getSupportActionBar().setTitle("Update Note");
            readMode();
        } else {
            palette.setSelectedColor(getResources().getColor(R.color.white));
            color = getResources().getColor(R.color.white);
            editMode();
        }

    }

    private void editMode() {
        txt_title.setFocusableInTouchMode(true);
        txt_note.setFocusableInTouchMode(true);
        palette.setEnabled(true);
    }

    private void readMode() {
        txt_title.setFocusableInTouchMode(false);
        txt_note.setFocusableInTouchMode(false);
        txt_title.setFocusable(false);
        txt_note.setFocusable(false);
        palette.setEnabled(false);
    }


}
