package org.betterstack.punkbeerchallenge.features.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.betterstack.punkbeerchallenge.R;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

public class PunkBeerAdapter extends RecyclerView.Adapter<PunkBeerAdapter.PunkBeerViewHolder> {

    private List<PunkBeer> punkBeerList;
    private Subject<PunkBeer> punkBeerClickSubject;

    @Inject
    public PunkBeerAdapter() {
        this.punkBeerList = Collections.emptyList();
        this.punkBeerClickSubject = PublishSubject.create();
    }

    public void setPunkBeerList(List<PunkBeer> punkBeerList) {
        this.punkBeerList = punkBeerList;
        notifyDataSetChanged();
        Timber.i("Notified punk beer adapter");
    }

    Observable<PunkBeer> getPunkBeerClickSubject() {
        return punkBeerClickSubject;
    }

    @Override
    public PunkBeerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_beer, parent, false);

        return new PunkBeerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PunkBeerViewHolder holder, int position) {
        PunkBeer punkBeer = this.punkBeerList.get(position);
        holder.bindView(punkBeer);
    }

    @Override
    public int getItemCount() {
        return this.punkBeerList.size();
    }


    public class PunkBeerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.beer_name)
        TextView beerName;

        @BindView(R.id.beer_tagline)
        TextView beerTagline;

        @BindView(R.id.beer_alc)
        TextView beerAlc;

        private PunkBeer punkBeer;

        PunkBeerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> punkBeerClickSubject.onNext(punkBeer));
        }

        void bindView(PunkBeer punkBeer){
            this.punkBeer = punkBeer;
            beerName.setText(punkBeer.getName());
            beerTagline.setText(punkBeer.getTagline());
            beerAlc.setText(punkBeer.getAbv().toString());
        }

    }
}
