package ru.yandex.practicum.contacts.presentation.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

import ru.yandex.practicum.contacts.R;
import ru.yandex.practicum.contacts.databinding.ItemContactBinding;
import ru.yandex.practicum.contacts.presentation.base.BaseListDiffCallback;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final AsyncListDiffer<ContactUi> differ = new AsyncListDiffer<>(
            new AdapterListUpdateCallback(this),
            new AsyncDifferConfig.Builder<>(new BaseListDiffCallback<ContactUi>()).build()
    );

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemContactBinding binding = ItemContactBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setItems(List<ContactUi> items) {
        differ.submitList(items);
    }

    public void setItems(List<ContactUi> items, @NonNull Runnable callback) {
        differ.submitList(items, callback);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemContactBinding binding;

        public ViewHolder(@NonNull ItemContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> {
            });
        }

        public void bind(ContactUi contact) {
            binding.name.setText(contact.getName());
            loadAvatar(contact);

            final int phoneVisibility = TextUtils.isEmpty(contact.getPhone()) ? View.GONE : View.VISIBLE;
            binding.phone.setText(contact.getPhone());
            binding.phone.setVisibility(phoneVisibility);

            binding.contactType.setData(contact.getTypes());
        }

        private void loadAvatar(ContactUi contact) {
            final Context context = binding.contactPhoto.getContext();
            final Drawable drawable = Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_avatar));
            drawable.setTint(ContextCompat.getColor(context, R.color.color_light_grey));
            Glide.with(binding.contactPhoto)
                    .load(contact.getPhoto())
                    .circleCrop()
                    .placeholder(drawable)
                    .fallback(drawable)
                    .error(drawable)
                    .into(binding.contactPhoto);
        }
    }

    static class ListDiffCallback extends DiffUtil.ItemCallback<ContactUi> {

        @Override
        public boolean areItemsTheSame(@NonNull ContactUi oldItem, @NonNull ContactUi newItem) {
            return oldItem.hashCode() == newItem.hashCode();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ContactUi oldItem, @NonNull ContactUi newItem) {
            return oldItem.equals(newItem);
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull ContactUi oldItem, @NonNull ContactUi newItem) {
            return newItem;
        }
    }
}
