package dnd.jackpot.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response extends BasicResponse{
	private String message;

    public Response(String message) {
        this.message = message;
    }
}