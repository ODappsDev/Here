package com.here.danielstolero.here.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.here.danielstolero.here.DataRepository;
import com.here.danielstolero.here.MyApplication;
import com.here.danielstolero.here.R;
import com.here.danielstolero.here.adapters.TaxiAdapter;
import com.here.danielstolero.here.base.BaseActivity;
import com.here.danielstolero.here.db.AppDatabase;
import com.here.danielstolero.here.db.entities.TaxiEntity;
import com.here.danielstolero.here.models.Taxi;
import com.here.danielstolero.here.viewmodel.TaxiViewModel;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
        MyApplication application = viewModel.getApplication();
        DataRepository repository = application.getRepository();

        final int[] counter = {0};

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Taxi taxi = repository.isFinish();
                if (taxi != null) {
                    this.cancel();
                    runOnUiThread(() -> new AlertDialog.Builder(MainActivity.this)
                            .setMessage(String.format("The Taxi %s here!!!", taxi.getName()))
                            .setPositiveButton("Finish", (dialog, which) -> finish())
                            .setNegativeButton("Restart", (dialog, which) -> {
                                        application.getDatabase().restart(application.getAppExecutors());
                                        startRandomData(viewModel);
                                    }
                            )
                            .show());
                }

                Log.d("Daniel", "task working " + counter[0] + " times!!!");

                List<TaxiEntity> data = repository.getTaxis().getValue();

                if(data != null) {
                    Random random = new Random();
                    int changes = random.nextInt(data.size());
                    int [] randomIndexes = new int[changes];
                    int [] ids = new int[changes];
                    for (int i = 0; i < changes; i++) {
                        randomIndexes[i] = random.nextInt(data.size());
                        ids[i] = data.get(randomIndexes[i]).getId();
                    }
                    repository.updateEta(ids);
                }

                counter[0]++;
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, TimeUnit.SECONDS.toMillis(5), TimeUnit.SECONDS.toMillis(5));
    }

}
