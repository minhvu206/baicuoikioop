package minhvu.dmt.manhinhdangnhap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btndangnhap;
    EditText edtusername, edtpassword;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Quản Lí Nhân Viên VinMart");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btndangnhap = findViewById(R.id.btndangnhap);
        edtusername = findViewById(R.id.username);
        edtpassword = findViewById(R.id.password);
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtusername.getText().toString().equals("Admin") && edtpassword.getText().toString().equals("112")){
                    Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Bạn đã đăng nhập thành công!!!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MainActivity.this, "Bạn đã sai Tên đăng nhập hoặc mật khẩu. Vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}