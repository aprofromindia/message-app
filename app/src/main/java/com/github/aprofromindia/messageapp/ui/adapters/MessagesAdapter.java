package com.github.aprofromindia.messageapp.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.aprofromindia.messageapp.R;
import com.github.aprofromindia.messageapp.databinding.ItemMessageBinding;
import com.github.aprofromindia.messageapp.viewModels.MessagesViewModel;

/**
 * Created by Apro on 15-06-2017.
 */

public final class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private MessagesViewModel viewModel;

    public MessagesAdapter(MessagesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMessageBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_message, parent, false);
        return new MessageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if (position > 0.9 * viewModel.getMessages().size()) {
            fetchNextMessages(position);
        }

        holder.binding.setMessage(viewModel.getMessages().get(position));
        holder.binding.executePendingBindings();
    }

    private void fetchNextMessages(final int position) {
        viewModel.fetchMessages(position, () -> {
            notifyItemRangeInserted(viewModel.getMessages().size() - MessagesViewModel.PAGE_SIZE,
                    viewModel.getMessages().size() - 1);
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.getMessages().size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public ItemMessageBinding binding;

        MessageViewHolder(ItemMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
