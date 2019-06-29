package com.themaker.fshmo.klassikaplus.presentation.novelties;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.RequestManager;
import com.themaker.fshmo.klassikaplus.R;
import com.themaker.fshmo.klassikaplus.data.domain.Item;

class NoveltyViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = NoveltyViewHolder.class.getName();

    @BindView(R.id.novelty_item_icon)
    ImageView icon;
    @BindView(R.id.novelty_item_name)
    TextView name;
    @BindView(R.id.novelty_item_price)
    TextView price;

    private View baseView;
    private Item item;

    NoveltyViewHolder(View v) {
        super(v);
        this.baseView = v;
        ButterKnife.bind(this, v);
    }

    public void bind(@NonNull final Item item,
                     @NonNull RequestManager glide,
                     @NonNull NoveltyAdapter.OnItemClickListener onItemClickListener) {
        this.item = item;
        if (item.getPrice() != null)
            price.setText(String.valueOf(item.getPrice()));
        else
            price.setVisibility(View.GONE);
        if (item.getName() != null)
            name.setText(item.getName());
        else
            name.setVisibility(View.GONE);
//        if (item.getPageAlias() != null)
//            goToShop.setVisibility(View.VISIBLE);
//        if (item.getNovelty() && item.getNovelty() != null)
//            novelty.setVisibility(View.VISIBLE);
        if(!item.getIcon().isEmpty()) {
            String url = item.getIcon();
            glide.load(url)
                    .into(icon);
            baseView.setOnClickListener(v -> onItemClickListener.onItemClick(item));
        } else {
            Log.i(TAG, "bind: setting default picture for view holder");
            // TODO: 2/6/2019 placeholder image
        }
    }

    public Item getItem() {
        return item;
        }
    }
}
