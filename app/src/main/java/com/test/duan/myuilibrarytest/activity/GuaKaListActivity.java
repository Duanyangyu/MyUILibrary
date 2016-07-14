package com.test.duan.myuilibrarytest.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.view.GuaGuaKaView;

public class GuaKaListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gua_ka_list);
        ListView listView = (ListView) findViewById(R.id.list_guaka);

        listView.setAdapter(new GuaKaAdapter(this,10));

    }


    class GuaKaAdapter extends BaseAdapter{


        private Context context;
        private int sum;

        public GuaKaAdapter(Context context, int sum) {
            this.context = context;
            this.sum = sum;
        }

        @Override
        public int getCount() {
            return sum;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (convertView == null){
                GuaGuaKaView guaGuaKaView = new GuaGuaKaView(context);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        200);
                guaGuaKaView.setLayoutParams(params);
                guaGuaKaView.setTag(position);
                guaGuaKaView.setText("寰宇医道");
                return guaGuaKaView;
            }
            return convertView;
        }
    }

}
