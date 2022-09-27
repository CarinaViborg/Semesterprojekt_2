package timeplan.shared.transferobjects;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Timestamp implements Serializable
{
  private final Time inTime;
  private final Time outTime;
  private final Date date;
  private final int id;

  public Timestamp(int id, Date date, Time inTime, Time outTime){
    this.id = id;
    this.date = date;
    this.inTime = inTime;
    this.outTime = outTime;
  }

  public Time getInTime()
  {
    return inTime;
  }

  public Time getOutTime()
  {
    return outTime;
  }

  public Date getDate()
  {
    return date;
  }

  public int getId()
  {
    return id;
  }
}
