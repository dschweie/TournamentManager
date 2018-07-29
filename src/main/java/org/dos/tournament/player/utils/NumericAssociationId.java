package org.dos.tournament.player.utils;

public class NumericAssociationId implements IParticipantId
{
  private int id;
  private String code = null;
  private String association = null;
  private String description = null; 

  public NumericAssociationId(String code, String association, String description)
  {
    this.code = null==code?null:code.toString();
    this.association = null==association?null:association.toString();
    this.description = null==description?null:description.toString();
  }

  public NumericAssociationId(int id, String association, String description)
  {
    this(id, null, association, description);
  }
  
  public NumericAssociationId(int id, String code, String association, String description)
  {
    this(code, association, description);
    this.id = id;
  }

  @Override
  public String getCode()
  {
    return null==code?String.format("%3d", this.id):this.code.substring(0, 3);
  }

  @Override
  public String getName()
  {
    return null==this.association?"":this.association;
  }

  @Override
  public String getDescription()
  {
    return null==this.description?"":this.description;
  }

}
