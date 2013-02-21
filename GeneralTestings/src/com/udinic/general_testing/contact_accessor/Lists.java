package com.udinic.general_testing.contact_accessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * User: alexv
 * Date: 25/09/11
 * Time: 22:15
 */
public class Lists {
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();


    }

    public static <E> ArrayList<E> newArrayList(E... objects) {
        ArrayList<E> list = new ArrayList<E>(objects.length);
        Collections.addAll(list, objects);
        return list;

    }

    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
        // Let ArrayList's sizing logic work, if possible
        return (elements instanceof Collection)
                ? new ArrayList<E>((Collection<? extends E>) elements)
                : newArrayList(elements.iterator());
    }

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
        ArrayList<E> list = newArrayList();
        while (elements.hasNext()) {
            list.add(elements.next());
        }
        return list;
    }




}
