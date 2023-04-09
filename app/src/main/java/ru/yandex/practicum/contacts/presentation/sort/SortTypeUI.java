package ru.yandex.practicum.contacts.presentation.sort;

import androidx.annotation.NonNull;

import ru.yandex.practicum.contacts.presentation.base.ListDiffInterface;
import ru.yandex.practicum.contacts.presentation.sort.model.SortType;

public class SortTypeUI implements ListDiffInterface<SortTypeUI> {

    private final SortType sortType;
    private final boolean selected;

    public SortTypeUI(@NonNull SortType sortType, boolean selected) {
        this.sortType = sortType;
        this.selected = selected;
    }

    @Override
    public boolean theSameAs(SortTypeUI s) {
        return this.getSortType() == s.getSortType();
    }

    public SortType getSortType() {
        return sortType;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SortTypeUI that = (SortTypeUI) o;

        if (selected != that.selected) return false;
        return sortType == that.sortType;
    }

    @Override
    public int hashCode() {
        int result = sortType.hashCode();
        result = 31 * result + (selected ? 1 : 0);
        return result;
    }
}
