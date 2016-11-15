package com.example.administrator.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "MainActivity";
    //显示的用户的item
    public ArrayList<User> userList = new ArrayList<>();
    public userListAdapter userListAdapter;
    public  EditText etSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化Xutils
        x.Ext.init(getApplication());

        etSearch= (EditText) findViewById(R.id.et_search);
        ListView listView = (ListView) findViewById(R.id.list);
        userListAdapter = new userListAdapter(this, userList);
        listView.setAdapter(userListAdapter);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "---beforeTextChanged---");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                userList.clear();
//                String str = etSearch.getText().toString().trim();
//                if (str.length()>0){
//                  RequestUserData(str);
//                }
                RequestUserData();
                Log.i(TAG, "---onTextChanged---");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG, "---afterTextChanged---");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        userList.clear();
    }

    /*
            获取用户数据
             */
    private void RequestUserData() {
        RequestParams params = new RequestParams("https://api.github.com/users/test/repos");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(MainActivity.this,"---onSuccess---",Toast.LENGTH_SHORT).show();
                Log.i(TAG, s);
                /*
                将获取到的用户信息存储到userList
                 */
                try {
                    JSONArray resultJson = new JSONArray(s);
                    User user;
                    for (int i = 0; i < resultJson.length(); i++) {
                        user = new User();
                        JSONObject userObject = (JSONObject) resultJson.get(i);

                        int userId = userObject.getInt("id");
                        String userIcon = userObject.getJSONObject("owner").getString("avatar_url");
//                        Log.i(TAG,"userIcon---"+userIcon);
                        String userName = userObject.getString("name");
                        String userLanguage = userObject.getString("language");

                        user.setId(userId);
                        user.setIcon(userIcon);
                        user.setName(userName);
                        user.setPrivator(userLanguage);
//                        Log.i(TAG, "language---" + userLanguage + "---str---" + str);
                        String str = etSearch.getText().toString().trim();
                        if (str.length()>0){
                        if (userLanguage.toLowerCase().contains(str.toLowerCase())) {
                            //去重复
                            if (userList.size() > 0) {
                                for (int j = 0; j < userList.size(); j++) {
                                    if (userId != userList.get(j).getId()) {
                                        userList.add(user);
                                    }
                                }
                            } else {
                                userList.add(user);
                            }
                        }
                        }else{
                            userList.clear();
                        }
                        user = null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "---onSuccess---" + userList.toString());
                userListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "---onError---");
                Toast.makeText(MainActivity.this,"---onError---",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException e) {
                Log.i(TAG, "---onCancelled---");
                Toast.makeText(MainActivity.this,"---onCancelled---",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "---onFinished---");
                Toast.makeText(MainActivity.this,"---onFinished---",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
