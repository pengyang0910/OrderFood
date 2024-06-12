package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.orderfood.activity.BusinessActivity;
import com.example.orderfood.activity.BusinessCommentActivity;
import com.example.orderfood.activity.BusinessMyActivity;
import com.example.orderfood.activity.RegisteredBusiness;
import com.example.orderfood.activity.RegisteredUsers;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.db.DBUtil;
import com.example.orderfood.user.activity.BuyFoodActivity;
import com.example.orderfood.user.activity.UserAddressActivity;
import com.example.orderfood.user.activity.UserManageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //调用连接数据库
        DBUtil dbUtil=new DBUtil(MainActivity.this);
        SQLiteDatabase db = dbUtil.getWritableDatabase();//获取到得数据库连接
        DBUtil.db=db;
        //进行加载内容

        //进行数据共享
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sharedPreferences.edit();
//        edit.putString("account","admin");
//        edit.apply();



        //调试其他界面
        //Intent intent=new Intent(MainActivity.this, AddGoodActivity.class);
        //Intent intent=new Intent(MainActivity.this, BusinessActivity.class);
       //Intent intent=new Intent(MainActivity.this, BusinessCommentActivity.class);
//        Intent intent=new Intent(MainActivity.this, UserAddressActivity.class);
//////        Intent intent=new Intent(MainActivity.this, BuyFoodActivity.class);
////        intent.putExtra("business","root");
//        startActivity(intent);






        //让默认选择的是商家
        RadioButton sj=this.findViewById(R.id.login_sj);
        sj.setChecked(true);

        //打开注册商机界面
        Button zcsj=this.findViewById(R.id.login_zcsj);
        Button zcyh=this.findViewById(R.id.login_zcyh);

        zcsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegisteredBusiness.class);
                startActivity(intent);
            }
        });


        zcyh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegisteredUsers.class);
                startActivity(intent);
            }
        });

        EditText id=this.findViewById(R.id.login_id);
        EditText pwd=this.findViewById(R.id.login_pwd);

        //登录功能
        Button dl=this.findViewById(R.id.login_dl);
        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String idT=id.getText().toString();
                String pwdT=pwd.getText().toString();

                if(idT.isEmpty()){
                    Toast.makeText(MainActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                }else if( pwdT.isEmpty()){
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else{

                    if(sj.isChecked()){//打开商家界面

                        int a=UserDao.loginUser(idT,pwdT,"1");
                        if(a==0){



                            Toast.makeText(MainActivity.this, "账号或密码错误，登录失败", Toast.LENGTH_SHORT).show();
                        }else{
                            edit.putString("account",idT);
                            edit.apply();
                            Intent intent=new Intent(MainActivity.this, BusinessActivity.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        }
                    }else{//打开用户界面

                        int a=UserDao.loginUser(idT,pwdT,"2");
                        if(a==0){

                            Toast.makeText(MainActivity.this, "账号或密码错误，登录失败", Toast.LENGTH_SHORT).show();
                        }else{
                            edit.putString("account",idT);
                            edit.apply();
                            Intent intent=new Intent(MainActivity.this, UserManageActivity.class);
//        Intent intent=new Intent(MainActivity.this, BuyFoodActivity.class);
                            intent.putExtra("business",idT);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                }




            }
        });






    }
    // 保存图片到外部存储


}