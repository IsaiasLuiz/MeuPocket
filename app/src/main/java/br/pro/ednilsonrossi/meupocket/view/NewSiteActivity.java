package br.pro.ednilsonrossi.meupocket.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.dao.SiteDao;
import br.pro.ednilsonrossi.meupocket.model.Site;
import br.pro.ednilsonrossi.meupocket.utils.UrlUtils;

public class NewSiteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText titleEditText;
    private EditText urlEditText;
    private CheckBox favoriteCheckBox;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_site);
        titleEditText = findViewById(R.id.title_edit_text);
        urlEditText = findViewById(R.id.url_edit_text);
        favoriteCheckBox = findViewById(R.id.checkbox_favorite);
        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(saveButton == v) {
            String title = titleEditText.getText().toString();
            String url = urlEditText.getText().toString();
            if(title.isEmpty() || url.isEmpty()) {
                Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
                return;
            }
            Site site = new Site(title, UrlUtils.corrigeEndereco(url));
            if(favoriteCheckBox.isChecked()) {
                site.doFavotite();
            }
            SiteDao.insert(getApplicationContext(), site);
            finish();
        }
    }
}
