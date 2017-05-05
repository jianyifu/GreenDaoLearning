package com.fjy.greendaolearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fjy.greendaolearning.db.DBManager;
import com.fjy.greendaolearning.db.DaoSession;
import com.fjy.greendaolearning.db.PushMsgDao;
import com.fjy.greendaolearning.pojo.PushMsg;

import org.greenrobot.greendao.query.Query;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    private Button addMsgBtn;
    private RecyclerView msgList;
    private PushMsgDao pushMsgDao;
    private Query<PushMsg> msgQuery;
    private PushMsgAdapter pushAdapter;
    private PushMsgAdapter.MsgClickListener msgClickListener;
    private Button doQueryBtn;
    private TextView queryResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        DaoSession daoSession = DBManager.getInstance().getDaoSession();
        pushMsgDao = daoSession.getPushMsgDao();

        // query all notes, sorted a-z by their text
        msgQuery = pushMsgDao.queryBuilder().orderAsc(PushMsgDao.Properties.Id).build();
        updateMsgList();
    }

    private void updateMsgList() {
        List<PushMsg> notes = msgQuery.list();
        pushAdapter.setNotes(notes);
    }

    private void setUpViews() {
        addMsgBtn = (Button) findViewById(R.id.add_msg_btn);
        doQueryBtn = (Button) findViewById(R.id.do_query_btn);
        queryResult = (TextView) findViewById(R.id.query_result);
        msgList = (RecyclerView) findViewById(R.id.msg_list);
        msgList.setLayoutManager(new LinearLayoutManager(this));
        msgClickListener = new PushMsgAdapter.MsgClickListener() {
            @Override
            public void onMsgClick(int position) {
                Toast.makeText(MainActivity.this,"msg pos="+position,Toast.LENGTH_SHORT).show();
            }
        };
        pushAdapter = new PushMsgAdapter(msgClickListener);
        msgList.setAdapter(pushAdapter);
        addMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMsg();
            }
        });
        doQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doQueryOnOneSession();
            }


        });
    }
    private void addMsg() {
        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());
        PushMsg msg = new PushMsg();
        msg.setCreateTime(new Date());
        msg.setDescription(comment);
        msg.setTitle("This is a new pushMsg");
        pushMsgDao.insert(msg);
        Log.d(TAG, "Inserted new note, ID: " + msg.getId());
        updateMsgList();
    }
    private void doQueryOnOneSession() {
        pushMsgDao.detachAll();//To clear the identity scope for just a single DAO:
//        DBManager.getInstance().getDaoSession().clear();//To clear the identity scope of the whole session so no “cached” objects are returned:
        List msgs = pushMsgDao.queryBuilder()
                .where(PushMsgDao.Properties.Id.eq("1"))
                .list();
        queryResult.setText(queryResult.getText()+(msgs==null?"null":msgs.toString()));
    }
}
