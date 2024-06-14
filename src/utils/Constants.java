package utils;

public class Constants {

	public static class Ships{
		public static final int CARRIER = 4;
		public static final int CRUISER = 3;
		public static final int DESTROYER = 2;
		public static final int SUB = 1;
		
		public static final int CARRIER_WIDTH = 160;
		public static final int CARRIER_HEIGHT = 40;
		
		public static final int CRUISER_WIDTH = 120;
		public static final int CRUISER_HEIGHT = 40;
		
		public static final int DESTROYER_WIDTH = 80;
		public static final int DESTROYER_HEIGHT = 40;
		
		public static final int SUBMARINE_WIDTH = 40;
		public static final int SUBMARINE_HEIGHT = 40;
		
		public static int GetShipWidth(int type){
			switch(type) {
			case CARRIER:
				return CARRIER_WIDTH;
			case CRUISER:
				return CRUISER_WIDTH;
			case DESTROYER:
				return DESTROYER_WIDTH;
			case SUB:
				return SUBMARINE_WIDTH;
			}
			return 0;
		}
		
		public static int GetShipHeight(int type){
			switch(type) {
			case CARRIER:
				return CARRIER_HEIGHT;
			case CRUISER:
				return CRUISER_HEIGHT;
			case DESTROYER:
				return DESTROYER_HEIGHT;
			case SUB:
				return SUBMARINE_HEIGHT;
			}
			return 0;
		}
	}
	
}
