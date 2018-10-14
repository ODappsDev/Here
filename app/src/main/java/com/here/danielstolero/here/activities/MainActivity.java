package com.here.danielstolero.here.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.here.danielstolero.here.DataRepository;
import com.here.danielstolero.here.MyApplication;
import com.here.danielstolero.here.R;
import com.here.danielstolero.here.adapters.TaxiAdapter;
import com.here.danielstolero.here.base.BaseActivity;
import com.here.danielstolero.here.db.entities.TaxiEntity;
import com.here.danielstolero.here.viewmodel.TaxiViewModel;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private TaxiAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int setLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.taxi_list);

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        mAdapter = new TaxiAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        if (mRecyclerView.getItemAnimator() != null)
            mRecyclerView.getItemAnimator().setChangeDuration(0);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        TaxiViewModel viewModel = ViewModelProviders.of(this).get(TaxiViewModel.class);
        subscribeUi(viewModel);
        startRandomData(viewModel);
    }

    private void subscribeUi(TaxiViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getTaxis().observe(this, taxis -> mAdapter.setTaxis(taxis));
    }

    private void startRandomData(TaxiViewModel viewModel) {
        DataRepository repository = ((MyApplication) viewModel.getApplication()).getRepository();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(repository.isFinish()) {
                    this.cancel();
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "The wait is over!!", Toast.LENGTH_LONG).show());
                }

                Log.d("Daniel", "task working!!!");
                repository.updateEta();
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, TimeUnit.SECONDS.toMillis(5), TimeUnit.SECONDS.toMillis(5));
    }

}
