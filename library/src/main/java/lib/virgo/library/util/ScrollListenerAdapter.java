package lib.virgo.library.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lib.virgo.library.ScrollToCenterView;

/**
 * Rebuild from DroidSky/DiscreteScrollView
 * <a href="https://github.com/DroidSky/DiscreteScrollView">...</a>
 */
public class ScrollListenerAdapter<T extends RecyclerView.ViewHolder> implements ScrollToCenterView.ScrollStateChangeListener<T> {

    private ScrollToCenterView.ScrollListener<T> adaptee;

    public ScrollListenerAdapter(ScrollToCenterView.ScrollListener<T> adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void onScrollStart(@NonNull T currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScrollEnd(@NonNull T currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScroll(float scrollPosition, @NonNull T currentHolder, @NonNull T newCurrentHolder) {
        adaptee.onScroll(scrollPosition, currentHolder, newCurrentHolder);
    }
}
