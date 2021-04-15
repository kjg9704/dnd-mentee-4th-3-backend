package dnd.jackpot.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonResponse<T> extends BasicResponse {
	private String message;
	private T result;

	public CommonResponse(T result) {
		this.message = "success";
		this.result = result;
	}
	public CommonResponse(List<T> result) {
		this.message = "success";
		this.result = (T) result;
	}
}
