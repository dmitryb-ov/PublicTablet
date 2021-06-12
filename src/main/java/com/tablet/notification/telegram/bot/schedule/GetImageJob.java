package com.tablet.notification.telegram.bot.schedule;

import com.tablet.notification.telegram.model.AnimalType;
import com.tablet.notification.telegram.model.Image;
import com.tablet.notification.telegram.service.ImageService;
import com.tablet.notification.telegram.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
@EnableAsync
public class GetImageJob {
    private static final int ITERATION_CAT_COUNT = 10;
    private static final int ITERATION_GIPFY_COUNT = 7;

    @Autowired
    private ImageService imageService;

    //Every 4 and 14 hour 23 min
    @Scheduled(cron = "0 23 4,14 ? * *")
    @Async
    public void scheduleGetImage() {
        imageService.deleteAll();
        for (int i = 0; i < ITERATION_CAT_COUNT; i++) {
            try {
                ImageUtil imageUtil = new ImageUtil();
                Image catImage = new Image("Cat image", imageUtil.getImageType(), imageUtil.getImageURL(), AnimalType.CAT);
                imageService.saveImage(catImage);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        for (int i = 0; i < ITERATION_GIPFY_COUNT; i++) {
//            try {
//                GiphyUtil giphyUtil = new GiphyUtil();
//                Image animalImage = new Image("Giphy animal image", giphyUtil.getImageType(), giphyUtil.getStringGifUrl(), AnimalType.OTHER);
//                imageService.saveImage(animalImage);
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
