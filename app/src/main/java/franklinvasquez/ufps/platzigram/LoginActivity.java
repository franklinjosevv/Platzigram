package franklinvasquez.ufps.platzigram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.textoEjemplo) TextView textoEjemplo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        textoEjemplo.setText("Jose Villar");
    }

    @OnClick(R.id.textoEjemplo) void submit() {
        // TODO call server...
        Toast.makeText(this, "probar cambios", Toast.LENGTH_SHORT).show();
        textoEjemplo.setText("Franklin Jose Vasquez Villar");
    }

}
