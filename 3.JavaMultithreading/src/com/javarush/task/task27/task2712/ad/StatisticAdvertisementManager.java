package com.javarush.task.task27.task2712.ad;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Предоставляет информацию из AdvertisementStorage в нужном виде
 */
public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance = new StatisticAdvertisementManager();

    public static StatisticAdvertisementManager getInstance() {
        return instance;
    }

    private AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {
    }

    public List<Advertisement> getSortedVideoList() {
        List<Advertisement> result = storage.list();
        Collections.sort(result, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement adv1, Advertisement adv2) {
                return adv1.getName().compareToIgnoreCase(adv2.getName());
            }
        });
        return result;
    }
}
