package org.dos.tournament.application.common.controls;

public class TableHeaderColumnContent
{
  private String strId;
  private Object objValue;
  private int iPreferredWidth;
  
  public TableHeaderColumnContent(String id, Object value, int preferredWidth)
  {
    this.strId = id;
    this.objValue = value;
    this.iPreferredWidth = preferredWidth;
  }

  /**
   * @return the strValue
   */
  public Object getValue()
  {
    return objValue;
  }

  /**
   * @param objValue the strValue to set
   */
  public void setValue(Object value)
  {
    this.objValue = value;
  }

  /**
   * @return the PreferredSize
   */
  public int getPreferredWidth()
  {
    return iPreferredWidth;
  }

  /**
   * @param PreferredSize the iPreferredSize to set
   */
  public void setPreferredWidth(int preferredWidth)
  {
    this.iPreferredWidth = preferredWidth;
  }

  /**
   * @return the strId
   */
  public String getId()
  {
    return strId;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return this.objValue.toString();
  }
  
  
}
