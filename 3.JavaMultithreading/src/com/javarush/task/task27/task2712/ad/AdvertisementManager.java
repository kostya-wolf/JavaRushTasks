package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Менеджер рекламы на планшете
 */
public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private int timeSeconds;

    private List<Advertisement> bestList = new ArrayList<>();

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        List<Advertisement> xList = new ArrayList<>();

        chooseVideosByRecursion(storage.list(), xList);
        if (storage.list().isEmpty()) {
            throw new NoVideoAvailableException();
        }
        Collections.sort(bestList, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement adv1, Advertisement adv2) {
                long result = adv2.getAmountPerOneDisplaying() - adv1.getAmountPerOneDisplaying();
                if (result == 0) {
                    result = (adv1.getAmountPerOneDisplaying() * 1000 / adv1.getDuration()) - (adv2.getAmountPerOneDisplaying() * 1000 / adv2.getDuration());
                }
                return (int) result;
            }
        });
        StatisticManager.getInstance().register(
                new VideoSelectedEventDataRow(
                        bestList,
                        getVideoListAmount(bestList),
                        getVideoListDuration(bestList)));
        for (Advertisement adv : bestList) {
            ConsoleHelper.writeMessage(adv.getName() + " is displaying... "
                    + adv.getAmountPerOneDisplaying() + ", " + (adv.getAmountPerOneDisplaying() * 1000 /adv.getDuration()));
            adv.revalidate();
        }
    }

    private void chooseVideosByRecursion(List<Advertisement> list, List<Advertisement> xList) {
        if (list.isEmpty()) {
            long bestListAmount = getVideoListAmount(bestList);
            long xListAmount = getVideoListAmount(xList);
            if (bestListAmount < xListAmount) {
                bestList = xList;
            } else if (bestListAmount == xListAmount) {
                int bestListDuration = getVideoListDuration(bestList);
                int xListDuration = getVideoListDuration(xList);
                if (bestListDuration < xListDuration) {
                    bestList = xList;
                } else if (bestListDuration == xListDuration) {
                    if (xList.size() < bestList.size()) {
                        bestList = xList;
                    }
                }
            }
            return;
        }
        int xListDuration = getVideoListDuration(xList);
        for (int i = 0; i < list.size(); i++) {
            List<Advertisement> newXList = new ArrayList<>(xList);
            if (list.get(i).getDuration() + xListDuration <= timeSeconds) {
                if (list.get(i).getHits() > 0) {
                    newXList.add(list.get(i));
                }
            }
            List<Advertisement> newList = new ArrayList<>(list);
            newList.remove(i);
            chooseVideosByRecursion(newList, newXList);
        }
    }

    private long getVideoListAmount(List<Advertisement> videoList) {
        long result = 0;
        for (Advertisement a : videoList) {
            result += a.getAmountPerOneDisplaying();
        }
        return result;
    }

    private int getVideoListDuration(List<Advertisement> videoList) {
        int result = 0;
        for (Advertisement a : videoList) {
            result += a.getDuration();
        }
        return result;
    }
}
