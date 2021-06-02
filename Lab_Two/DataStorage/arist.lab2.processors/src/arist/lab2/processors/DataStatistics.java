package arist.lab2.processors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;


public class DataStatistics {
    public Collection<String> collect(ArrayList<String> data) {
        Collection<String> collect = new ArrayList<>();
        TreeMap<String, Integer> map = new TreeMap<>();
        for (String datum : data) {
            String[] parts = datum.split("&");
            String key = parts[0];
            if (map.containsKey(key)) {
                map.replace(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }
        for (String name : map.keySet()) {
            collect.add(name + ": " + map.get(name));
        }
        return collect;
    }
}