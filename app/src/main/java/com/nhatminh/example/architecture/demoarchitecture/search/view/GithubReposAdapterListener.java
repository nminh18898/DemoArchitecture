package com.nhatminh.example.architecture.demoarchitecture.search.view;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GithubReposAdapterListener implements RecyclerView.OnItemTouchListener {

    private GithubReposAdapterListener.OnItemClickListener listener;
    private GestureDetector gestureDetector;

    public GithubReposAdapterListener(Context context, RecyclerView recyclerView, GithubReposAdapterListener.OnItemClickListener listener) {
        this.listener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = recyclerView.getChildAdapterPosition(childView);
                if (position < 0){
                    return false;
                }
                listener.onItemClick(childView, position);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = recyclerView.getChildAdapterPosition(childView);
                if (position < 0){
                    return;
                }
                if(childView != null && listener != null) {
                    listener.onItemLongClick(childView, position);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
