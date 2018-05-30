package com.example.danieljezik.reader;

import android.view.View;

public interface RecyclerViewClickListener {
    /**
     * Interface na zistenie pozicie kliknutia
     *
     * @param view view
     * @param position pozícia
     */
    void onClick(View view, int position);
}
