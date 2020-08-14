package frame.base.exception;

public class FrameException extends RuntimeException {
	private static final long serialVersionUID = -4255988253365337864L;


	public FrameException(String message, Throwable e) {
		super(message + "[" + e.getMessage() + "]", e);
	}

	public FrameException(Throwable e) {
		super(e);
	}

	public FrameException(String message) {
		super(message);
	}
}
