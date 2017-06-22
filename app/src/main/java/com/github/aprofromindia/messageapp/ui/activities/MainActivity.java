package com.github.aprofromindia.messageapp.ui.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.github.aprofromindia.messageapp.R;
import com.github.aprofromindia.messageapp.databinding.ActivityMainBinding;
import com.github.aprofromindia.messageapp.ui.adapters.MessagesAdapter;
import com.github.aprofromindia.messageapp.ui.views.MessageView;
import com.github.aprofromindia.messageapp.viewModels.MessagesFragment;
import com.github.aprofromindia.messageapp.viewModels.MessagesViewModel;

import lombok.val;

public final class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);
        setupViews(binding, getViewModel());
    }

    private void setupViews(ActivityMainBinding binding, MessagesViewModel viewModel) {
        val recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        val adapter = new MessagesAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        viewModel.fetchMessages(0, () -> adapter.notifyItemRangeInserted(0,
                MessagesViewModel.PAGE_SIZE - 1));

        setupSwipeHelper(viewModel, recyclerView, adapter);

        val divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);
    }

    private void setupSwipeHelper(final MessagesViewModel viewModel,
                                  RecyclerView recyclerView, final MessagesAdapter adapter) {

        ItemTouchHelper swipeHelper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                MessageView messageView = ((MessagesAdapter.MessageViewHolder) viewHolder).binding.msgView;
                getDefaultUIUtil().onDraw(c, recyclerView, messageView,
                        dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                        int actionState, boolean isCurrentlyActive) {
                MessageView messageView = ((MessagesAdapter.MessageViewHolder) viewHolder).binding.msgView;
                getDefaultUIUtil().onDrawOver(c, recyclerView, messageView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                MessageView messageView = ((MessagesAdapter.MessageViewHolder) viewHolder).binding.msgView;
                getDefaultUIUtil().clearView(messageView);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                val position = viewHolder.getAdapterPosition();
                viewModel.deleteMessage(position, () -> adapter.notifyItemRemoved(position));
            }
        });
        swipeHelper.attachToRecyclerView(recyclerView);
    }

    private MessagesViewModel getViewModel() {
        MessagesFragment messagesFragment = (MessagesFragment) getSupportFragmentManager()
                .findFragmentByTag(MessagesFragment.TAG);

        if (messagesFragment == null) {
            messagesFragment = MessagesFragment.newInstance(getApplicationContext());
            getSupportFragmentManager().beginTransaction()
                    .add(messagesFragment, MessagesFragment.TAG)
                    .commit();
        }
        return messagesFragment.getViewModel();
    }
}
