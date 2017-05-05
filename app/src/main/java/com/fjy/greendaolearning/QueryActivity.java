package com.fjy.greendaolearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fjy.greendaolearning.db.DBManager;
import com.fjy.greendaolearning.db.PushMsgDao;
import com.fjy.greendaolearning.pojo.PushMsg;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class QueryActivity extends AppCompatActivity {
    private static final String TAG = "QueryActivity";
    private Button doQueryBtn;
    private RecyclerView msgList;
    private PushMsgAdapter adapter;
    private PushMsgDao pushMsgDao;
    private Query<PushMsg> msgQuery;
    private QueryBuilder<PushMsg> qb;
    private Button doQueryAgainBtn;
    private Button doMultiThreadQueryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        setUpViews();
        pushMsgDao = DBManager.getInstance().getDaoSession().getPushMsgDao();
        qb = pushMsgDao.queryBuilder();
        msgQuery = qb.where(PushMsgDao.Properties.Title.like("aa%")).offset(0).limit(20).build();
    }

    private void setUpViews() {
        doQueryBtn = (Button) findViewById(R.id.do_query_btn);
        doQueryAgainBtn = (Button) findViewById(R.id.do_query_again_btn);
        doMultiThreadQueryBtn = (Button) findViewById(R.id.do_multi_thread_query_btn);
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
        doQueryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLimitOffsetQueryWithDiffParams();
            }
        });
        doMultiThreadQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doMultiThreadQuery();
            }
        });
    }

    private void doMultiThreadQuery() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Every time, forCurrentThread() is called, the parameters are set to the initial parameters at the time the query was built using its builder.
//                final List<PushMsg> msgs = msgQuery.list();//org.greenrobot.greendao.DaoException: Method may be called only in owner thread, use forCurrentThread to get an instance for this thread
//                final List<PushMsg> msgs = qb.build().forCurrentThread().list(); // results same with doLimitOffsetQuery()
//                final List<PushMsg> msgs = qb.build().setParameter(0,"dd%").forCurrentThread().list(); // results same with doLimitOffsetQuery()
                final List<PushMsg> msgs = qb.build().forCurrentThread().setParameter(0,"dd%").list();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setMsgs(msgs);
                    }
                });
            }
        }).start();
    }

    private void doLimitOffsetQueryWithDiffParams() {

        msgQuery.setParameter(0, "cc%");
        List<PushMsg> msgs = msgQuery.list();
        Log.d(TAG, "doLimitOffsetQuery() called msgQuery"+msgQuery);
        adapter.setMsgs(msgs);
    }


    private void doLimitOffsetQuery() {
        //get items from index=2 and count=5
        adapter.setMsgs(msgQuery.list());
    }
}
