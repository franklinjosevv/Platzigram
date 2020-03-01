package franklinvasquez.ufps.platzigram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
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
    int colores;

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

        palette.setOnColorSelectedListener(clr -> colores = clr);

        palette.setSelectedColor(getResources().getColor(R.color.white));
        colores = getResources().getColor(R.color.white);


        presenter = new EditorPresenter(this);

        button = (Button) findViewById(R.id.save_retrofit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = txt_title.getText().toString().trim();
                String note = txt_note.getText().toString().trim();
                int color = colores;

                if(title.isEmpty()){
                    txt_title.setError("Please enter a title");
                }else if(note.isEmpty()){
                    txt_note.setError("Please enter a note");
                }else{
                    presenter.saveNote(title,note,color);
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:

                String title = txt_title.getText().toString().trim();
                String note = txt_note.getText().toString().trim();
                int color = this.colores;

                if(title.isEmpty()){
                    txt_title.setError("Please enter a title");
                }else if(note.isEmpty()){
                    txt_note.setError("Please enter a note");
                }else{
                    presenter.saveNote(title,note,color);
                }

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
    public void onAddSuccess(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onAddError(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}
