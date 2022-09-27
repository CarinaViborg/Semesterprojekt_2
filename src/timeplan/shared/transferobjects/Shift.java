package timeplan.shared.transferobjects;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Shift implements Serializable
{
  private final int id;
  private final Date date;
  private final Time timeStart;
  private final Time timeEnd;


  public Shift (int id, Date date, Time timeStart, Time timeEnd)
  {
    this.id = id;
    this.date = date;
    this.timeStart = timeStart;
    this.timeEnd = timeEnd;
  }

  public int getId() {return  id;}
  public Date getDate() {return  date;}
  public Time getTimeStart() {return  timeStart;}
  public Time getTimeEnd() {return  timeEnd;}
}
