package minhvu.dmt.manhinhdangnhap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {
        EditText edtmanv, edttenv, edtmucluong;
        Button btnthem, btnsua, btnxoa, btntruyvan, btndangxuat, btnClear;
        ListView Lv;
        ArrayList<String> mylist;
        ArrayAdapter<String> myadapter;
        SQLiteDatabase mydatababe; // lưu trữ dữ liệu dưới dạng table (database)
        TextView txtDanhsach;
        RadioButton rbca1, rbca2, rbtot, rbkha;
        String ca = "";String daotao = "";
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Quản Lí Nhân Viên VinMart");
                return super.onCreateOptionsMenu(menu);

        }
        @SuppressLint({"MissingInflatedId", "WrongViewCast"})
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main3);
                Anhxa();
                txtDanhsach.setVisibility(View.INVISIBLE);  // Ẩn Text View Danh sách nhân viên
                mylist = new ArrayList<>(); // tạo 1 mảng array rỗng có tên mylist
                myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mylist); // khởi taok đối tượng Arrayadapter
                // R.layout.simple_list_item_1 là layout đc sử sungjd dể hiển thị danh sách mặc định của androi
                // my list dùng để lưu trữ các phần tử hiển thị lên listView.
                Lv.setAdapter(myadapter); // set adapter cho LV bằng myadapter được tạo ở trên
                mydatababe = openOrCreateDatabase("qlnhanvien.db", MODE_PRIVATE, null); // khởi tạo csdl SQLife  có tên qlnhanvien và truy cập quyền MODE_PRIVATE.

                // Tạo table để chứa dữ liệu
                try {
                        String sql = "CREATE TABLE tbllop(manv TEXT primary key, tennv TEXT, mucluong INTEGER)"; // tạo ra 1 bảng mới tbllop, có các trường dữ liệu manv, tennv...
                        mydatababe.execSQL(sql); // tạo bảng bằng execSQL(sql) trên csdl mydatabase và đối số sẽ là sql đc tạo ở trên
                }
                catch (Exception e) { // nếu tạo bảng không thành thì sẽ exception ra và Log lên Lỗi!!!
                        Log.e("Lỗi!!!", "Table đã tồn tại");
                }

                btnthem.setOnClickListener(new View.OnClickListener() { // Nut them du lieu
                        @Override
                        public void onClick(View view) {
                                String manv = edtmanv.getText().toString().trim();
                                String tennv = edttenv.getText().toString().trim();
                                String mucluoong = edtmucluong.getText().toString().trim();
                                if (manv.length() == 0 || tennv.length() == 0 || mucluoong.isEmpty()){
                                        Toast.makeText(MainActivity3.this, "Vui lòng nhập đủ thông tin!!!", Toast.LENGTH_SHORT).show();
                                }else {
                                        if (rbca1.isChecked()){
                                                ca = "ca 1";
                                        } else if (rbca2.isChecked()){
                                                ca = "ca 2";
                                        };
                                        if (rbtot.isChecked()){
                                                daotao = "Tốt";
                                        }else {
                                                daotao = "khá";
                                        }
                                        ContentValues myvalue = new ContentValues(); // lấy giá trị các manv tennv mucluong lưu vào contentValues
                                        myvalue.put("manv", manv);
                                        myvalue.put("tennv", tennv);
                                        myvalue.put("mucluong", mucluoong + "Vnđ/giờ - " + ca + " - " + daotao);
                                        String msg = "";
                                        if (mydatababe.insert("tbllop", null,myvalue) == -1){ // thêm bản ghi mới bằng insert nếu thành công thì sẽ trả về số thứ tự của bản ghi ngược lại trả về -1
                                                msg = "Thêm nhân viên thất bại!";
                                        }else {
                                                msg = "Thêm nhân viên thành công!";
                                        }
                                        Toast.makeText(MainActivity3.this, msg, Toast.LENGTH_SHORT).show();
                                }
                        }


                });

                btnxoa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                String manv = edtmanv.getText().toString(); //lấy giá trị của manv
                                int n = mydatababe.delete("tbllop", "manv = ?", new String[]{manv}); // sau đó thực hiện delete cột trùng với giá trị manv
                                // nếu xóa thành công n sẽ trả về số thứ tự của bản ghi và ngược lại sẽ là 0
                                String msg = "";
                                if (n == 0){

                                        msg = "Xóa nhân viên không thành công";
                                }else {
                                        msg = n + "Xóa nhân viên thành công!";
                                }
                                Toast.makeText(MainActivity3.this, msg, Toast.LENGTH_SHORT).show();
                        }
                });
                btnsua.setOnClickListener(new View.OnClickListener() { // Sua du lieu
                        @Override
                        public void onClick(View view) {
                                String tennv = edttenv.getText().toString().trim();
                                String mucluong = edtmucluong.getText().toString().trim();
                                String manv = edtmanv.getText().toString().trim();
                                if (manv.length() == 0 || tennv.length() == 0 || mucluong.isEmpty()) {
                                        Toast.makeText(MainActivity3.this, "Vui lòng nhập đủ thông tin để sửa!!!", Toast.LENGTH_SHORT).show();
                                } else {
                                        if (rbca1.isChecked()) {
                                                ca = "ca 1";
                                        } else if (rbca2.isChecked()) {
                                                ca = "ca 2";
                                        }
                                        ;
                                        if (rbtot.isChecked()) {
                                                daotao = "Tốt";
                                        } else {
                                                daotao = "khá";
                                        }
                                        ContentValues myvalue = new ContentValues(); // Contentvalues để nhận giá trị mới nhập
                                        myvalue.put("tennv", tennv);
                                        myvalue.put("mucluong", mucluong + "Vnđ/giờ - " + ca + " - " + daotao);
                                        int n = mydatababe.update("tbllop", myvalue, "manv = ?", new String[]{manv}); //Sau đó up date theo manv vào tbllop
                                        String msg = "";
                                        if (n == 0) {
                                                msg = "Cập nhật dữ liệu thất bại";
                                        } else {
                                                msg = n + " Cập nhật mới!";
                                        }
                                        Toast.makeText(MainActivity3.this, msg, Toast.LENGTH_SHORT).show();
                                }
                        }
                });
                btntruyvan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                txtDanhsach.setVisibility(View.VISIBLE); // CHo hiện lên Text View Danh sách nhân viên
                                mylist.clear(); // xóa dữ lieuj ở mylist trước đó
                                Cursor c = mydatababe.query("tbllop", null, null, null,null, null, null, null); // truy vấn vào database để lấy dữ liệu từ tbllop// muốn lấy ra tất cả các cột nên dùng null
                                c.moveToNext(); // di chuyển đến bản ghi tiếp theo
                                String data = "";
                                while (c.isAfterLast() == false){
                                        data = c.getString(0)+" - " +c.getString(1) + " - " +c.getString(2); // giá trị của từng cột được lấy từ getString // ví dụ 0  là manv, 1 là tennv ...
                                        c.moveToNext();
                                        mylist.add(data);// thêm vào mylist
                                }
                                c.close(); // sau khi xong đóng cursor
                                myadapter.notifyDataSetChanged();// thông báo cho myadapter rằng dữ liệu dã được thay đổi // cần cập nhật lại
                        }
                });
                Lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                // Lấy giá trị của item được click
                                String selectedItem = (String) adapterView.getItemAtPosition(position); // position là đối số của vị trí được click

                                // Tách các giá trị của item thành các thành phần tương ứng
                                String[] itemValues = selectedItem.split(" - ");// tách chuỗi thành các phần tử tương ứng qua dấu -
                                String manv = itemValues[0];
                                String tennv = itemValues[1];
                                String mucluong = itemValues[2]; // gán các giá trị tương ứng theo thứ tự
                                String calamviec = itemValues[3];
                                String daotao = itemValues[4];
                                mucluong = mucluong.replace("Vnđ/giờ", "");// thay thế chuỗi vndgio thành chuỗi rỗng

                                // Set giá trị của các EditText tương ứng

                                edtmanv.setText(manv);
                                edttenv.setText(tennv);  // gán giá trị ngược lại vào EdtText
                                edtmucluong.setText(mucluong);
                                if (calamviec.equals("ca 1")){
                                        rbca1.setChecked(true);
                                } else if (calamviec.equals("ca 2")) {
                                        rbca2.setChecked(true);
                                }
                                if (daotao.equals("Tốt")){
                                        rbtot.setChecked(true);
                                } else if (daotao.equals("khá")) {
                                        rbkha.setChecked(true);
                                }
                        }
                });
                btndangxuat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                finish();
                        }
                });
                btnClear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                edtmanv.setText("");
                                edttenv.setText("");
                                edtmucluong.setText("");
                                rbca1.setChecked(false);
                                rbca2.setChecked(false);
                                rbtot.setChecked(false);
                                rbkha.setChecked(false);
                        }
                });
        }
        private void Anhxa(){
                edtmanv = (EditText) findViewById(R.id.editTextTextPersonName);
                edttenv = (EditText) findViewById(R.id.editTextTextPersonName2);
                edtmucluong = (EditText) findViewById(R.id.editTextTextPersonName3);
                btnthem = (Button) findViewById(R.id.btnthem);
                btnsua = (Button) findViewById(R.id.btncapnhat);
                btnxoa = (Button) findViewById(R.id.btnxoa);
                btntruyvan = findViewById(R.id.btnttruyvan);
                btndangxuat = findViewById(R.id.btndangxuat);
                Lv = (ListView) findViewById(R.id.lv);
                txtDanhsach = findViewById(R.id.txtDanhsachnhanvien);
                rbca1 = findViewById(R.id.Rbca1);
                rbca2 = findViewById(R.id.rbca2);
                rbtot = findViewById(R.id.rbdaotaotot);
                rbkha = findViewById(R.id.rbdaotaokha);
                btnClear = findViewById(R.id.btnclear);


        }

}