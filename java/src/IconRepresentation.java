public enum IconRepresentation
{
	ZER0("0"), ONE("1"), TWO("2"), THREE("3"),
	FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"),
	EIGHT("8"), COVERED("C"), BOOM("B"), FLAG("F"),
	EMPTY("E"), MINE("M"), FLAG_WRONG("W");
	
	private String rep;

	private IconRepresentation(String s)
	{
		rep = s;
	}

	public String getRepresentation()
	{
		return rep;
	}

}
