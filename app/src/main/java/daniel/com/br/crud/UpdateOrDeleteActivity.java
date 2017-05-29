package daniel.com.br.crud;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import daniel.com.br.crud.database.BookDaoSQLite;
import daniel.com.br.crud.model.Book;

public class UpdateOrDeleteActivity extends AppCompatActivity {

    //widgets
    private EditText txtTitle, txtAuthor;
    private Button btnUpdate, btnDelete, btnBack;

    private Context context;
    private Book activityBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_or_delete);

        this.context = this;

        //getting information from the intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        activityBook = new Book(id,title,author);

        //getting widgets from view
        //adding current information from the book into the textfields
        txtTitle = (EditText)findViewById(R.id.txtTitle);
        txtTitle.setText(activityBook.getTitle());
        txtAuthor = (EditText)findViewById(R.id.txtAuthor);
        txtAuthor.setText(activityBook.getAuthor());

        //setting buttons actions
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new BtnUpdateOnClickListener());
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new BtnDeleteOnClickListener());
        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new BtnBackOnClickListener());
    }

    private class BtnUpdateOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (areFieldsValid()){
                //if all the fields were filled correctly...

                //get book's new information and adding it to the activity book
                activityBook.setTitle(txtTitle.getText().toString());
                activityBook.setAuthor(txtAuthor.getText().toString());

                //update it on the database
                new BookDaoSQLite(context).update(activityBook,activityBook.getId());

                //show message
                String messageToast = "Book updated sucessfully";
                Toast.makeText(context,messageToast,Toast.LENGTH_SHORT);

                //going back to the previous screen
                Intent newMainActivity = new Intent(context,MainActivity.class);
                context.startActivity(newMainActivity);

            }
        }
    }

    private class BtnDeleteOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

        }
    }

    private class BtnBackOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent mainActivityIntent = new Intent(UpdateOrDeleteActivity.this,MainActivity.class);
            UpdateOrDeleteActivity.this.startActivity(mainActivityIntent);
        }
    }

    //return true if all the fields text are valid
    //also it sets an error message on the txts invalid, so that the user knows
    private boolean areFieldsValid(){
        boolean isValid = true;
        //title
        if (txtTitle.getText().toString().isEmpty()){
            isValid = false;
            txtTitle.setError("New title's field must be filled");
        }
        //author
        if (txtAuthor.getText().toString().isEmpty()){
            isValid = false;
            txtAuthor.setError("New author's field must be filled");
        }

        return isValid;
    }


}