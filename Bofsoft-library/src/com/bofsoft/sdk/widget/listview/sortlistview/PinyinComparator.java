package com.bofsoft.sdk.widget.listview.sortlistview;

import java.util.Comparator;

public class PinyinComparator<T> implements Comparator<SortModel<T>> {

    public int compare(SortModel<T> o1, SortModel<T> o2) {
        if (o1.getKey().equals("@") || o2.getKey().equals("#")) {
            return -1;
        } else if (o1.getKey().equals("#") || o2.getKey().equals("@")) {
            return 1;
        } else {
            return o1.getKey().compareTo(o2.getKey());
        }
    }

}
