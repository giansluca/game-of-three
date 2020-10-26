package org.gmdev.event.message;

public interface Message {
	
	public enum InitNumberMode {
		MANUAL,
		RANDOM
	}
	
	public enum StartMode {
		SEND_FIRST("SEND_FIRST"),
		ONLY_LISTEN("ONLY_LISTEN");
		
		private String startMode;

		StartMode(String startMode) {
			this.startMode = startMode;
		}
		
		public String getStartMode() {
			return startMode;
		}
	}
}
