package com.nmatte.mood.adapters;

import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.views.chart.columns.ReadView;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EntryAdapter {
    ArrayList<ModuleAdapter> adapters;
    Observable<ModuleAdapter> oAdapters;
    public EntryAdapter(ModuleConfig config) {
        adapters = config.adapters();
        oAdapters = Observable.from(config.adapters());
    }

    public ReadView getReadView(Context context, ChartEntry entry) {
        ReadView readView = new ReadView(context, entry);

        oAdapters
                .flatMap(moduleAdapter -> moduleAdapter.getReadViews(context, entry))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<View>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(View view) {
                        readView.addView(view);
                    }
                });

        return readView;
    }

    public Observable<View> getEditViews(Context context, ChartEntry entry) {
        return oAdapters
                .flatMap(moduleAdapter -> moduleAdapter.getEditViews(context, entry))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
