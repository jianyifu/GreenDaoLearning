package com.fjy.greendaolearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.fjy.greendaolearning.db.DBManager;
import com.fjy.greendaolearning.db.PushMsgDao;
import com.fjy.greendaolearning.pojo.PushMsg;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class QueryActivity extends AppCompatActivity {
    private Button doQueryBtn;
    private RecyclerView msgList;
    private PushMsgAdapter adapter;
    private PushMsgDao pushMsgDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        setUpViews();
        pushMsgDao = DBManager.getInstance().getDaoSession().getPushMsgDao();
    }

    private void setUpViews() {
        doQueryBtn = (Button) findViewById(R.id.do_query_btn);
        msgList = (RecyclerView) findViewById(R.id.msg_list);
        msgList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PushMsgAdapter(new PushMsgAdapter.MsgClickListener() {
            @Override
            public void onMsgClick(int position) {

            }
        });
        msgList.setAdapter(adapter);
        doQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               doLimitOffsetQuery();
            }


        });
    }





    private void doLimitOffsetQuery() {
        //get items from index=2 and count=5
       Query<PushMsg> msgQuery =  pushMsgDao.queryBuilder().limit(5).offset(2).build();
        List<PushMsg> msgs = msgQuery.list();
        adapter.setMsgs(msgs);
    }
}
