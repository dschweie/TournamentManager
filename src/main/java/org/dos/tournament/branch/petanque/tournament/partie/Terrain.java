package org.dos.tournament.branch.petanque.tournament.partie;

public class Terrain
{
  private String code;
  private String text;
  private String description;

  public Terrain(String code, String text, String description)
  {
    this.code           = code.toString();
    this.text           = text.toString();
    this.description    = description.toString();
  }

  public static Terrain getTerraLibre()
  {
    return new Terrain("TLi", "Terra Libre", "Kein fester Platz zugewiesen.");
  }

  public String getCode()
  {
    return this.code;
  }
}
