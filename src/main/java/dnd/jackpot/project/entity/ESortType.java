package dnd.jackpot.project.entity;


public enum ESortType {
	최신순("createdAt"), 
	조회순("scrappedNum");
	
	final private String name;
	

	private ESortType (String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}