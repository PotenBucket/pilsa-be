package potenday.pilsa.chatGpt.dto.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class ChatRequest implements Serializable {

    private String question;

}
