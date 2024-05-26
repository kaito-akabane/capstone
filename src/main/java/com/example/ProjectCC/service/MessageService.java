package com.example.ProjectCC.service;

import com.example.ProjectCC.domain.Message;
import com.example.ProjectCC.repository.ChatRoomRepository;
import com.example.ProjectCC.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void saveMsg(String sender, String receiver, String content) {
        String dataTime = getDateTime();
        Message message = new Message();
        message.setRoomNum(findRoomNum(sender, receiver));
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setDateTime(dataTime);
        messageRepository.save(message);
    }

    public String getDateTime() {
        // 현재 날짜와 시간을 구합니다.
        Date now = new Date();
        // 날짜와 시간을 포맷할 포맷터를 생성합니다.
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 포맷터를 사용하여 날짜와 시간을 원하는 형식으로 포맷합니다.
        String formattedDateTime = dateTimeFormatter.format(now);

        return formattedDateTime;
    }

    public int findRoomNum(String member1, String member2) {
        int roomNum = -1;
        if (chatRoomRepository.findByMember1AndMember2(member1, member2).isPresent()) {
            roomNum = chatRoomRepository.findByMember1AndMember2(member1, member2).get().getRoomNum();
        } else {
            roomNum = chatRoomRepository.findByMember1AndMember2(member2, member1).get().getRoomNum();
        }
        return roomNum;
    }
}
