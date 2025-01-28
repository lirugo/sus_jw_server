package com.lirugo.sus_jw.service;

import com.lirugo.sus_jw.config.TelegramBot;
import com.lirugo.sus_jw.entity.StandDayEntity;
import com.lirugo.sus_jw.entity.TimeFrameEntity;
import static com.lirugo.sus_jw.util.Utils.DAY_MONTH_FORMATTER;
import java.text.MessageFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramService {

    private static final String FREE_PLACE = "Вільно";
    private final TelegramBot telegramBot;

    @Value("${app.chat-id}")
    private String chatId;

    @Value("${app.message-template}")
    private String messageTemplate;

    public void sendStandNeedNotification(StandDayEntity standDay, TimeFrameEntity timeFrame) {
        var attendees = standDay.getAttendees().stream().filter(a -> a.getTimeFrame().equals(timeFrame)).toList();
        if (attendees.size() != 3) {
            log.info("StandDay {} attendees, no need to send notification", attendees.size());
            return;
        }

        telegramBot.sendMessage(chatId, MessageFormat.format(messageTemplate,
                DAY_MONTH_FORMATTER.format(standDay.getDate()),
                timeFrame.getFromTime() + "-" + timeFrame.getToTime(),
                standDay.getStand().getName(),
                attendees.size() > 0 ? attendees.get(0).getUser().getFullName() : FREE_PLACE,
                attendees.size() > 1 ? attendees.get(1).getUser().getFullName() : FREE_PLACE,
                attendees.size() > 2 ? attendees.get(2).getUser().getFullName() : FREE_PLACE,
                attendees.size() > 3 ? attendees.get(3).getUser().getFullName() : FREE_PLACE
        ));
    }
}
