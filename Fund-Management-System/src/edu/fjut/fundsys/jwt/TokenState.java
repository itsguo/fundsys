package edu.fjut.fundsys.jwt;

/**
 * 枚举，定义token的三种状态
 * 
 */
public enum TokenState {
	/**
	 * 过期
	 */
	EXPIRED("EXPIRED"),
	/**
	 * 无效(token不合�?
	 */
	INVALID("INVALID"),
	/**
	 * 有效�?
	 */
	VALID("VALID");

	private String state;

	private TokenState(String state) {
		this.state = state;
	}

	/**
	 * 根据状�?字符串获取token状�?枚举对象
	 * 
	 * @param tokenState
	 * @return
	 */
	public static TokenState getTokenState(String tokenState) {
		TokenState[] states = TokenState.values();
		TokenState ts = null;
		for (TokenState state : states) {
			if (state.toString().equals(tokenState)) {
				ts = state;
				break;
			}
		}
		return ts;
	}

	public String toString() {
		return this.state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
